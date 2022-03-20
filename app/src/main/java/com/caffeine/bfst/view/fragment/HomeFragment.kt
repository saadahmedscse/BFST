package com.caffeine.bfst.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.caffeine.bfst.R
import com.caffeine.bfst.databinding.FragmentHomeBinding
import com.caffeine.bfst.utils.Constants
import com.caffeine.bfst.utils.DataState
import com.caffeine.bfst.view.adapter.PostAdapter
import com.caffeine.bfst.viewmodel.PostViewModel

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val viewModel : PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        binding.recyclerView.layoutManager = Constants.getVerticalLayoutManager(requireContext())
        viewModel.getBloodPosts()

        viewModel.postLiveData.observe(viewLifecycleOwner){
            when (it){
                is DataState.Loading -> {}

                is DataState.Success -> {
                    val adapter = PostAdapter(it.data!!, requireContext())
                    binding.recyclerView.adapter = adapter
                }

                is DataState.Failed -> {}
            }
        }

        return binding.root
    }
}