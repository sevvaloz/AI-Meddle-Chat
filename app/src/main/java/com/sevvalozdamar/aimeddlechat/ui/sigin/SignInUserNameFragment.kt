package com.sevvalozdamar.aimeddlechat.ui.sigin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.Timestamp
import com.sevvalozdamar.aimeddlechat.R
import com.sevvalozdamar.aimeddlechat.databinding.FragmentSignInUserNameBinding
import com.sevvalozdamar.aimeddlechat.model.firebase.User
import com.sevvalozdamar.aimeddlechat.repository.FirebaseRepository
import com.sevvalozdamar.aimeddlechat.utils.viewBinding

class SignInUserNameFragment : Fragment(R.layout.fragment_sign_in_user_name) {

    private val binding by viewBinding(FragmentSignInUserNameBinding::bind)
    private val args by navArgs<SignInUserNameFragmentArgs>()
    private var _username: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUsername()

        with(binding) {
             _username = etUserName.text.toString()
            btnToHome.setOnClickListener {
                setUsername()
            }
        }
    }

    private fun setUsername() {
        with(binding) {
            val username = etUserName.text.toString()
            if (username.length < 3) {
                etUserName.error = "Username length should be at least 3 characters"
                return
            }
            if (_username?.isEmpty() == true) {
                createUsername()
            } else if (_username?.isNotEmpty() == true) {
                updateUsername()
            }

            val action = SignInUserNameFragmentDirections.signInUserNameToHome()
            findNavController().navigate(action)

        }
    }

    private fun getUsername() {
        with(binding) {
            FirebaseRepository.getCurrentUserDetails().get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null) {
                            val username = document.getString("userName")
                            etUserName.setText(username)
                        }
                    }
                }
        }
    }

    private fun createUsername() {
        with(binding) {
            val fullPhoneNumber = args.fullPhoneNumber
            val username = etUserName.text.toString()

            val newUser = User(FirebaseRepository.currentUserId(), fullPhoneNumber, username, Timestamp.now())
            FirebaseRepository.getCurrentUserDetails().set(newUser)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("FIRESTORE", "Your username created successfully")
                    } else {
                        Log.e("FIRESTORE", "Your username can not be created")
                    }
                }
        }
    }

    private fun updateUsername() {
        with(binding) {
            val username = hashMapOf<String, Any>(
                "userName" to etUserName.text.toString()
            )
            FirebaseRepository.getCurrentUserDetails().update(username)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("FIRESTORE", "Your username updated successfully")
                    } else {
                        Log.e("FIRESTORE", "Your username can not be updated")
                    }
                }
        }
    }
}