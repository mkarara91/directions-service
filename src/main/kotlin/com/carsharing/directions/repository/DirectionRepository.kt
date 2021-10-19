package com.carsharing.directions.repository

import com.carsharing.directions.model.UserJourney
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DirectionRepository: CassandraRepository<UserJourney, UUID> {
}