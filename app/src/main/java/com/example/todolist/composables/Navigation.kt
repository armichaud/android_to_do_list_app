package com.example.todolist.composables

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

const val LIST_ID_KEY = "listId"
const val LIST_NAME_KEY = "listName"
const val LIST_ROUTE = "list/"

@Composable
fun Navigation(navController: NavHostController = rememberNavController()) {
    NavHost(startDestination = "main", navController = navController) {
        composable("main") { Home() { listId: Int, listName: String -> navController.navigate("$LIST_ROUTE/{$listName}/{$listId}") } }
        composable(
            "$LIST_ROUTE/{$LIST_NAME_KEY}/{$LIST_ID_KEY}",
            arguments = listOf(navArgument(LIST_ID_KEY) { type = NavType.IntType })
        ) { navBackStackEntry ->
            val args = navBackStackEntry.arguments
            val parentListId = args?.getInt(LIST_ID_KEY)
            val parentListName = args?.getString(LIST_NAME_KEY)
            parentListId?.let {
                if (parentListName != null) {
                    ToDoList(parentListId = it, parentListName = parentListName)
                }
            }
        }
    }
}