package com.kk.m3biserverrequest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kk.m3biserverrequest.api.UserService
import com.kk.m3biserverrequest.models.QuotesList
import com.kk.m3biserverrequest.models.UserList

//Repository used in between View model and web api interface to manage data
class UserRepository(private val userService: UserService) //if need database add one more param
{

    //USER Repository
    //mutable live data possible to change values from it
    private val userLiveData = MutableLiveData<UserList>()

    //live data not possible to change values after first initialize
    //Share live data to out side the repository so not possible to change that from out side repository
    val users: LiveData<UserList>
        get() = userLiveData

    suspend fun getUsers(page_no: Int) {
        //call to web api user interface
        val result = userService.getUsers(page_no)
        //null safety used
        if (result?.body() != null) {
            //return to view  model
            userLiveData.postValue(result.body())

        }
    }


    //Quote data for testing purpose
    //mutable live data possible to change values from it
    private val quoteLiveData = MutableLiveData<QuotesList>()

    //live data not possible to change values after first initialize
    //Share live data to out side the repository so not possible to change that from out side repository
    val quotes: LiveData<QuotesList>
        get() = quoteLiveData

    suspend fun getQuotes(page_no: Int) {
        //call to web api user interface
        val result = userService.getQuotes(page_no)
        //null safety used
        if (result?.body() != null) {
            //return to view  model
            quoteLiveData.postValue(result.body())

        }
    }

}