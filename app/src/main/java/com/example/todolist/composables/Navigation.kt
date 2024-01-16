package com.example.todolist.composables

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val LIST_ID_KEY = "listId"

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(startDestination = "main", navController = navController) {
        composable("main") { Main() }
        composable(
            "list/{listId}",
            arguments = listOf(navArgument(LIST_ID_KEY) { type = NavType.IntType })
        ) { navBackStackEntry -> navBackStackEntry.arguments?.getInt(LIST_ID_KEY)
            ?.let { ToDoList(parentListId = it) } }
    }
}