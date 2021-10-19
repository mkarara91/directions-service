package com.carsharing.directions

import com.carsharing.directions.model.Marker
import com.carsharing.directions.model.UserJourney
import com.datastax.oss.driver.api.core.CqlSession
import io.kotest.inspectors.forAtMostOne
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.cassandraunit.spring.EmbeddedCassandra
import org.cassandraunit.utils.EmbeddedCassandraServerHelper
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedCassandra
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DirectionsApplicationTests {


    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    private val directionBaseURL = "/directions"

    init {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra()
        val session: CqlSession = EmbeddedCassandraServerHelper.getSession()
        session.execute("CREATE KEYSPACE carsharing_directions WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1}")
        session.execute("USE carsharing_directions")
    }

    companion object {
        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            EmbeddedCassandraServerHelper.cleanEmbeddedCassandra()
        }
    }

    private fun createMarker(lon: Long, lat: Long) = Marker(
        lng = lon,
        lat = lat
    )

    private fun createDirection(directionID: UUID = UUID.randomUUID(), start: Marker, end: Marker, journey: Set<Marker>) =
        UserJourney(
            directionId = directionID,
            start = start,
            end = end,
            journey = journey,
            deleted = false
        )

    @Nested
    inner class GetDirections {
        private val uuid = UUID.randomUUID()
        @Test
        fun shouldCreateJourney() {
            //given
            val userJourneyMarkers = setOf<Marker>(createMarker(50, 50), createMarker(40, 40), createMarker(30, 30))
            val userJourney: UserJourney =
                createDirection(directionID = uuid, start = createMarker(10, 20), end = createMarker(30, 40), userJourneyMarkers)
            val headers = HttpHeaders()
            headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            val requestWithHeaders = HttpEntity(userJourney, headers)
            //when
            val result = testRestTemplate.postForEntity("$directionBaseURL/create", requestWithHeaders, Object::class.java)
            //then
            result shouldNotBe null
            result?.statusCode shouldBe HttpStatus.OK
        }

        @Test
        fun shouldGetPreviouslyCreateJourney(){
            //when
            val result: ResponseEntity<List<UserJourney>>? = testRestTemplate.getForEntity(directionBaseURL, List::class.java as Class<List<UserJourney>>)
            //then
            result shouldNotBe null
            result?.statusCode shouldBe HttpStatus.OK
            result?.body?.forAtMostOne {
                it.directionId shouldBe uuid
            }
        }
    }


}
