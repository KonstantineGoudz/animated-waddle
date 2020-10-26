package com.capsule.health.models.net

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NetworkResource<out T>(
    val state: LoadingState,
    val data: T? = null,
    val error: Throwable? = null,
    val message: String? = null
) {
    companion object {
        fun <T> loading(): NetworkResource<T> {
            return NetworkResource(LoadingState.LOADING)
        }

        fun <T> loaded(data: T?): NetworkResource<T> {
            return NetworkResource(LoadingState.COMPLETE, data)
        }

        fun <T> error(message: String?, error: Throwable?): NetworkResource<T> {
            return NetworkResource(LoadingState.ERROR, null, error, message)
        }
    }
}

inline fun <T> MutableLiveData<NetworkResource<T>>.load(
    scope: CoroutineScope,
    crossinline f: suspend (scope: CoroutineScope) -> T
) {
    scope.launch {
        this@load.postValue(NetworkResource.loading())
        try {
            this@load.postValue(NetworkResource.loaded(f(this)))
        } catch (e: Throwable) {
            this@load.postValue(NetworkResource.error(e.message, e))
        }
    }
}

enum class LoadingState {
    LOADING,
    COMPLETE,
    ERROR
}