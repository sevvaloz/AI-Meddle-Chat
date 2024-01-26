package com.sevvalozdamar.aimeddlechat.ui.profile

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sevvalozdamar.aimeddlechat.R
import com.sevvalozdamar.aimeddlechat.databinding.FragmentProfileBinding
import com.sevvalozdamar.aimeddlechat.repository.FirebaseRepository
import com.sevvalozdamar.aimeddlechat.utils.gone
import com.sevvalozdamar.aimeddlechat.utils.viewBinding
import com.sevvalozdamar.aimeddlechat.utils.visible

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            progressBar.visible()
            FirebaseRepository.getCurrentUserDetails().get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null) {
                            progressBar.gone()
                            txtUserName.text = document.getString("userName")
                            txtUserPhone.text = FirebaseRepository.firebaseAuth().currentUser?.phoneNumber
                            imgUserImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.asset_user))
                            btnLogout.visible()
                        }
                    }
                }
            btnLogout.setOnClickListener {
                FirebaseRepository.logout()
                findNavController().navigate(ProfileFragmentDirections.profileToSplash())
            }
        }
    }

}