package com.mvi.presentation.ui.detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.mvi.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenTopAppBar(onBackButtonClicked: () -> Unit) {
    CenterAlignedTopAppBar(title = {
        Text(
            text = stringResource(R.string.home_screen_top_bar_title),
            textAlign = TextAlign.Center,
        )
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
    ), navigationIcon = {
        IconButton(onClick = { onBackButtonClicked() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        }
    })
}