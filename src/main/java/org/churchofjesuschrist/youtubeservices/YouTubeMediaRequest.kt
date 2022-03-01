package org.churchofjesuschrist.youtubeservices

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import javax.enterprise.context.ApplicationScoped
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive
import kotlin.reflect.KClass


@JsonDeserialize
class YouTubeMediaRequest (
    @get:NotBlank
    @JsonProperty("s3VideoBucket")
    val s3VideoBucket: String,

    @get:NotBlank
    @JsonProperty("s3VideoFilename")
    val s3VideoFilename: String,

    @get:NotBlank
    @JsonProperty("s3ImageBucket")
    val s3ImageBucket: String,

    @get:NotBlank
    @JsonProperty("s3ImageFilename")
    val s3ImageFilename: String,

    @get:Positive
    @JsonProperty("youTubeChannelId")
    val youTubeChannelId: Int,

    @get:ValidYouTubeCategory
    @JsonProperty("youTubeCategoryId")
    val youTubeCategoryId: Int,

    @JsonProperty("privateVideo")
    val privateVideo: Boolean,

    @get:NotBlank
    @JsonProperty("assetId")
    val assetId: String,

    @get:ValidYouTubeMetaData
    @JsonProperty("metaData")
    val metaData: YouTubeMetaData
)

@JsonDeserialize
class YouTubeMetaData(
    @get:NotBlank
    @JsonProperty("title")
    val title: String,

    @get:NotBlank
    @JsonProperty("description")
    val description: String,

    @JsonProperty("tags")
    val tags: List<String>
)

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY_GETTER)
@Constraint(validatedBy = [YouTubeCategoryValidator::class])
annotation class ValidYouTubeCategory(
    val message: String = "Must be a valid YouTube Category.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

@ApplicationScoped
class YouTubeCategoryValidator : ConstraintValidator<ValidYouTubeCategory, Int> {
//    @Overridepublic
//    override fun initialize(constraintAnnotation: RequiredProperty?) {
//    }

    override fun isValid(property: Int, context: ConstraintValidatorContext): Boolean {
        return (property == 1 || property == 2 || property == 10 || property == 15 || property == 17 ||
                property == 19 || property == 20 || (property in 22..29))
    }
}

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY_GETTER)
@Constraint(validatedBy = [YouTubeMetaDataValidator::class])
annotation class ValidYouTubeMetaData(
    val message: String = "must have a title and description",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class YouTubeMetaDataValidator : ConstraintValidator<ValidYouTubeMetaData, YouTubeMetaData> {
    override fun isValid(property: YouTubeMetaData, context: ConstraintValidatorContext): Boolean {
        return (property.title.isNotBlank() && property.description.isNotBlank())
    }
}