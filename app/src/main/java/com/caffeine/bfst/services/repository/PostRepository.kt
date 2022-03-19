package com.caffeine.bfst.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.bfst.services.model.BloodModel
import com.caffeine.bfst.utils.Constants
import com.caffeine.bfst.utils.DataState

class PostRepository : PostInterface {
    override fun postARequest(
        bloodModel: BloodModel,
        bloodMutableLiveData: MutableLiveData<DataState<String>>
    ) {
        bloodMutableLiveData.postValue(DataState.Loading())
        Constants.postReference.child(Constants.auth.uid!!).child(bloodModel.currentTime).setValue(bloodModel)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    bloodMutableLiveData.postValue(DataState.Success("Request for blood bag has been posted successfully. The post will be visible for three days"))
                }
                else {
                    bloodMutableLiveData.postValue(DataState.Failed(it.exception?.message))
                }
            }
    }

    override suspend fun getPosts(bloodMutableLiveData: MutableLiveData<DataState<ArrayList<BloodModel>>>) {
        TODO("Not yet implemented")
    }
}