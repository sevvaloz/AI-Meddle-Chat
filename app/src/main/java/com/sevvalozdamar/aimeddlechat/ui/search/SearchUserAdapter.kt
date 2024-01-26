package com.sevvalozdamar.aimeddlechat.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.sevvalozdamar.aimeddlechat.R
import com.sevvalozdamar.aimeddlechat.databinding.ItemSearchUserBinding
import com.sevvalozdamar.aimeddlechat.model.firebase.User
import com.sevvalozdamar.aimeddlechat.repository.FirebaseRepository

class SearchUserAdapter(
    options: FirestoreRecyclerOptions<User>,
    private val onUserClick: (String) -> Unit
) : FirestoreRecyclerAdapter<User, SearchUserAdapter.SearchViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchUserBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onUserClick
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int, model: User) {
        holder.bind(getItem(position))
    }

    class SearchViewHolder(
        private val binding: ItemSearchUserBinding,
        private val onUserClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            with(binding) {
                itemUserPhoneNumber.text = user.userPhone
                itemUserImage.setImageResource(R.drawable.asset_person)
                root.setOnClickListener {
                    onUserClick(user.userId!!)
                }
                if (FirebaseRepository.currentUserId() == user.userId) {
                    itemUsername.text = "${user.userName} (me)"
                    root.isEnabled = false
                } else {
                    itemUsername.text = user.userName
                }
            }
        }
    }
}