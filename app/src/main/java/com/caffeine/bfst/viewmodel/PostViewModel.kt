package com.caffeine.bfst.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.caffeine.bfst.services.model.BloodModel
import com.caffeine.bfst.services.repository.PostRepository
import com.caffeine.bfst.utils.Constants
import com.caffeine.bfst.utils.DataState

class PostViewModel : ViewModel() {

    private val repository = PostRepository()

    private val bloodMutableLiveData = Constants.getMutableDataStateOfString()
    val bloodLiveData : LiveData<DataState<String>>
        get() = bloodMutableLiveData

    fun postABloodRequest(bloodModel: BloodModel){
        repository.postARequest(bloodModel, bloodMutableLiveData)
    }
}