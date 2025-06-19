package pl.blaszak.ai.demoapiclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

/*@EnableConfigurationProperties(SecretProperties::class)*/
@ConfigurationPropertiesScan
@SpringBootApplication
class ApiClientApplication {

}

    fun main(args: Array<String>) {
        val applicationBuilder = SpringApplicationBuilder(ApiClientApplication::class.java)
        applicationBuilder.headless(false)
        applicationBuilder.run(*args)
    }