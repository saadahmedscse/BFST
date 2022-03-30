package com.caffeine.bfst.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.caffeine.bfst.R
import com.caffeine.bfst.databinding.FragmentPostBinding
import com.caffeine.bfst.services.model.BloodModel
import com.caffeine.bfst.utils.AlertDialog
import com.caffeine.bfst.utils.Constants
import com.caffeine.bfst.utils.DataState
import com.caffeine.bfst.utils.SuccessDialog
import com.caffeine.bfst.viewmodel.PostViewModel
import com.caffeine.bfst.viewmodel.UserViewModel
import java.lang.Exception
import java.text.SimpleDateFormat

class PostFragment : Fragment() {

    private lateinit var binding : FragmentPostBinding
    private val viewModel : PostViewModel by viewModels()
    private val userViewModel : UserViewModel by viewModels()

    private var name : String = ""
    private var quantity : String = ""
    private var date : String = ""
    private var time : String = ""
    private var hospital : String = ""
    private var location : String = ""
    private var desc : String = ""
    private var currentTime : String = ""
    private var bloodGroup : String = ""
    private var poster : String = ""
    private var number : String = ""

    private lateinit var format : SimpleDateFormat

    private lateinit var bgList : ArrayList<TextView>
    private lateinit var bella : ArrayList<TextView>
    private var listItemCount = 0

    private var bag = 0

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostBinding.inflate(inflater)
        userViewModel.getMyInfo()
        userViewModel.myLiveData.observe(viewLifecycleOwner){
            when (it){
                is DataState.Loading -> {}

                is DataState.Success -> {
                    poster = it.data!!.name
                    number = it.data.number
                }

                is DataState.Failed -> {}
            }
        }

        binding.plus.setOnClickListener{
            if (bag < 10){
                bag++
                binding.quantity.text = "$bag Bag"
            }
        }

        binding.minus.setOnClickListener{
            if (bag > 0){
                bag--
                binding.quantity.text = "$bag Bag"
            }
        }

        bgList = ArrayList()
        bella = ArrayList()
        addTextViewToList()
        textItemClickListeners()
        format = SimpleDateFormat("dd-MMM-yyyy")

        binding.postBtn.setOnClickListener{
            initialize()
            if (validate()){
                if (Constants.internetAvailable(requireContext())){
                    val blood = BloodModel(
                        currentTime,
                        Constants.auth.uid!!,
                        name,
                        poster,
                        number,
                        bloodGroup,
                        quantity,
                        date,
                        time,
                        hospital,
                        location,
                        desc,
                        currentTime
                    )
                    viewModel.postABloodRequest(blood)

                    viewModel.bloodLiveData.observe(viewLifecycleOwner){
                        when (it) {
                            is DataState.Loading -> {
                                binding.btnTxt.visibility = View.GONE
                                binding.progressBar.visibility = View.VISIBLE
                            }

                            is DataState.Success -> {
                                SuccessDialog.showAlertDialog(requireContext(), it.data!!, "Close")
                                binding.progressBar.visibility = View.GONE
                                binding.btnTxt.visibility = View.VISIBLE
                            }

                            is DataState.Failed -> {
                                Constants.showSnackBar(requireContext(), binding.root, it.message!!, Constants.SNACK_LONG)
                                binding.progressBar.visibility = View.GONE
                                binding.btnTxt.visibility = View.VISIBLE}
                        }
                    }
                }
                else {
                    Constants.showSnackBar(requireContext(), binding.root, "No internet connection", Constants.SNACK_SHORT)
                }
            }
        }

        return binding.root
    }

    private fun initialize(){
        currentTime = System.currentTimeMillis().toString()
        name = binding.name.text.toString()
        date = binding.date.selectedItem.toString() + "-" + binding.month.selectedItem.toString() + "-" + binding.year.selectedItem.toString()
        hospital = binding.hospital.text.toString()
        quantity = bag.toString()
        location = binding.location.text.toString()
        desc = binding.desc.text.toString()
    }

    private fun validate() : Boolean{
        if (name.isEmpty()){
            Constants.showSnackBar(requireContext(), binding.root, "Please enter petient name", Constants.SNACK_SHORT)
            return false
        }

        else if (bloodGroup.isEmpty()){
            Constants.showSnackBar(requireContext(), binding.root, "Please choose blood group", Constants.SNACK_SHORT)
            return false
        }

        else if (quantity.isEmpty() || bag == 0){
            Constants.showSnackBar(requireContext(), binding.root, "Please choose quantity", Constants.SNACK_SHORT)
            return false
        }

        else if (!dateValidate()){
            return false
        }

        else if (time.isEmpty()){
            Constants.showSnackBar(requireContext(), binding.root, "Please choose donation time", Constants.SNACK_SHORT)
            return false
        }

        else if (hospital.isEmpty()){
            Constants.showSnackBar(requireContext(), binding.root, "Please define hospital name", Constants.SNACK_SHORT)
            return false
        }

        else if (location.isEmpty()){
            Constants.showSnackBar(requireContext(), binding.root, "Please define hospital location", Constants.SNACK_SHORT)
            return false
        }

        else if (desc.isEmpty()){
            Constants.showSnackBar(requireContext(), binding.root, "Please write a short description about petient", Constants.SNACK_SHORT)
            return false
        }

        else if (poster.isEmpty() || number.isEmpty()){
            AlertDialog.showAlertDialog(requireContext(), "Something went wrong, please try again later", "Close")
            return false
        }

        return true
    }

    private fun dateValidate() : Boolean {
        if (date.isEmpty()) {
            Constants.showSnackBar(
                requireContext(),
                binding.root,
                "Define blood donation date",
                Constants.SNACK_SHORT
            )
            return false
        } else {
            return try {
                format.parse(date)
                true
            } catch (e: Exception) {
                Constants.showSnackBar(
                    requireContext(),
                    binding.root,
                    "You entered an invalid date",
                    Constants.SNACK_SHORT
                )
                false
            }
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

        bella[0].setOnClickListener{
            listItemCount = 0
            time = bella[listItemCount].text.toString()
            changeItemBackground(bella)
        }

        bella[1].setOnClickListener{
            listItemCount = 1
            time = bella[listItemCount].text.toString()
            changeItemBackground(bella)
        }

        bella[2].setOnClickListener{
            listItemCount = 2
            time = bella[listItemCount].text.toString()
            changeItemBackground(bella)
        }

        bella[3].setOnClickListener{
            listItemCount = 3
            time = bella[listItemCount].text.toString()
            changeItemBackground(bella)
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

        bella.add(binding.morning)
        bella.add(binding.noon)
        bella.add(binding.evening)
        bella.add(binding.night)
    }

    private fun changeItemBackground(list : ArrayList<TextView>){
        for (i in 0 until list.size){
            if (i==listItemCount){
                list[i].setBackgroundResource(R.drawable.bg_light_red_5)
                list[i].setTextColor(resources.getColor(R.color.colorWhite))
            }
            else{
                list[i].setBackgroundResource(R.drawable.bg_white_5)
                list[i].setTextColor(resources.getColor(R.color.colorGrey))
            }
        }
    }

    /*------------------------------------------------------TEXT VIEW FUNCTIONALITIES------------------------------------------------------*/
}