package com.caffeine.bfst.services.repository

import androidx.lifecycle.MutableLiveData
import com.caffeine.bfst.services.model.UserDetails
import com.caffeine.bfst.utils.Constants
import com.caffeine.bfst.utils.DataState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

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
        userMutableLiveData.postValue(DataState.Loading())
        val list = ArrayList<UserDetails>()
        Constants.userReference
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for (ds in snapshot.children){
                        ds.getValue(UserDetails::class.java)?.let { list.add(it) }
                    }
                    userMutableLiveData.postValue(DataState.Success(list))
                }

                override fun onCancelled(error: DatabaseError) {
                    userMutableLiveData.postValue(DataState.Failed(error.message))
                }

            })
    }
}