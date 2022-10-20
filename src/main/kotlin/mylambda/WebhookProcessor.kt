package mylambda

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import mylambda.inbound.EmailDeliveryUpdate
import io.quarkus.logging.Log
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.apache.commons.codec.digest.HmacAlgorithms.HMAC_SHA_256
import org.apache.commons.codec.digest.HmacUtils
import javax.inject.Named

// TODO: env configuration
val HMAC_UTILS = HmacUtils(HMAC_SHA_256, "key-ffffffffff111111222222222")

@Named("MailgunWebhookProcessor")
class WebhookProcessor(val json: Json): RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
{
    override fun handleRequest(
        input: APIGatewayProxyRequestEvent,
        context: Context): APIGatewayProxyResponseEvent
    {
        return try {
            val callback = json.decodeFromString<EmailDeliveryUpdate>(input.body)
            APIGatewayProxyResponseEvent().withStatusCode(200)
//            val signature = callback.signature
//            val candidate = "${signature.timestamp}${signature.token}"
//
//            val hexDigest = HMAC_UTILS.hmacHex(candidate)
//
//            if (hexDigest != signature.signature) {
//                Log.error("Signature not valid: $hexDigest vs ${signature.signature}")
//            } else {
//                Log.info("Body $callback")
//            }
        } catch (e: Exception) {
            e.printStackTrace()
            APIGatewayProxyResponseEvent().withStatusCode(500).withBody(e.message)
        }
    }
}
