package com.sevvalozdamar.aimeddlechat.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.sevvalozdamar.aimeddlechat.R
import com.sevvalozdamar.aimeddlechat.databinding.FragmentHomeBinding
import com.sevvalozdamar.aimeddlechat.model.firebase.Chatroom
import com.sevvalozdamar.aimeddlechat.repository.FirebaseRepository
import com.sevvalozdamar.aimeddlechat.utils.viewBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupChatroomAdapter()

    }

    private fun setupChatroomAdapter(){
        with(binding){
            val query: Query = FirebaseRepository.getAllChatroomCollection()
                .whereArrayContains("userIds", FirebaseRepository.currentUserId().toString())
                .orderBy("lastMessageTimestamp", Query.Direction.DESCENDING)

            val options = FirestoreRecyclerOptions.Builder<Chatroom>()
                .setQuery(query, Chatroom::class.java)
                .build()

            //val adapter = RecentChatAdapter(options, onChatroomClick = ::onChatroomClick)
            //rvChatrooms.adapter = adapter
            //adapter.startListening()
        }
    }

    private fun onChatroomClick(chatroomId: String) {
        println("Home ChatrromId: " + chatroomId)
        findNavController().navigate(HomeFragmentDirections.homeToDetail(chatroomId))
    }
}