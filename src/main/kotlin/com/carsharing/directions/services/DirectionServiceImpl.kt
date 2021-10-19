package com.carsharing.directions.services

import com.carsharing.directions.model.UserJourney
import com.carsharing.directions.repository.DirectionRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DirectionServiceImpl(
    private val cassandraRepository: DirectionRepository
): DirectionService {
    override fun getAllDirections(): List<UserJourney> {
        return cassandraRepository.findAll()
    }

    override fun getDirectionById(key: UUID): Optional<UserJourney> {
        return cassandraRepository.findById(key)
    }

    override fun addDirection(direction: UserJourney): UserJourney {
        return cassandraRepository.insert(direction)
    }

    override fun deleteDirectionById(key: UUID) {
        TODO("Not yet implemented")
    }

}