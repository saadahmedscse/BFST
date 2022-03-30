package com.caffeine.bfst.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.caffeine.bfst.R
import com.caffeine.bfst.databinding.FragmentProfileBinding
import com.caffeine.bfst.utils.AlertDialog
import com.caffeine.bfst.utils.Constants
import com.caffeine.bfst.utils.DataState
import com.caffeine.bfst.utils.SuccessDialog
import com.caffeine.bfst.view.activity.AuthenticationActivity
import com.caffeine.bfst.viewmodel.PostViewModel
import com.caffeine.bfst.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private val userViewModel : UserViewModel by viewModels()
    private lateinit var last : String
    private var now : String = ""
    private var count = 0

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)

        userViewModel.getMyInfo()

        userViewModel.myLiveData.observe(viewLifecycleOwner){
            when (it){
                is DataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is DataState.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val user = it.data!!
                    binding.bg.text = user.bg
                    binding.name.text = user.name
                    binding.number.text = user.number
                    binding.location.text = user.location
                    last = user.ldd

                    if (last == "never"){
                        binding.ldDate.text = "Never Donated"
                        binding.txtCanDonate.text = "You can donate today!"
                        binding.donateBtn.setBackgroundResource(R.drawable.bg_light_red_5)
                        count = 1
                    }
                    else {
                        binding.ldDate.text = last
                        val sdf = SimpleDateFormat("dd-MMM-yyyy")
                        val today = sdf.format(Date())
                        now = today
                        val days: Long
                        try {
                            val TODAY = sdf.parse(today)
                            val LAST = sdf.parse(user.ldd)
                            days = ((TODAY.time - LAST.time) / (1000 * 60 * 60 * 24))
                            val mainDays = 120 - days

                            if (mainDays > 0){
                                binding.donateBtn.setBackgroundResource(R.drawable.bg_disabled_red_5)
                                binding.txtCanDonate.text = "Wait $mainDays more days to donate again"
                            }
                            else {
                                count = 1
                                binding.txtCanDonate.text = "You can donate today!"
                                binding.donateBtn.setBackgroundResource(R.drawable.bg_light_red_5)
                            }
                        }
                        catch (e : Exception){}
                    }
                }

                is DataState.Failed -> {
                    binding.progressBar.visibility = View.GONE
                    AlertDialog.showAlertDialog(requireContext(), it.message!!, "Close")
                }
            }

            binding.donateBtn.setOnClickListener{
                if (count == 1){
                    Constants.userReference.child(Constants.auth.uid!!).child("ldd")
                        .setValue(now).addOnCompleteListener{
                            if (it.isSuccessful){
                                SuccessDialog.showAlertDialog(requireContext(), "Congratulations, you have donated blood today", "Close")
                                count = 0
                            }
                            else {
                                AlertDialog.showAlertDialog(requireContext(), "An error occurred, try again later", "Clsoe")
                                count = 0
                            }
                        }
                }
            }

            binding.infoBtn.setOnClickListener{
                AlertDialog.showAlertDialog(requireContext(), "You can only donate after 120 days you donated", "Close")
            }

            binding.logoutBtn.setOnClickListener{
                Constants.auth.signOut()
                Constants.intentToActivity(requireActivity(), AuthenticationActivity::class.java)
            }
        }

        return binding.root
    }
}