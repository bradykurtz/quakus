package org.churchofjesuschrist.youtubeservices

import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse
import com.amazonaws.services.lambda.runtime.Context
import io.quarkus.amazon.lambda.http.LambdaHttpHandler
import javax.inject.Inject
import javax.inject.Named
import javax.validation.Validator
//
//@Named("test")
//class RequestSqsHandler: LambdaHttpHandler() {
//
//    @Inject
//    lateinit var validator: Validator
//
//    override fun handleRequest(input: APIGatewayV2HTTPEvent, context: Context): APIGatewayV2HTTPResponse {
//        val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
//        val validator: Validator = factory.validator
//        val body = input.body
//        val validationResults: Set<ConstraintViolation<YouTubeMediaRequest>> = validator.validate(input)
//        val t = APIGatewayV2HTTPResponse()
//        t.body = "Hello World"
//        t.statusCode = 200
//        return t
//    }
//}