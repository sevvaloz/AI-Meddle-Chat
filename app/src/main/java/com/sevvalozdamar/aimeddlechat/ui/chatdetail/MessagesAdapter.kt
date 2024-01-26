package com.sevvalozdamar.aimeddlechat.ui.chatdetail

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.sevvalozdamar.aimeddlechat.databinding.ItemMessageBinding
import com.sevvalozdamar.aimeddlechat.model.firebase.Message
import com.sevvalozdamar.aimeddlechat.repository.FirebaseRepository
import com.sevvalozdamar.aimeddlechat.repository.FirebaseRepository.timestampToDate
import com.sevvalozdamar.aimeddlechat.utils.gone
import com.sevvalozdamar.aimeddlechat.utils.visible

class MessagesAdapter(
    options: FirestoreRecyclerOptions<Message>
) : FirestoreRecyclerAdapter<Message, MessagesAdapter.MessagesViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        return MessagesViewHolder(
            ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int, model: Message) {
        holder.bind(getItem(position))
    }

    class MessagesViewHolder(
        private val binding: ItemMessageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) {
            with(binding) {
                if (message.senderId.equals(FirebaseRepository.currentUserId())) {
                    llLeftChat.gone()
                    tvLeftChatTime.gone()
                    llRightChat.visible()
                    tvRightChat.text = message.messageText
                    tvRightChatTime.text = message.timestamp?.let { timestampToDate(it) }
                } else {
                    llRightChat.gone()
                    tvRightChatTime.gone()
                    llLeftChat.visible()
                    tvLeftChat.text = message.messageText
                    tvLeftChatTime.text = message.timestamp?.let { timestampToDate(it) }
                }
            }
        }
    }

}