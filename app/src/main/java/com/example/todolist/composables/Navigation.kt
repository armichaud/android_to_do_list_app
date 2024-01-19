package com.example.todolist.composables

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolist.viewmodels.DEFAULT_TITLE

const val MAIN_ROUTE = "main"
const val LIST_ROUTE = "list"
const val LIST_ID_KEY = "listId"

@Composable
fun Navigation(navController: NavHostController = rememberNavController(), updateTitle: (String) -> Unit) {
    NavHost(startDestination = "main", navController = navController) {
        composable(MAIN_ROUTE) {
            Home(
                updateTitle = updateTitle,
                goToList = { listId: Int -> navController.navigate("$LIST_ROUTE/$listId") }
            )
        }
        composable(
            "$LIST_ROUTE/{$LIST_ID_KEY}",
            arguments = listOf(navArgument(LIST_ID_KEY) { type = NavType.IntType })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getInt(LIST_ID_KEY)?.let {
                ToDoList(parentListId = it) {
                    updateTitle(DEFAULT_TITLE)
                    navController.navigate(MAIN_ROUTE)
                }
            }
        }
    }
}