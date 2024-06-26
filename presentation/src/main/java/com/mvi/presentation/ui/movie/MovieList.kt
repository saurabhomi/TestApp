package com.mvi.presentation.ui.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mvi.domain.model.movie.MovieDomainModel

@Composable
fun MovieList(
    paddingValues: PaddingValues,
    list: List<MovieDomainModel.MovieListDomainModel>,
    onListItemCLick: (MovieDomainModel.MovieListDomainModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyColumn {
            item {
                MovieListHeader()
            }
            items(count = list.size) { i ->
                MovieListItem(item = list[i], onItemClick = onListItemCLick)
            }
        }
    }
}
