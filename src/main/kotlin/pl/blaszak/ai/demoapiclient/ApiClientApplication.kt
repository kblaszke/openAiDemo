package pl.blaszak.ai.demoapiclient

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@ConfigurationPropertiesScan
@SpringBootApplication
class ApiClientApplication

fun main(args: Array<String>) {
        val applicationBuilder = SpringApplicationBuilder(ApiClientApplication::class.java)
        applicationBuilder.headless(false)
        applicationBuilder.bannerMode(Banner.Mode.OFF)
        applicationBuilder.run(*args)
    }