package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.todolist.composables.LIST_ROUTE
import com.example.todolist.composables.Navigation
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.viewmodels.DEFAULT_TITLE
import com.example.todolist.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            val viewModel: MainActivityViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val navController = rememberNavController()

            ToDoListTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(uiState.title) },
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    },
                    floatingActionButton = {
                        if (uiState.title == DEFAULT_TITLE) {
                            ExtendedFloatingActionButton(
                                onClick = { viewModel.openDialog() },
                                icon = { Icon(Icons.Filled.Edit, "Create new to-do list.") },
                                text = { Text(text = "New List") }
                            )
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Navigation(navController)
                        when {
                            uiState.dialogOpen ->
                                Dialog(
                                    onDismissRequest = { viewModel.closeDialog() }
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        shape = RoundedCornerShape(16.dp),
                                    ) {
                                        Text(modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            text = "Create a new list",
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.titleLarge,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        OutlinedTextField(
                                            modifier = Modifier.padding(16.dp),
                                            value = uiState.newListInput,
                                            onValueChange = { viewModel.updateNewListInput(it) },
                                            label = { Text("List Name") }
                                        )
                                        LazyRow(
                                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            item {
                                                TextButton(onClick = {
                                                    viewModel.closeDialog()
                                                }) {
                                                    Text("Cancel")
                                                }
                                            }
                                            item {
                                                TextButton(onClick = {
                                                    viewModel.newList(
                                                        updateTitle = { newTile -> viewModel.updateTitle(newTile) },
                                                        navigate = { listId: Long -> navController.navigate("$LIST_ROUTE/$listId") }
                                                    )
                                                }) {
                                                    Text("Add")
                                                }
                                            }
                                        }
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
}



