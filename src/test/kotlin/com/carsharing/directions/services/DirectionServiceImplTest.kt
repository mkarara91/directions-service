package com.carsharing.directions.services

import com.carsharing.directions.model.Marker
import com.carsharing.directions.model.UserJourney
import com.carsharing.directions.repository.DirectionRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.InputStream
import java.util.*

internal class DirectionServiceImplTest {
    private val directionRepository: DirectionRepository = mockk()

    private lateinit var directionServiceImpl: DirectionService

    @BeforeEach
    fun init() {
        clearAllMocks()
        directionServiceImpl = DirectionServiceImpl(directionRepository)
    }

    private fun createMarker(lon: Double, lat: Double) = Marker(
        lng = lon,
        lat = lat
    )

    private fun createDirection(directionID: UUID = UUID.randomUUID(), start: Marker, end: Marker, journey: List<Marker>) =
        UserJourney(
            directionId = directionID,
            start = start,
            end = end,
            journey = journey,
            deleted = false
        )

    @Test
    fun `should get all matching routes to user journey`() {
        //given
        val userJourneyMarkers = listOf(createMarker(50.0, 50.0), createMarker(40.0, 40.0), createMarker(30.0, 30.0))
        val userJourney: UserJourney =
            createDirection(start = createMarker(50.0, 50.0), end = createMarker(30.0, 30.0), journey = userJourneyMarkers)

        val userJourneyMarkers2 = listOf(createMarker(30.0, 30.0), createMarker(40.0, 40.0), createMarker(50.0, 50.0))
        val userJourney2: UserJourney =
            createDirection(start = createMarker(30.0, 30.0), end = createMarker(50.0, 50.0), journey = userJourneyMarkers2)


        val journeyList: List<UserJourney> = listOf(userJourney, userJourney2)
        val startMarkerToTest = createMarker(30.0, 30.0)
        val endMarkerToTest = createMarker(50.0, 50.0)

        every {
            directionRepository.findAll()
        } returns journeyList
        //when
        val result = directionServiceImpl.getMatchingRoute(startMarkerToTest, endMarkerToTest)

        //then

        result.size shouldBe 1
        result[0].start.lat shouldBe 30.0
        result[0].start.lng shouldBe 30.0

    }

    @Test
    fun `should return empty list if no routes match even in wrong direction`() {
        //given
        val userJourneyMarkers = listOf(createMarker(50.0, 50.0), createMarker(40.0, 40.0), createMarker(30.0, 30.0))
        val userJourney: UserJourney =
            createDirection(start = createMarker(50.0, 50.0), end = createMarker(30.0, 30.0), journey = userJourneyMarkers)


        val journeyList: List<UserJourney> = listOf(userJourney)
        val startMarkerToTest = createMarker(30.0, 30.0)
        val endMarkerToTest = createMarker(50.0, 50.0)

        every {
            directionRepository.findAll()
        } returns journeyList
        //when
        val result = directionServiceImpl.getMatchingRoute(startMarkerToTest, endMarkerToTest)

        //then

        result.size shouldBe 0
    }

    @Test
    fun `should find matching routes based on actual data`() {
        //given
        val mapper = jacksonObjectMapper()

        val fileContent: InputStream = this.javaClass.getResourceAsStream("/services/directionServiceData.json")
        val routes: List<UserJourney> = mapper.readValue(fileContent)
        val startMarkerToTest = createMarker(13.4614338, 52.493945)
        val endMarkerToTest = createMarker(13.460030000000001, 52.498870000000004)
        every {
            directionRepository.findAll()
        } returns routes
        //when
        val result = directionServiceImpl.getMatchingRoute(startMarkerToTest, endMarkerToTest)
        //then

        result.size shouldBe 2
    }


}