package org.churchofjesuschrist.youtubeservices

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import io.quarkus.amazon.lambda.http.model.AwsProxyRequest
import io.quarkus.amazon.lambda.http.model.AwsProxyResponse
import javax.inject.Inject
import javax.inject.Named
import javax.validation.ConstraintViolation
import javax.validation.Validator

@Named("test")
class TestLambda : RequestHandler<AwsProxyRequest, AwsProxyResponse> {

    @Inject
    lateinit var validator: Validator


    override fun handleRequest(input: AwsProxyRequest, context: Context): AwsProxyResponse {
//        val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
//        val validator: Validator = factory.validator
        val body = input.body
        val validationResults: Set<ConstraintViolation<YouTubeMediaRequest>> = validator.validate(input)

        return OutputObject().apply {
            setResult("Hello World")
            setRequestId(input.assetId)
        }
    }
}