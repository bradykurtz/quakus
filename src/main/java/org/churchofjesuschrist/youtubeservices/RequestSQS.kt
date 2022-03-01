package org.churchofjesuschrist.youtubeservices

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.quarkus.amazon.lambda.http.model.AwsProxyRequest
import io.quarkus.amazon.lambda.http.model.AwsProxyResponse
import java.util.Base64
import javax.validation.ConstraintViolation
import javax.validation.Validator
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Context

@Path("requestToSqs")
class RequestSQS(private val validator: Validator, private val objectMapper: ObjectMapper) {

    @POST
    fun request(@Context req: AwsProxyRequest): AwsProxyResponse {
        val body = if (req.isBase64Encoded) {
            String(Base64.getDecoder().decode(req.body))
        } else {
            req.body
        }

        val input = objectMapper.readValue<YouTubeMediaRequest>(body)
        val validationResults: Set<ConstraintViolation<YouTubeMediaRequest>> = validator.validate(input)
        if (validationResults.isNotEmpty()) {
            var validationMessage = ""

            for (result in validationResults) {
                validationMessage += result.propertyPath.toString() + " " + result.message + ". "
            }

            return AwsProxyResponse(400).apply {
                setBody(validationMessage)
            }
        }
        return AwsProxyResponse(200).apply {
            setBody(objectMapper.writeValueAsString(input))
        }
    }

}