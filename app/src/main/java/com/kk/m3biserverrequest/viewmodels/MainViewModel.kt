package com.kk.m3biserverrequest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kk.m3biserverrequest.models.QuotesList
import com.kk.m3biserverrequest.models.UserList
import com.kk.m3biserverrequest.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    //if View Model load then Repository also get load

    init {
        //getUsers is suspend function that's why launch coroutine
        //IO operation
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getUsers(1)
        }

/*                viewModelScope.launch(Dispatchers.IO) {
            userRepository.getQuotes(1)
        }*/
    }

    val users :LiveData<UserList>
    get() = userRepository.users

    val quotes :LiveData<QuotesList>
        get() = userRepository.quotes


}