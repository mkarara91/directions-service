package com.carsharing.directions.model

import org.springframework.data.cassandra.core.mapping.UserDefinedType

@UserDefinedType
data class Marker(
    val lng: Double, val lat: Double)
