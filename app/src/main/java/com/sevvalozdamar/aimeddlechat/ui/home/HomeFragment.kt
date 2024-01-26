package com.sevvalozdamar.aimeddlechat.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sevvalozdamar.aimeddlechat.R
import com.sevvalozdamar.aimeddlechat.databinding.FragmentHomeBinding
import com.sevvalozdamar.aimeddlechat.utils.viewBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgNoMessage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.asset_no_message))
    }
}