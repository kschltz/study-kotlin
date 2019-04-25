package com.schultz.reactdemo.gateways.http.profile

import com.schultz.reactdemo.gateways.database.ProfileMongoRepository
import io.mockk.mockk
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile

@Profile("test")
@Configuration
class ProfileTestConfig {

    @Bean
    @Primary
    fun mockMongoRepo(): ProfileMongoRepository {
        return mockk()
    }
}