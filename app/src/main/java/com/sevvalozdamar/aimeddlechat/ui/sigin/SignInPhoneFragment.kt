package com.sevvalozdamar.aimeddlechat.ui.sigin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sevvalozdamar.aimeddlechat.R
import com.sevvalozdamar.aimeddlechat.databinding.FragmentSignInPhoneBinding
import com.sevvalozdamar.aimeddlechat.repository.FirebaseRepository
import com.sevvalozdamar.aimeddlechat.utils.viewBinding

class SignInPhoneFragment : Fragment(R.layout.fragment_sign_in_phone) {

    private val binding by viewBinding(FragmentSignInPhoneBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(FirebaseRepository.isCurrentUserExist()){
            val action = SignInPhoneFragmentDirections.signInPhoneToHome()
            findNavController().navigate(action)
        }

        with(binding) {
            codePicker.registerCarrierNumberEditText(etPhoneNumber)
            btnSendCode.setOnClickListener {
                if (!codePicker.isValidFullNumber) {
                    etPhoneNumber.error = "Phone number not valid"
                    return@setOnClickListener
                }
                findNavController().navigate(
                    SignInPhoneFragmentDirections.signInPhoneToValidateSignIn(
                        codePicker.fullNumberWithPlus
                    )
                )
            }
        }
    }
}