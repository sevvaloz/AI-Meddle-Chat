package com.sevvalozdamar.aimeddlechat.ui.chatdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sevvalozdamar.aimeddlechat.databinding.ItemSuggestionMessageBinding
import com.sevvalozdamar.aimeddlechat.model.base.SuggestionMessage

class SuggestionMessagesAdapter(
    private val onSuggestionMessageClick: (String) -> Unit
): ListAdapter<SuggestionMessage, SuggestionMessagesAdapter.SearchViewHolder> (ProductDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSuggestionMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onSuggestionMessageClick
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) = holder.bind(getItem(position))

    class SearchViewHolder(
        private val binding: ItemSuggestionMessageBinding,
        private val onSuggestionMessageClick: (String) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(suggestionMessage: SuggestionMessage) {
            with(binding) {
                tvSuggestionMessage.text = suggestionMessage.message

                tvSuggestionMessage.setOnClickListener {
                     onSuggestionMessageClick(suggestionMessage.message)
                }
            }
        }
    }

    class ProductDiffUtilCallBack : DiffUtil.ItemCallback<SuggestionMessage>() {
        override fun areItemsTheSame(oldItem: SuggestionMessage, newItem: SuggestionMessage): Boolean {
            return oldItem.message == newItem.message
        }

        override fun areContentsTheSame(oldItem: SuggestionMessage, newItem: SuggestionMessage): Boolean {
            return oldItem == newItem
        }
    }

}