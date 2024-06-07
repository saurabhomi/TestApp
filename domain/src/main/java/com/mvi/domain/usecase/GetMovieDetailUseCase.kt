package com.mvi.domain.usecase

import com.mvi.base.BaseUseCase
import com.mvi.base.Resource
import com.mvi.base.qualifiers.IoDispatcher
import com.mvi.domain.entity.detail.DetailEntityModel
import com.mvi.domain.repository.detail.DetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val detailRepository: DetailRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseUseCase<DetailEntityModel, String> {
    suspend operator fun invoke(params: String): Resource<DetailEntityModel> {
        return withContext(dispatcher) {
            detailRepository.fetchMovieDetail(params)
        }
    }
}
