package com.carsharing.directions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DirectionsApplication

fun main(args: Array<String>) {
	runApplication<DirectionsApplication>(*args)
}
