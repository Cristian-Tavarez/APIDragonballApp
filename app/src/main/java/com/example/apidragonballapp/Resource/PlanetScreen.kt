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
fun PlanetScreen(
    viewModel: PlanetViewModel = hiltViewModel()
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
                label = { Text("Buscar planeta por nombre...") },
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
                            items(state.data ?: emptyList()) { planet ->
                                Card(modifier = Modifier.fillMaxWidth()) {
                                    Column(modifier = Modifier.padding(16.dp)) {

                                        AsyncImage(
                                            model = planet.image,
                                            contentDescription = "Imagen de ${planet.name}",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(180.dp),
                                            contentScale = ContentScale.Crop
                                        )

                                        Spacer(modifier = Modifier.height(12.dp))

                                        Text(text = planet.name, style = MaterialTheme.typography.titleLarge)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = planet.description, style = MaterialTheme.typography.bodyMedium)
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