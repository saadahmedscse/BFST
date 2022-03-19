package com.caffeine.bfst.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.caffeine.bfst.R
import com.caffeine.bfst.databinding.FragmentDonerBinding
import com.caffeine.bfst.services.model.UserDetails
import com.caffeine.bfst.utils.Constants
import com.caffeine.bfst.utils.DataState
import com.caffeine.bfst.view.adapter.DonorAdapter
import com.caffeine.bfst.viewmodel.UserViewModel

class DonerFragment : Fragment() {

    private lateinit var binding : FragmentDonerBinding
    private val viewModel : UserViewModel by viewModels()

    private var bloodGroup : String = ""
    private lateinit var bgList : ArrayList<TextView>
    private var listItemCount = 0

    private lateinit var user : ArrayList<UserDetails>
    private lateinit var newUser : ArrayList<UserDetails>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDonerBinding.inflate(layoutInflater)

        binding.recyclerView.layoutManager = Constants.getVerticalLayoutManager(requireContext())
        bgList = ArrayList()
        user = ArrayList()
        newUser = ArrayList()
        addTextViewToList()
        textItemClickListeners()

        viewModel.getDonors()

        viewModel.donorLiveData.observe(viewLifecycleOwner){
            when(it) {
                is DataState.Loading -> {}

                is DataState.Success -> {
                    user = it.data!!
                    val adapter = DonorAdapter(it.data, requireContext())

                    if (adapter.itemCount < 1){
                        binding.noDataLayout.visibility = View.VISIBLE
                    }
                    else binding.noDataLayout.visibility = View.GONE

                    binding.recyclerView.adapter = adapter
                }

                is DataState.Failed -> {}
            }
        }

        return binding.root
    }

    /*------------------------------------------------------TEXT VIEW FUNCTIONALITIES------------------------------------------------------*/

    private fun textItemClickListeners() {
        bgList[0].setOnClickListener{
            listItemCount = 0
            bloodGroup = bgList[listItemCount].text.toString()
            filterDonors(bloodGroup)
            changeItemBackground(bgList)
        }

        bgList[1].setOnClickListener{
            listItemCount = 1
            bloodGroup = bgList[listItemCount].text.toString()
            filterDonors(bloodGroup)
            changeItemBackground(bgList)
        }

        bgList[2].setOnClickListener{
            listItemCount = 2
            bloodGroup = bgList[listItemCount].text.toString()
            filterDonors(bloodGroup)
            changeItemBackground(bgList)
        }

        bgList[3].setOnClickListener{
            listItemCount = 3
            bloodGroup = bgList[listItemCount].text.toString()
            filterDonors(bloodGroup)
            changeItemBackground(bgList)
        }

        bgList[4].setOnClickListener{
            listItemCount = 4
            bloodGroup = bgList[listItemCount].text.toString()
            filterDonors(bloodGroup)
            changeItemBackground(bgList)
        }

        bgList[5].setOnClickListener{
            listItemCount = 5
            bloodGroup = bgList[listItemCount].text.toString()
            filterDonors(bloodGroup)
            changeItemBackground(bgList)
        }

        bgList[6].setOnClickListener{
            listItemCount = 6
            bloodGroup = bgList[listItemCount].text.toString()
            filterDonors(bloodGroup)
            changeItemBackground(bgList)
        }

        bgList[7].setOnClickListener{
            listItemCount = 7
            bloodGroup = bgList[listItemCount].text.toString()
            filterDonors(bloodGroup)
            changeItemBackground(bgList)
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

    /*------------------------------------------------------FLITER BLOOD DONORS------------------------------------------------------*/

    private fun filterDonors(text : String){
        newUser.clear()
        for (bg in user){
            if (bg.bg == text){
                newUser.add(bg)
            }
        }

        val adapter = DonorAdapter(newUser, requireContext())

        if (adapter.itemCount < 1){
            binding.noDataLayout.visibility = View.VISIBLE
        }
        else binding.noDataLayout.visibility = View.GONE

        binding.recyclerView.adapter = adapter
    }

    /*------------------------------------------------------FLITER BLOOD DONORS------------------------------------------------------*/
}