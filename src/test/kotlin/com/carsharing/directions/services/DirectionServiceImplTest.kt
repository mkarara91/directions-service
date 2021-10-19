package com.carsharing.directions.services

import com.carsharing.directions.repository.DirectionRepository
import io.mockk.clearAllMocks
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class DirectionServiceImplTest{
    private val directionRepository: DirectionRepository = mockk()

    private lateinit var directionServiceImpl: DirectionService

    @BeforeEach
    fun init(){
        clearAllMocks()
        directionServiceImpl = DirectionServiceImpl(directionRepository)
    }


}