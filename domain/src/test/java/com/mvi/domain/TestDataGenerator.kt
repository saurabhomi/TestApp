package com.mvi.domain

import com.mvi.domain.entity.detail.DetailEntityModel
import com.mvi.domain.entity.movie.MovieEntityModel
import com.mvi.domain.entity.movie.MovieListEntityModel


/**
 * Dummy data generator for tests
 */
class TestDataGenerator {

    companion object {


        fun generateMovieEntityModel(): MovieEntityModel {
            val item1 = MovieListEntityModel(
                1, "title 1", "test body 1", "path1", 1f
            )
            val item2 = MovieListEntityModel(
                1, "title 2", "test body 2", "path2", 2f
            )
            val item3 = MovieListEntityModel(
                1, "title 3", "test body 3", "path2", 3f
            )
            return MovieEntityModel(listOf(item1, item2, item3))
        }

        fun generateMovieDetailEntityModel(): DetailEntityModel {
            return DetailEntityModel(
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