package com.mvi.presentation

import com.mvi.domain.model.detail.DetailDomainModel
import com.mvi.domain.model.movie.MovieDomainModel


/**
 * Dummy data generator for tests
 */
class TestDataGenerator {
    companion object {
        fun generateMovieListData(): MovieDomainModel {
            val item1 = MovieDomainModel.MovieListDomainModel(
                1, "title 1", "test body 1", "path1", 1f
            )
            val item2 = MovieDomainModel.MovieListDomainModel(
                1, "title 2", "test body 2", "path2", 2f
            )
            val item3 = MovieDomainModel.MovieListDomainModel(
                1, "title 3", "test body 3", "path2", 3f
            )
            return MovieDomainModel(listOf(item1, item2, item3))
        }

        fun generateMovieDetailData(): DetailDomainModel {
            return DetailDomainModel(
                true,
                "path",
                100,
                "en",
                "title",
                "overview",
                45.2,
                "path",
                "20.03.1990",
                123,
                "released",
                "tagline",
                "title",
                5.6,
                456
            )
        }
    }

}