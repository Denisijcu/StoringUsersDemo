package com.denisijcu.storingusersdemo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denisijcu.storingusersdemo.model.User
import com.denisijcu.storingusersdemo.repositoriy.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    suspend  fun fetchUsers() {
        viewModelScope.launch {
            _users.value = userRepository.getAllUsers()
        }
    }

    suspend fun addUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
            fetchUsers()
        }
    }

    suspend fun saveUsername(username: String) {
        viewModelScope.launch {
            userRepository.saveUsername(username)
        }
    }

    suspend fun getUsername(): String {
        return userRepository.getUsername()
    }
}

