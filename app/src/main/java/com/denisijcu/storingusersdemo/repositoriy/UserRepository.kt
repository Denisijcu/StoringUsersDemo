package com.denisijcu.storingusersdemo.repositoriy

// UserRepository.kt
import com.denisijcu.storingusersdemo.model.DataCache
import com.denisijcu.storingusersdemo.model.User
import com.denisijcu.storingusersdemo.model.UserDao
import com.denisijcu.storingusersdemo.model.UserPreferences

class UserRepository(
    private val userDao: UserDao,
    private val dataCache: DataCache<List<User>>,
    private val userPreferences: UserPreferences
) {
    // Use 'by lazy' for lazy initialization of cachedData
    private val cachedData: List<User> by lazy {
        dataCache.getData() ?: emptyList()
    }

    suspend fun getAllUsers(): List<User> {
        // If cachedData is not empty, return it
        if (cachedData.isNotEmpty()) {
            return cachedData
        }

        // Fetch users from userDao
        val users = userDao.getAllUsers()

        // Update the cache with the fetched data
        dataCache.setData(users)

        return users
    }

    suspend fun insertUser(user: User) {
        // Insert user into userDao
        userDao.insertUser(user)

        // Update the cache with the updated users
        dataCache.setData(userDao.getAllUsers())
    }

    fun saveUsername(username: String) {
        userPreferences.saveUsername(username)
    }

    fun getUsername(): String {
        return userPreferences.getUsername()
    }
}

