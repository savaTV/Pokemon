// app/src/main/java/com/example/pokemon/FilterScreen.kt
package com.example.pokemon

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun FilterScreen(
    onSearch: (String, String) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf(TextFieldValue("")) }
    var type by remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Поиск по имени") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Фильтр по типу") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSearch(query.text, type.text)
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Применить")
        }
    }
}