package com.mvi.presentation.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import com.mvi.domain.model.detail.DetailDomainModel
import com.mvi.presentation.R

@Composable
fun MovieDetail(paddingValues: PaddingValues, detailUiModel: DetailDomainModel) {
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val paddingXSmall = dimensionResource(id = R.dimen.padding_x_small)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = paddingMedium,
                end = paddingMedium
            )
    ) {
        Spacer(modifier = Modifier.size(paddingXSmall))

        ScoreSession(detailUiModel)

        Spacer(modifier = Modifier.size(paddingXSmall))

        OverView(detailUiModel)

    }


}

@Composable
fun ScoreSession(detailUiModel: DetailDomainModel) {
    val paddingMedium = dimensionResource(id = R.dimen.padding_medium)
    val avatarSize = dimensionResource(id = R.dimen.avatar_size_large)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = detailUiModel.posterPath,
                placeholder = rememberAsyncImagePainter(model = R.drawable.user),
            ),
            contentDescription = stringResource(id = R.string.movie_image),
            modifier = Modifier
                .size(avatarSize)
                .padding(end = paddingMedium)
        )

        Plot(detailUiModel)
    }
}


@Composable
fun Plot(detailUiModel: DetailDomainModel) {
    val paddingXXSmall = dimensionResource(id = R.dimen.padding_xx_small)
    Column {
        Text(
            text = detailUiModel.originalTitle,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(paddingXXSmall))
        Text(
            text = detailUiModel.voteAverage.toString(),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun OverView(detailUiModel: DetailDomainModel) {
    Text(
        text = detailUiModel.overview,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.fillMaxWidth()
    )

}