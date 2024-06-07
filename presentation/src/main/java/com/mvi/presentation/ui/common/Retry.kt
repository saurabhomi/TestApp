package com.mvi.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.mvi.presentation.R

@Composable
fun Retry(onRetryButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onRetryButtonClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(
                painter = painterResource(id = R.drawable.retry_icon),
                contentDescription = stringResource(id = R.string.retry),
                modifier = Modifier.size(dimensionResource(R.dimen.avatar_size_small))
            )
            Spacer(modifier = Modifier.size(dimensionResource(R.dimen.padding_xx_small)))
            Text(
                text = stringResource(R.string.retry),
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
            )
        }

    }
}