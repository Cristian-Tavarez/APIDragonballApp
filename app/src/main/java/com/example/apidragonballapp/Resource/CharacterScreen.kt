package com.example.apidragonballapp.Resource

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun CharacterScreen(
    viewModel: CharacterViewModel = hiltViewModel()
) {
    val resultState = viewModel.uiState
    val query = viewModel.searchQuery

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.onSearchQueryChanged(it) },
                label = { Text("Buscar personaje por nombre...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                when (val state = resultState) {
                    is NetworkResult.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    is NetworkResult.Success -> {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(state.data ?: emptyList()) { character ->
                                Card(modifier = Modifier.fillMaxWidth()) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            AsyncImage(
                                                model = character.image,
                                                contentDescription = character.name,
                                                modifier = Modifier
                                                    .size(100.dp)
                                                    .padding(end = 12.dp),
                                                contentScale = ContentScale.Fit
                                            )

                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(text = character.name, style = MaterialTheme.typography.titleLarge)
                                                Text(text = "Raza: ${character.race}", style = MaterialTheme.typography.bodyMedium)
                                                Text(text = "Ki: ${character.ki}", style = MaterialTheme.typography.bodyMedium)
                                            }
                                        }

                                        if (character.description.isNotBlank()) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = character.description,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is NetworkResult.Error -> {
                        Text(
                            text = state.message ?: "Error desconocido",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}