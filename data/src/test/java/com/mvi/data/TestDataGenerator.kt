package com.mvi.data

import com.mvi.data.model.detail.DetailDataModel
import com.mvi.data.model.movie.MovieDataModel

/**
 * Dummy data generator for tests
 */
class TestDataGenerator {

    companion object {
        fun generateMovieListData(): MovieDataModel {
            val item1 = MovieDataModel.MovieListDataModel(
                1, "title 1", "test body 1", "path1", 1f
            )
            val item2 = MovieDataModel.MovieListDataModel(
                1, "title 2", "test body 2", "path2", 2f
            )
            val item3 = MovieDataModel.MovieListDataModel(
                1, "title 3", "test body 3", "path2", 3f
            )
            return MovieDataModel(listOf(item1, item2, item3))
        }

        fun generateMovieDetailData(): DetailDataModel {
            return DetailDataModel(
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

        fun generateMovieDetailNetworkData(): DetailDataModel {
            return DetailDataModel(
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