package com.capsule.health.binding

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.capsule.health.models.net.LoadingState
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class BindingExtKtTest {
    private val view = Mockito.mock(View::class.java)
    private val recyclerView = Mockito.mock(RecyclerView::class.java)
    private val adapter = Mockito.mock(RecyclerView.Adapter::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testLoading() {
        view.visibility = GONE
        view.progress(LoadingState.LOADING)
        Mockito.verify(view, Mockito.times(1)).visibility = VISIBLE
    }

    @Test
    fun testComplete() {
        view.visibility = VISIBLE
        view.progress(LoadingState.COMPLETE)
        Mockito.verify(view, Mockito.times(1)).visibility = GONE
    }

    @Test
    fun testError() {
        view.visibility = VISIBLE
        view.progress(LoadingState.ERROR)
        Mockito.verify(view, Mockito.times(1)).visibility = GONE
    }

    @Test
    fun testSetAdapter(){
        recyclerView.bindAdapter(adapter)
        Mockito.verify(recyclerView, Mockito.times(1)).adapter = adapter

    }
}