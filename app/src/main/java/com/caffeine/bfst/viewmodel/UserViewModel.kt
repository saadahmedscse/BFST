package com.caffeine.bfst.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caffeine.bfst.services.model.UserDetails
import com.caffeine.bfst.services.repository.UserRepository
import com.caffeine.bfst.utils.Constants
import com.caffeine.bfst.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel : ViewModel(){

    private val repository = UserRepository()

    private val userMutableLiveData = Constants.getMutableDataStateOfString()
    val userLiveData : LiveData<DataState<String>>
        get() = userMutableLiveData

    private val donorMutableLiveData = Constants.getMutableDataStateOfUserDetails()
    val donorLiveData : LiveData<DataState<ArrayList<UserDetails>>>
        get() = donorMutableLiveData

    fun updateData(user : UserDetails){
        repository.updateData(user, userMutableLiveData)
    }

    fun getDonors(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserData(donorMutableLiveData)
        }
    }
}