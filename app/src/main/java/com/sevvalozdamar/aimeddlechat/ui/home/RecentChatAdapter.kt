package com.sevvalozdamar.aimeddlechat.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.sevvalozdamar.aimeddlechat.R
import com.sevvalozdamar.aimeddlechat.databinding.ItemChatroomBinding
import com.sevvalozdamar.aimeddlechat.model.firebase.Chatroom
import com.sevvalozdamar.aimeddlechat.model.firebase.User
import com.sevvalozdamar.aimeddlechat.repository.FirebaseRepository

class RecentChatAdapter(
    options: FirestoreRecyclerOptions<Chatroom>,
    private val onChatroomClick: (String) -> Unit
) : FirestoreRecyclerAdapter<Chatroom, RecentChatAdapter.RecentChatViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentChatViewHolder {
        return RecentChatViewHolder(
            ItemChatroomBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onChatroomClick
        )
    }

    override fun onBindViewHolder(holder: RecentChatViewHolder, position: Int, model: Chatroom) {
        holder.bind(getItem(position))
    }

    class RecentChatViewHolder(
        private val binding: ItemChatroomBinding,
        private val onChatroomClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(chatroom: Chatroom) {
            with(binding) {
                FirebaseRepository.getOtherUserFromChatroom(chatroom.userIds!!)
                        .get().addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                val isLastMessageSentByMe: Boolean = chatroom.lastMessageSenderId.equals(FirebaseRepository.currentUserId())
                                val otherUser: User? = task.result.toObject(User::class.java)

                                itemUsername.text = otherUser!!.userName
                                itemMessageTime.text = FirebaseRepository.timestampToDate(chatroom.lastMessageTimestamp!!)
                                itemUserImage.setImageResource(R.drawable.asset_person)

                                if (isLastMessageSentByMe){
                                    itemUserMessage.text = "You: ${chatroom.lastMessage}"
                                } else {
                                    itemUserMessage.text = "${otherUser.userName}: ${chatroom.lastMessage}"
                                }

                                root.setOnClickListener {
                                    onChatroomClick(chatroom.chatroomId!!)
                                }
                            }
                        }
            }
        }
    }

}