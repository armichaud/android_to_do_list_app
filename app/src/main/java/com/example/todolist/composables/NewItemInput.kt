package com.example.todolist.composables

import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.todolist.viewmodels.MainActivityViewModel
import com.example.todolist.viewmodels.UiState

@Composable
fun NewItemInput(
    modifier: Modifier = Modifier,
    uiState: UiState,
    updateCurrentInput: (String) -> Unit,
    handleDeselect: () -> Unit
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        shape = RectangleShape,
        value = uiState.currentInput,
        maxLines = 1,
        onValueChange = { target: String -> updateCurrentInput(target) },
        placeholder = { Text("New item") },
        leadingIcon = {
            IconButton(
                modifier = modifier.absolutePadding(left = 16.dp),
                onClick = { handleDeselect() }
            ) { Icon(Icons.Outlined.Add, contentDescription = "New list item icon") }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions( onDone = { handleDeselect() })
    )
}
