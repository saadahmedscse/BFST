package com.caffeine.bfst.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.navArgs
import com.caffeine.bfst.R
import com.caffeine.bfst.databinding.FragmentInfoBinding
import com.caffeine.bfst.utils.Constants

class InfoFragment : Fragment() {

    private val args: InfoFragmentArgs by navArgs()
    private lateinit var binding : FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater)

        Constants.showToast(requireContext(), args.number, Constants.TOAST_SHORT)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {requireActivity().finish()}
        return binding.root
    }
}