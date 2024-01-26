package com.sevvalozdamar.aimeddlechat.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sevvalozdamar.aimeddlechat.R
import com.sevvalozdamar.aimeddlechat.databinding.FragmentProfileBinding
import com.sevvalozdamar.aimeddlechat.repository.FirebaseRepository
import com.sevvalozdamar.aimeddlechat.utils.viewBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogout.setOnClickListener {
                txtUserName.text = FirebaseRepository.firebaseAuth().currentUser.toString()
                FirebaseRepository.logout()
                findNavController().navigate(ProfileFragmentDirections.profileToSplash())
            }
        }
    }

}