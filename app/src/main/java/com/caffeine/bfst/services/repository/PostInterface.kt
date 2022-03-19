package com.caffeine.bfst.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.bfst.services.model.BloodModel
import com.caffeine.bfst.utils.DataState

interface PostInterface {

    fun postARequest(bloodModel: BloodModel, bloodMutableLiveData: MutableLiveData<DataState<String>>)

    suspend fun getPosts(bloodMutableLiveData: MutableLiveData<DataState<ArrayList<BloodModel>>>)
}