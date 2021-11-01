package com.carsharing.directions.services

import com.carsharing.directions.model.Marker
import com.carsharing.directions.model.UserJourney
import java.util.*

interface DirectionService {
    fun getAllDirections(): List<UserJourney>
    fun getDirectionById(key: UUID): Optional<UserJourney>
    fun addDirection(direction: UserJourney): UserJourney
    fun deleteDirectionById(key: UUID)
    fun getMatchingRoute(startMarker: Marker, endMarker: Marker): List<UserJourney>
}