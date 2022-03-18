package com.caffeine.bfst.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.caffeine.bfst.services.model.UserDetails
import com.caffeine.bfst.services.repository.UserRepository
import com.caffeine.bfst.utils.Constants
import com.caffeine.bfst.utils.DataState

class UserViewModel : ViewModel(){

    private val repository = UserRepository()

    private val userMutableLiveData = Constants.getMutableDataStateOfString()
    val userLiveData : LiveData<DataState<String>>
        get() = userMutableLiveData

    fun updateData(user : UserDetails){
        repository.updateData(user, userMutableLiveData)
    }
}