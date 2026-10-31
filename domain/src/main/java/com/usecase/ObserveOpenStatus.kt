package com.usecase

import com.extension.statusAt
import com.model.OpenStatus
import com.repository.RestaurantRepository
import jakarta.inject.Inject
import jdk.jfr.internal.OldObjectSample.emit
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.ZoneId

class ObserveOpenStatus @Inject constructor(
    private val repository: RestaurantRepository
) {
    operator fun invoke(): Flow<OpenStatus> = flow{
        while (true){
            emit(repository.info.statusAt(LocalDateTime.now(ZoneId.of("Europe/Madrid"))))
            delay(60000)
        }
    }
}