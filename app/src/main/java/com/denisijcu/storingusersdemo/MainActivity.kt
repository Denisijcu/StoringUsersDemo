package com.denisijcu.storingusersdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denisijcu.storingusersdemo.model.AppDatabase
import com.denisijcu.storingusersdemo.ui.theme.StoringUsersDemoTheme
import com.denisijcu.storingusersdemo.viewModel.UserViewModel
import com.denisijcu.storingusersdemo.model.DataCache
import com.denisijcu.storingusersdemo.model.UserPreferences
import com.denisijcu.storingusersdemo.model.UserDao
import com.denisijcu.storingusersdemo.repositoriy.UserRepository
import com.denisijcu.storingusersdemo.screens.UserScreen

class MainActivity : ComponentActivity() {


    private val viewModel: UserViewModel by viewModels {
        UserViewModelFactory(
            UserRepository(
                userDao = AppDatabase.getDatabase(applicationContext).userDao(),
                dataCache = DataCache(),
                userPreferences = UserPreferences(applicationContext)
            )
        )
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoringUsersDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   UserScreen(viewModel)
                }
            }
        }
    }




    class UserViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }




}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StoringUsersDemoTheme {
        Greeting("Android")
    }
}