package com.sevvalozdamar.aimeddlechat.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.sevvalozdamar.aimeddlechat.R
import com.sevvalozdamar.aimeddlechat.databinding.FragmentSearchBinding
import com.sevvalozdamar.aimeddlechat.model.firebase.Chatroom
import com.sevvalozdamar.aimeddlechat.model.firebase.User
import com.sevvalozdamar.aimeddlechat.repository.FirebaseRepository
import com.sevvalozdamar.aimeddlechat.utils.viewBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            svSearchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(search: String?): Boolean {
                    if ((search?.length ?: 0) >= 3) {
                        performSearch(search!!)
                    }
                    return false
                }

                override fun onQueryTextChange(newSearch: String?): Boolean {
                    if ((newSearch?.length ?: 0) >= 3) {
                        performSearch(newSearch!!)
                    }
                    return false
                }
            })

        }
    }

    private fun performSearch(searchTerm: String) {
        with(binding) {
            val query = FirebaseRepository.getAllUserDetails()
                .whereGreaterThanOrEqualTo("userName", searchTerm)
                .whereLessThanOrEqualTo("userName", searchTerm + '\uf8ff')

            val options: FirestoreRecyclerOptions<User> =
                FirestoreRecyclerOptions.Builder<User>()
                    .setQuery(query, User::class.java).build()

            val adapter = SearchUserAdapter(options, onUserClick = ::onUserClick)
            rvSearchUser.adapter = adapter
            adapter.startListening()
        }
    }

    private fun onUserClick(userId: String) {
        val chatroomId = FirebaseRepository.currentUserId() + "_" + userId
        //println("Search UserId: " + userId)
        //println("Search ChatroomId: " + chatroomId)
        findNavController().navigate(SearchFragmentDirections.searchToDetail(chatroomId))
    }
}