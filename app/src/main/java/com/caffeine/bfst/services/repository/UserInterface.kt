package com.caffeine.bfst.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.bfst.services.model.UserDetails
import com.caffeine.bfst.utils.DataState

interface UserInterface {

    fun updateData(user : UserDetails, userMutableLiveData: MutableLiveData<DataState<String>>)

    suspend fun getUserData(userMutableLiveData: MutableLiveData<DataState<ArrayList<UserDetails>>>)

    suspend fun getMyInfo(userMutableLiveData: MutableLiveData<DataState<UserDetails>>)
}