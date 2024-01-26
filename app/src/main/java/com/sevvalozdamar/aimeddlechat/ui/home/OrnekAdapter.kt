/*
package com.sevvalozdamar.aimeddlechat.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easychat.ChatActivity
import com.example.easychat.R
import com.example.easychat.model.ChatroomModel
import com.example.easychat.model.UserModel
import com.example.easychat.utils.AndroidUtil
import com.example.easychat.utils.FirebaseUtil
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

package com.example.easychat.adapter
import com.example.easychat.ChatActivity
import com.example.easychat.R
import com.example.easychat.model.ChatroomModel
import com.example.easychat.model.UserModel
import com.example.easychat.utils.AndroidUtil
import com.example.easychat.utils.FirebaseUtil

class RecentChatRecyclerAdapter(
    options: FirestoreRecyclerOptions<ChatroomModel?>,
    var context: Context
) :
    FirestoreRecyclerAdapter<ChatroomModel, com.example.easychat.adapter.RecentChatRecyclerAdapter.ChatroomModelViewHolder>(
        options
    ) {
    protected override fun onBindViewHolder(
        holder: com.example.easychat.adapter.RecentChatRecyclerAdapter.ChatroomModelViewHolder,
        position: Int,
        model: ChatroomModel
    ) {
        FirebaseUtil.getOtherUserFromChatroom(model.getUserIds())
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful()) {
                    val lastMessageSentByMe: Boolean =
                        model.getLastMessageSenderId().equals(FirebaseUtil.currentUserId())
                    val otherUserModel: UserModel = task.getResult().toObject(UserModel::class.java)
                    FirebaseUtil.getOtherProfilePicStorageRef(otherUserModel.getUserId())
                        .getDownloadUrl()
                        .addOnCompleteListener { t ->
                            if (t.isSuccessful()) {
                                val uri: Uri = t.getResult()
                                AndroidUtil.setProfilePic(context, uri, holder.profilePic)
                            }
                        }
                    holder.usernameText.setText(otherUserModel.getUsername())
                    if (lastMessageSentByMe) holder.lastMessageText.setText("You : " + model.getLastMessage()) else holder.lastMessageText.setText(
                        model.getLastMessage()
                    )
                    holder.lastMessageTime.setText(FirebaseUtil.timestampToString(model.getLastMessageTimestamp()))
                    holder.itemView.setOnClickListener(View.OnClickListener { v: View? ->
                        //navigate to chat activity
                        val intent = Intent(context, ChatActivity::class.java)
                        AndroidUtil.passUserModelAsIntent(intent, otherUserModel)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    })
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): com.example.easychat.adapter.RecentChatRecyclerAdapter.ChatroomModelViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.recent_chat_recycler_row, parent, false)
        return com.example.easychat.adapter.RecentChatRecyclerAdapter.ChatroomModelViewHolder(view)
    }

    internal inner class ChatroomModelViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var usernameText: TextView
        var lastMessageText: TextView
        var lastMessageTime: TextView
        var profilePic: ImageView

        init {
            usernameText = itemView.findViewById<TextView>(R.id.user_name_text)
            lastMessageText = itemView.findViewById<TextView>(R.id.last_message_text)
            lastMessageTime = itemView.findViewById<TextView>(R.id.last_message_time_text)
            profilePic = itemView.findViewById<ImageView>(R.id.profile_pic_image_view)
        }
    }
}*/
