package com.carsharing.directions.recources

import com.carsharing.directions.model.Marker
import com.carsharing.directions.model.UserJourney
import com.carsharing.directions.services.DirectionService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/directions")
@RestController
class DirectionResource(
    private val directionService: DirectionService
) {

    @GetMapping("")
    fun getAllDirections(): List<UserJourney> {
        return directionService.getAllDirections()
    }

    @GetMapping("/id/{id}")
    fun getDirectionById(
        @PathVariable(name = "id") id: UUID
    ): UserJourney? {
        val userJourney: Optional<UserJourney> = directionService.getDirectionById(id)
        if (userJourney.isPresent) {
            return userJourney.get()
        }
        return null
    }

    @GetMapping(
        "/match",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun matchUserRoute(
        @RequestParam startLat: Double,
        @RequestParam startLon: Double,
        @RequestParam endLat: Double,
        @RequestParam endLon: Double
    ): List<UserJourney> {
        val start = Marker(startLon, startLat)
        val end = Marker(endLon, endLat)
        return directionService.getMatchingRoute(start, end)
    }


    @PostMapping(
        "/create",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun addUserDirection(@RequestBody direction: UserJourney): UserJourney {
        return directionService.addDirection(direction)
    }

}