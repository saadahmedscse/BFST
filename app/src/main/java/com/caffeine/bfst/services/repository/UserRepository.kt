package com.caffeine.bfst.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.bfst.services.model.UserDetails
import com.caffeine.bfst.utils.Constants
import com.caffeine.bfst.utils.DataState

class UserRepository : UserInterface {

    override fun updateData(user: UserDetails, userMutableLiveData: MutableLiveData<DataState<String>>) {
        userMutableLiveData.postValue(DataState.Loading())
        Constants.userReference.child(Constants.auth.uid!!).setValue(user)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    userMutableLiveData.postValue(DataState.Success("User Created Successfully"))
                }
                else{
                    userMutableLiveData.postValue(DataState.Failed(it.exception?.message))
                }
            }
    }

    override suspend fun getUserData(userMutableLiveData: MutableLiveData<DataState<ArrayList<UserDetails>>>) {
        //Get user data from here
    }
}