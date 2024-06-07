package com.mvi.presentation.ui.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import com.mvi.presentation.R
import com.mvi.presentation.model.movie.MovieListUiModel


@Composable
fun MovieListItem(
    item: MovieListUiModel, onItemClick: (MovieListUiModel) -> Unit
) {
    val paddingXXSmall = dimensionResource(id = R.dimen.padding_xx_small)
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val avatarSize = dimensionResource(id = R.dimen.avatar_size_medium)
    val paddingStart =
        dimensionResource(id = R.dimen.movie_list_item_start_indent)
    val paddingEnd = dimensionResource(id = R.dimen.movie_list_item_end_indent)

    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable { onItemClick(item) }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMedium)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = item.posterPath,
                    placeholder = rememberAsyncImagePainter(model = R.drawable.user),
                ),
                contentDescription = stringResource(id = R.string.movie_image),
                modifier = Modifier
                    .size(avatarSize)
                    .padding(end = paddingMedium)
            )


            Column {
                Text(
                    text = item.originalTitle!!,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(paddingXXSmall))

                Text(
                    text = item.voteAverage.toString(),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(start = paddingStart, end = paddingEnd),
            color = MaterialTheme.colorScheme.primary
        )
    }
}