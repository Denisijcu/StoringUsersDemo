package com.denisijcu.storingusersdemo.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.denisijcu.storingusersdemo.model.User
import com.denisijcu.storingusersdemo.viewModel.UserViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UserScreen(viewModel: UserViewModel) {
    var userName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.fetchUsers()
    }

    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Button(onClick = {

            coroutineScope.launch {
                viewModel.fetchUsers()
            }


        }) {
            Text("Fetch Users")
        }

        AddUserSection(
            userName = userName,
            userEmail = userEmail,
            onNameChange = { userName = it },
            onEmailChange = { userEmail = it },
            onAddUser = {
                if (userName.isNotBlank() || userEmail.isNotBlank()) {

                    coroutineScope.launch {
                        viewModel.addUser(User(id = 0, username = userName, email = userEmail))
                        userName = ""
                        userEmail = ""
                        viewModel.fetchUsers()
                    }

                }
            }
        )

        UserList(users = viewModel.users.value)

    }
}

@Composable
fun AddUserSection(
    userName: String,
    userEmail: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onAddUser: () -> Unit
) {
    Column(
        modifier = Modifier.padding(4.dp),
    ) {
        TextField(
            value = userName,
            onValueChange = { onNameChange(it) },
            label = { Text("UserName") },
            modifier = Modifier.padding(4.dp)
        )

        TextField(
            value = userEmail,
            onValueChange = { onEmailChange(it) },
            label = { Text("Email") },
            modifier = Modifier.padding(4.dp)
        )

        Button(onClick = { onAddUser() }) {
            Text("Add User")
        }
    }
}

// UserList.kt
@Composable
fun UserList(users: List<User>) {
    LazyColumn {
        items(users) { user ->
            UserCard(user = user)
        }
    }
}

// UserCard.kt
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = {
            // Handle item click if needed
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "ID: ${user.id}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Username: ${user.username}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Email: ${user.email}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

