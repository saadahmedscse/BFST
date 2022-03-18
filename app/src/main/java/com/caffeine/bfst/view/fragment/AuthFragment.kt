package com.caffeine.bfst.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.caffeine.bfst.R
import com.caffeine.bfst.databinding.FragmentAuthBinding
import com.caffeine.bfst.utils.AlertDialog
import com.caffeine.bfst.utils.Constants
import com.caffeine.bfst.utils.DataState
import com.caffeine.bfst.view.activity.HomeActivity
import com.caffeine.bfst.viewmodel.AuthViewModel

class AuthFragment : Fragment() {

    private lateinit var binding : FragmentAuthBinding
    private lateinit var number : String
    private lateinit var otp : String
    private var count = 1

    //verificationID receive from viewmodel
    private lateinit var verificationID : String

    private val authViewModel : AuthViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater)

        binding.loginBtn.setOnClickListener{
            if (count == 1){
                number = binding.number.text.toString()
                if (numberIsValid()){
                    if (Constants.internetAvailable(requireContext())){
                        authViewModel.sendVerificationCode(requireActivity(), number)
                    }
                    else {
                        Constants.showSnackBar(requireContext(), binding.root, "No internet connection available", Constants.SNACK_SHORT)
                    }
                }
            }

            else if (count == 0){
                otp = binding.otp.text.toString()
                if (otpIsValid()){
                    if (Constants.internetAvailable(requireContext())){
                        authViewModel.verifyOTP(verificationID, otp)
                    }
                    else {
                        Constants.showSnackBar(requireContext(), binding.root, "No internet connection available", Constants.SNACK_SHORT)
                    }
                }
            }
        }

        authViewModel.authLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    binding.txtLogin.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }

                is DataState.Success -> {
                    if (it.data!! != "noInfo" || it.data != "hasInfo"){
                        verificationID = it.data
                        binding.otpPlaceholder.visibility = View.GONE
                        binding.otp.visibility = View.VISIBLE
                        binding.txtLogin.text = "Verify"
                    }
                    if (it.data == "noInfo") {
                        val action = AuthFragmentDirections.authToInfo("+88$number")
                        Navigation.findNavController(binding.root).navigate(action)
                    }
                    if (it.data == "hasInfo"){
                        Constants.intentToActivity(requireActivity(), HomeActivity::class.java)
                    }
                    if (count == 1){
                        Constants.showSnackBar(requireContext(), binding.root, "Code sent successfully", Constants.SNACK_LONG)
                    }
                    count = 0
                    binding.progressBar.visibility = View.GONE
                    binding.txtLogin.visibility = View.VISIBLE
                }

                is DataState.Failed -> {
                    binding.progressBar.visibility = View.GONE
                    binding.txtLogin.visibility = View.VISIBLE
                    AlertDialog.showAlertDialog(requireContext(), it.message!!, "Close")
                }
            }
        }

        return binding.root
    }

    private fun numberIsValid() : Boolean{
        if (number.isEmpty()){
            Constants.showSnackBar(requireContext(), binding.root, "Please enter your number", Constants.SNACK_SHORT)
            return false
        }

        else if (number.length < 11 || !number.startsWith("01")){
            Constants.showSnackBar(requireContext(), binding.root, "Invalid mobile number", Constants.SNACK_SHORT)
            return false
        }

        else return true
    }

    private fun otpIsValid() : Boolean{
        if (otp.isEmpty()){
            Constants.showSnackBar(requireContext(), binding.root, "Please enter OTP code", Constants.SNACK_SHORT)
            return false
        }

        else if (otp.length < 6){
            Constants.showSnackBar(requireContext(), binding.root, "Invalid OTP", Constants.SNACK_SHORT)
            return false
        }

        else return true
    }
}