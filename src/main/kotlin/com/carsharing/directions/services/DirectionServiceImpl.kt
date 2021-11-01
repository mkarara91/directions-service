package com.carsharing.directions.services

import com.carsharing.directions.model.Marker
import com.carsharing.directions.model.UserJourney
import com.carsharing.directions.repository.DirectionRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

@Service
class DirectionServiceImpl(
    private val cassandraRepository: DirectionRepository
) : DirectionService {
    val maxDistance = 0.005
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

    override fun getMatchingRoute(startMarker: Marker, endMarker: Marker): List<UserJourney> {
        val routes = cassandraRepository.findAll()
        return routes.filter { isRouteMatching(startMarker, endMarker, it) }
    }

    private fun isRouteMatching(startMarker: Marker, endMarker: Marker, userJourney: UserJourney): Boolean {
        val closestPointStartIndex: Int? = getClosestPoint(startMarker, userJourney)

        val closestPointEndIndex: Int? = getClosestPoint(endMarker, userJourney)
        if(closestPointEndIndex == null || closestPointStartIndex == null){
            return false
        }
        //wrong direction
        if(closestPointStartIndex > closestPointEndIndex){
            return false
        }

        return true
    }

    private fun getClosestPoint(marker: Marker, userJourney: UserJourney): Int? {
        val closestPointIndices: List<Int> = userJourney.journey.mapIndexedNotNull { index, mark ->
            if (isMarkerClose(mark, marker)) index else null
        }
        if (closestPointIndices.isEmpty()) {
            return null;
        }

        return closestPointIndices.reduce { acc, i ->
            if (getDistance(userJourney.journey[i], marker) < getDistance(userJourney.journey[acc], marker))
                i else acc
        }
    }

    private fun getDistance(journeyMarker: Marker, markerToCompare: Marker): Double {
        return sqrt((journeyMarker.lat - markerToCompare.lat).pow(2) + (journeyMarker.lng - markerToCompare.lng).pow(2))
    }

    private fun isMarkerClose(journeyMarker: Marker, markerToCompare: Marker): Boolean {

        val distance = getDistance(journeyMarker, markerToCompare)
        return distance <= maxDistance
    }

}