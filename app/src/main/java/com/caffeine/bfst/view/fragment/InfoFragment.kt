package com.caffeine.bfst.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.caffeine.bfst.R
import com.caffeine.bfst.databinding.FragmentInfoBinding
import com.caffeine.bfst.services.model.UserDetails
import com.caffeine.bfst.utils.Constants
import com.caffeine.bfst.utils.DataState
import com.caffeine.bfst.view.activity.HomeActivity
import com.caffeine.bfst.viewmodel.UserViewModel
import java.lang.Exception
import java.text.SimpleDateFormat

class InfoFragment : Fragment() {

    private val args: InfoFragmentArgs by navArgs()
    private val viewModel : UserViewModel by viewModels()
    private lateinit var binding : FragmentInfoBinding

    private lateinit var number : String
    private var name : String = ""
    private var bloodGroup : String = ""
    private var gender : String = ""
    private var dob : String = ""
    private var ldd : String = ""
    private lateinit var format : SimpleDateFormat
    private var checked = false

    private lateinit var bgList : ArrayList<TextView>
    private lateinit var genderList : ArrayList<TextView>

    private var listItemCount = 0

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater)

        format = SimpleDateFormat("dd-MMM-yyyy")

        bgList = ArrayList()
        genderList = ArrayList()
        addTextViewToList()
        textItemClickListeners()

        binding.neverDonated.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                binding.lddLayout.visibility = View.GONE
                ldd = "never"
                checked = true
            }
            else {
                binding.lddLayout.visibility = View.VISIBLE
                checked = false
            }
        }

        binding.createAccountBtn.setOnClickListener{
            initialize()
            val user = UserDetails(
                Constants.auth.uid!!,
                name,
                number,
                bloodGroup,
                gender,
                dob,
                ldd
            )
            if (informationIsValid() && dobIsValid()){
                if (!checked){
                    if (lddIsValid()){
                        if (Constants.internetAvailable(requireContext())){

                            viewModel.updateData(user)
                        }
                    }
                }
                else{
                    if (Constants.internetAvailable(requireContext())){
                        viewModel.updateData(user)
                    }
                }
            }
        }

        viewModel.userLiveData.observe(viewLifecycleOwner){
            when (it){
                is DataState.Loading -> {
                    binding.btnTxt.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }

                is DataState.Success -> {
                    Constants.intentToActivity(requireActivity(), HomeActivity::class.java)
                    binding.progressBar.visibility = View.GONE
                    binding.btnTxt.visibility = View.VISIBLE
                }

                is DataState.Failed -> {
                    Constants.showSnackBar(requireContext(), binding.root, it.message!!, Constants.SNACK_SHORT)
                    binding.progressBar.visibility = View.GONE
                    binding.btnTxt.visibility = View.VISIBLE
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {requireActivity().finish()}
        return binding.root
    }

    private fun initialize(){
        number = args.number
        name = binding.name.text.toString()
        dob = binding.date.selectedItem.toString() + "-" + binding.month.selectedItem.toString() + "-" + binding.year.selectedItem.toString()
        if (ldd != "never"){
            ldd = binding.ldDate.selectedItem.toString() + "-" + binding.ldMonth.selectedItem.toString() + "-" + binding.ldYear.selectedItem.toString()
        }
    }

    /*------------------------------------------------------TEXT VIEW FUNCTIONALITIES------------------------------------------------------*/

    private fun textItemClickListeners() {
        bgList[0].setOnClickListener{
            listItemCount = 0
            bloodGroup = bgList[listItemCount].text.toString()
            changeItemBackground(bgList)
        }

        bgList[1].setOnClickListener{
            listItemCount = 1
            bloodGroup = bgList[listItemCount].text.toString()
            changeItemBackground(bgList)
        }

        bgList[2].setOnClickListener{
            listItemCount = 2
            bloodGroup = bgList[listItemCount].text.toString()
            changeItemBackground(bgList)
        }

        bgList[3].setOnClickListener{
            listItemCount = 3
            bloodGroup = bgList[listItemCount].text.toString()
            changeItemBackground(bgList)
        }

        bgList[4].setOnClickListener{
            listItemCount = 4
            bloodGroup = bgList[listItemCount].text.toString()
            changeItemBackground(bgList)
        }

        bgList[5].setOnClickListener{
            listItemCount = 5
            bloodGroup = bgList[listItemCount].text.toString()
            changeItemBackground(bgList)
        }

        bgList[6].setOnClickListener{
            listItemCount = 6
            bloodGroup = bgList[listItemCount].text.toString()
            changeItemBackground(bgList)
        }

        bgList[7].setOnClickListener{
            listItemCount = 7
            bloodGroup = bgList[listItemCount].text.toString()
            changeItemBackground(bgList)
        }

        genderList[0].setOnClickListener{
            listItemCount = 0
            gender = genderList[listItemCount].text.toString()
            changeItemBackground(genderList)
        }

        genderList[1].setOnClickListener{
            listItemCount = 1
            gender = genderList[listItemCount].text.toString()
            changeItemBackground(genderList)
        }
    }

    private fun addTextViewToList(){
        bgList.add(binding.bgAP)
        bgList.add(binding.bgAN)
        bgList.add(binding.bgBP)
        bgList.add(binding.bgBN)
        bgList.add(binding.bgAbP)
        bgList.add(binding.bgAbN)
        bgList.add(binding.bgOP)
        bgList.add(binding.bgON)

        genderList.add(binding.male)
        genderList.add(binding.female)
    }

    private fun changeItemBackground(list : ArrayList<TextView>){
        for (i in 0 until list.size){
            if (i==listItemCount){
                list[i].setBackgroundResource(R.drawable.bg_light_red_10)
                list[i].setTextColor(resources.getColor(R.color.colorWhite))
            }
            else{
                list[i].setBackgroundResource(R.drawable.storke_grey_10)
                list[i].setTextColor(resources.getColor(R.color.colorGrey))
            }
        }
    }

    /*------------------------------------------------------TEXT VIEW FUNCTIONALITIES------------------------------------------------------*/

    private fun informationIsValid() : Boolean{
        return when {
            name.isEmpty() -> {
                Constants.showSnackBar(requireContext(), binding.root, "Please enter your name", Constants.SNACK_SHORT)
                false
            }
            bloodGroup.isEmpty() -> {
                Constants.showSnackBar(requireContext(), binding.root, "Select your blood group", Constants.SNACK_SHORT)
                false
            }
            gender.isEmpty() -> {
                Constants.showSnackBar(requireContext(), binding.root, "Select your gender", Constants.SNACK_SHORT)
                false
            }
            else -> true
        }
    }

    private fun dobIsValid() : Boolean {
        if (dob.isEmpty()) {
            Constants.showSnackBar(
                requireContext(),
                binding.root,
                "Define your date of birth",
                Constants.SNACK_SHORT
            )
            return false
        } else {
            return try {
                format.parse(dob)
                true
            } catch (e: Exception) {
                Constants.showSnackBar(
                    requireContext(),
                    binding.root,
                    "Invalid Date of Birth",
                    Constants.SNACK_SHORT
                )
                false
            }
        }
    }

    private fun lddIsValid() : Boolean {
        if (ldd.isEmpty()) {
            Constants.showSnackBar(
                requireContext(),
                binding.root,
                "Define your last donation date",
                Constants.SNACK_SHORT
            )
            return false
        } else {
            return try {
                format.parse(ldd)
                true
            } catch (e: Exception) {
                Constants.showSnackBar(
                    requireContext(),
                    binding.root,
                    "Invalid last donation date",
                    Constants.SNACK_SHORT
                )
                false
            }
        }
    }
}