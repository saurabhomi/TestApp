package com.mvi.data

import com.mvi.data.model.detail.DetailDataModel
import com.mvi.data.model.movie.MovieDataModel
import com.mvi.data.model.movie.MovieListDataModel
import com.mvi.network.model.detail.DetailNetworkModel
import com.mvi.network.model.movie.MovieListNetworkModel
import com.mvi.network.model.movie.MovieNetworkModel


/**
 * Dummy data generator for tests
 */
class TestDataGenerator {

    companion object {


        fun generateMovieListData(): MovieDataModel {
            val item1 = MovieListDataModel(
                1, "title 1", "test body 1", "path1", 1f
            )
            val item2 = MovieListDataModel(
                1, "title 2", "test body 2", "path2", 2f
            )
            val item3 = MovieListDataModel(
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

        fun generateMovieDetailNetworkData(): DetailNetworkModel {
            return DetailNetworkModel(
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

        fun generateMovieData(): MovieNetworkModel {
            return MovieNetworkModel(list())
        }

        private fun list(): List<MovieListNetworkModel> {
            val item1 = MovieListNetworkModel(
                1, "title 1", "test body 1", "path1", 1f
            )
            val item2 = MovieListNetworkModel(
                1, "title 2", "test body 2", "path2", 2f
            )
            val item3 = MovieListNetworkModel(
                1, "title 3", "test body 3", "path2", 3f
            )
            return listOf(item1, item2, item3)
        }

    }
}