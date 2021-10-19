package com.carsharing.directions.model

import org.springframework.data.cassandra.core.mapping.Column
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.util.*

@Table("userjourney")
data class UserJourney(
    @PrimaryKey(value = "directionid")
    val directionId: UUID = UUID.randomUUID(),
    @Column(value = "start")
    val start: Marker,
    @Column(value = "end")
    val end: Marker,
    @Column(value = "journey")
    val journey: Set<Marker>,
    @Column(value = "deleted")
    val deleted: Boolean
)
