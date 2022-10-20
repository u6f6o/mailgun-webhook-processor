package mylambda.inbound

import io.quarkus.runtime.annotations.RegisterForReflection
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@RegisterForReflection
@Serializable
data class EmailDeliveryUpdate(
    val signature: Signature,
    @SerialName("event-data")
    val eventData: EventData
)

@RegisterForReflection
@Serializable
data class Signature(
    val token: String,
    val timestamp: Int,
    val signature: String
)

@RegisterForReflection
@Serializable
data class EventData (
    val id: String,
    val event: Event,
    val severity: Severity? = null,
    val template: Template? = null,
    @SerialName("user-variables")
    val userVariables: Map<String, String> = emptyMap(),
    @SerialName("delivery-status")
    val deliveryStatus: DeliveryStatus
)

@RegisterForReflection
@Serializable
data class Template(
    val name: String
)

@RegisterForReflection
@Serializable
data class DeliveryStatus(
    val message: String,
    @SerialName("attempt-no")
    val attempt: Int
)

@RegisterForReflection
@Serializable
enum class Event {
    @SerialName("delivered")
    DELIVERED,
    @SerialName("failed")
    FAILED
}

@RegisterForReflection
@Serializable
enum class Severity {
    @SerialName("permanent")
    PERMANENT,
    @SerialName("temporary")
    TEMPORARY
}
