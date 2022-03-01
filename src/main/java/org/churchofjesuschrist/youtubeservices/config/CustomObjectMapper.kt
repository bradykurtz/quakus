package org.churchofjesuschrist.youtubeservices.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.quarkus.jackson.ObjectMapperCustomizer
import javax.enterprise.inject.Instance
import javax.inject.Singleton

class CustomObjectMapper {

    @Singleton
    fun objectMapper(customizers: Instance<ObjectMapperCustomizer>): ObjectMapper? {
        val mapper = ObjectMapper()
        mapper.registerModule(KotlinModule.Builder().build())

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

        // Apply all ObjectMapperCustomizer beans (incl. Quarkus)
        for (customizer in customizers) {
            customizer.customize(mapper)
        }
        return mapper
    }
}