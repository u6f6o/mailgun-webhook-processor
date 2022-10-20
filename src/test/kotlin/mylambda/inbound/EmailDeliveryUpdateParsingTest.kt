package mylambda.inbound

import io.kotest.matchers.shouldBe
import io.quarkus.test.junit.QuarkusTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import mylambda.inbound.Event.DELIVERED
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
@OptIn(ExperimentalSerializationApi::class)
class EmailDeliveryUpdateParsingTest {

    @Inject
    private lateinit var json: Json

    @Test
    @DisplayName("Should parse successfully delivered message.")
    fun testSuccessfulDeliveryMessage() {
        val result = parseClassPathSampleResource("/mailgun/delivered.json")
        val eventData = result.eventData

        eventData.event shouldBe DELIVERED
        eventData.severity shouldBe null
        eventData.template!!.name shouldBe "password-reset.html"
    }

    private fun parseClassPathSampleResource(resource: String): EmailDeliveryUpdate = json.decodeFromStream(
        this::class.java.getResourceAsStream(resource)!!
    )
}