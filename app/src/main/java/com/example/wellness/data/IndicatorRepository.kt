package com.example.wellness.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface IndicatorRepository {
    fun getIndicatorStream(indicatorName: String): Flow<Indicator?>
    fun getStream(): List<Flow<Indicator>>
    fun insertIndicator(indicator: Indicator)
    fun deleteIndicator(indicator: Indicator)
    fun updateIndicator(indicator: Indicator)
}

class IndicatorMockRepository @Inject constructor(): IndicatorRepository {
    override fun getIndicatorStream(indicatorName: String): Flow<Indicator?> =
        flow {
            emit(IndicatorStorage.getAll()[indicatorName])
        }

    override fun getStream(): List<Flow<Indicator>> =
        IndicatorStorage.getAll().map { indicator ->
            flow { emit(indicator.value) }
        }

    override fun insertIndicator(indicator: Indicator) { TODO() }
    override fun deleteIndicator(indicator: Indicator) { TODO() }
    override fun updateIndicator(indicator: Indicator) { TODO() }

}