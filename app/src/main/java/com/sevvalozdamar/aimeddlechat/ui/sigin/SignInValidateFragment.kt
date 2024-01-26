package com.sevvalozdamar.aimeddlechat.ui.sigin

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.sevvalozdamar.aimeddlechat.R
import com.sevvalozdamar.aimeddlechat.databinding.FragmentValidateSignInBinding
import com.sevvalozdamar.aimeddlechat.repository.FirebaseRepository
import com.sevvalozdamar.aimeddlechat.utils.viewBinding
import java.util.concurrent.TimeUnit

class SignInValidateFragment : Fragment(R.layout.fragment_validate_sign_in) {

    private val binding by viewBinding(FragmentValidateSignInBinding::bind)
    private val args by navArgs<SignInValidateFragmentArgs>()
    var timeoutSeconds = 30L
    var resendingToken: ForceResendingToken? = null
    var verificationCode: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fullPhoneNumber = args.fullPhoneNumber

        with(binding) {
            sendOtp(fullPhoneNumber, false)

            btnValidate.setOnClickListener { _: View? ->
                val enteredOtp: String = etCode.text.toString()
                val credential =
                    verificationCode?.let {
                        PhoneAuthProvider.getCredential(it, enteredOtp)
                    }
                signIn(credential)
            }

            tvResendCode.setOnClickListener { _: View? ->
                sendOtp(
                    fullPhoneNumber,
                    true
                )
            }
        }
    }

    private fun sendOtp(phoneNumber: String?, isResend: Boolean) {
        startResendTimer()
        val builder = PhoneAuthOptions.newBuilder(FirebaseRepository.firebaseAuth())
            .setPhoneNumber(phoneNumber!!)
            .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(object : OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    signIn(phoneAuthCredential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(context, "Code verification failed", Toast.LENGTH_LONG).show()
                }

                override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                    super.onCodeSent(s, forceResendingToken)
                    verificationCode = s
                    resendingToken = forceResendingToken
                    Toast.makeText(context, "Code sent successfully", Toast.LENGTH_LONG).show()
                }
            })
        if (isResend) {
            resendingToken?.let { builder.setForceResendingToken(it).build() }?.let {
                PhoneAuthProvider.verifyPhoneNumber(
                    it
                )
            }
        } else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build())
        }
    }

    fun signIn(phoneAuthCredential: PhoneAuthCredential?) {
        if (phoneAuthCredential != null) {
            FirebaseRepository.firebaseAuth().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val action = SignInValidateFragmentDirections.validateSignInToSignInUserName(args.fullPhoneNumber)
                        findNavController().navigate(action)
                    } else {
                        Toast.makeText(context, "Code verification failed", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun startResendTimer() {
        with(binding) {
            tvResendCode.isEnabled = false
            val countDownTimer = object : CountDownTimer(timeoutSeconds * 1000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeoutSeconds--
                    tvResendCode.text = "Resend code in $timeoutSeconds seconds"
                }

                override fun onFinish() {
                    timeoutSeconds = 30L
                    tvResendCode.isEnabled = true
                    tvResendCode.text = "Resent the code"
                }
            }
            countDownTimer.start()
            tvResendCode.isEnabled = false
        }
    }

}