package com.capsule.health.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capsule.health.R
import com.capsule.health.databinding.ListItemConceptBinding
import com.capsule.health.models.concept.Concept

class ConceptAdapter(val onItemSelected: (Concept) -> Unit) :
    ListAdapter<Concept, ConceptAdapter.ConceptViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<Concept>() {
        override fun areItemsTheSame(oldItem: Concept, newItem: Concept): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Concept, newItem: Concept): Boolean {
            return oldItem.conceptId == newItem.conceptId
        }
    }

    class ConceptViewHolder(val topicBinding: ListItemConceptBinding) :
        RecyclerView.ViewHolder(topicBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConceptViewHolder {
        return ConceptViewHolder(
            ListItemConceptBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    private var selectedItem = 0

    override fun onBindViewHolder(holder: ConceptViewHolder, position: Int) {
        holder.topicBinding.apply {
            concept = getItem(position)
            concept?.let { concept ->
                root.setOnClickListener {
                    onItemSelected(concept)
                    notifyItemChanged(selectedItem)
                    selectedItem = position
                    (root as CardView).setCardBackgroundColor(
                        ContextCompat.getColor(root.context, R.color.selected_concept_color)
                    )
                }
                (root as CardView).setCardBackgroundColor(
                    ContextCompat.getColor(
                        root.context,
                        if (position == selectedItem) {
                            R.color.selected_concept_color
                        } else R.color.concept_color
                    )
                )
            }
            executePendingBindings()
        }

    }

}
