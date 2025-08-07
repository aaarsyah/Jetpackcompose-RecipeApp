package com.example.recipeapp.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.R
import com.example.recipeapp.di.Injection
import com.example.recipeapp.model.FavRecipe
import com.example.recipeapp.ui.ViewModelFactory
import com.example.recipeapp.ui.common.UiState
import com.example.recipeapp.ui.components.RecipeItems

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState(initial = UiState.Loading).value
    val query = viewModel.query.value

    when (uiState) {
        is UiState.Loading -> {
            viewModel.getAllRewards()
        }
        is UiState.Success -> {
            HomeContent(
                favRecipe = uiState.data,
                query = query,
                onQueryChange = viewModel::search,
                modifier = modifier,
                navigateToDetail = navigateToDetail,
            )
        }
        is UiState.Error -> {}
    }
}


@Composable
fun HomeContent(
    favRecipe: List<FavRecipe>,
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    Column(modifier = modifier) {
        SearchBar(query = query, onQueryChange = onQueryChange)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(favRecipe) { data ->
                RecipeItems(
                    name = data.recipe.name,
                    photoUrl = data.recipe.photoUrl,
                    modifier = Modifier.clickable {
                        navigateToDetail(data.recipe.id)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text(stringResource(R.string.search)) },
        singleLine = true
    )
}
