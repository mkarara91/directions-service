package com.carsharing.directions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories

@SpringBootApplication
@EnableCassandraRepositories
@EnableConfigurationProperties
class DirectionsApplication

fun main(args: Array<String>) {
	runApplication<DirectionsApplication>(*args)
}
