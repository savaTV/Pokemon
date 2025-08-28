// app/src/main/java/com/example/pokemon/FilterScreen.kt
package com.example.pokemon

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    onApply: (String, String) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf<PokemonType?>(null) }
    var showTypeDialog by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        TopAppBar(
            title = { Text("Фильтры") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                }
            }
        )

        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Поиск по имени") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔽 Выбор типа
            OutlinedTextField(
                value = selectedType?.typeName?.capitalize() ?: "Выберите тип",
                onValueChange = {},
                label = { Text("Тип покемона") },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {
                        showTypeDialog = true
                    }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Выбрать тип")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onApply(name, selectedType?.typeName ?: "")
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Применить")
            }
        }
    }




    if (showTypeDialog) {
        AlertDialog(
            onDismissRequest = { showTypeDialog = false },
            title = { Text("Выберите тип") },
            text = {
                LazyColumn(modifier = Modifier.height(300.dp)) {
                    items(PokemonType.values()) { type ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedType = type
                                    showTypeDialog = false
                                }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(type.color1)
                                    .align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = type.typeName.capitalize())
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showTypeDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}