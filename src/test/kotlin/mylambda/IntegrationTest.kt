package mylambda

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import io.kotest.assertions.json.shouldContainJsonKey
import io.kotest.assertions.json.shouldContainJsonKeyValue
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
class IntegrationTest {

    @Test
    fun testSimpleLambdaSuccess() {
        val deliveredEmailUpdateStr = parseClassPathSampleResource("/mailgun/delivered.json")
        val requestBody = APIGatewayProxyRequestEvent().withBody(deliveredEmailUpdateStr)

        val response = given()
            .contentType("application/json")
            .accept("application/json")
            .body(requestBody)
            .`when`()
            .post()
            .then()
            .extract()
            .body()
            .asString()

        response.shouldContainJsonKeyValue("$.statusCode", 200)
    }

    private fun parseClassPathSampleResource(resource: String): String = this::class.java.getResource(resource)!!.readText()
}