package com.carsharing.directions.model

import org.springframework.data.cassandra.core.mapping.UserDefinedType

@UserDefinedType
data class Marker(
    val lon: Long, val lat: Long)
