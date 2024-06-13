package com.mvi.domain.usecase

import com.mvi.domain.repository.detail.DetailRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) {
    suspend operator fun invoke(id: String) = detailRepository.fetchMovieDetail(id)
}
