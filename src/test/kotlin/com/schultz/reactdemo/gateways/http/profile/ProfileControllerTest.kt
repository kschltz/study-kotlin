package com.schultz.reactdemo.gateways.http.profile

import com.schultz.reactdemo.domain.Profile
import com.schultz.reactdemo.gateways.database.ProfileMongoRepository
import io.mockk.clearAllMocks
import io.mockk.every
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import reactor.core.publisher.Flux.fromStream
import reactor.core.publisher.Mono.just
import reactor.test.StepVerifier.create
import java.util.stream.Stream.iterate


@SpringBootTest
@ActiveProfiles("test")
internal class ProfileControllerTest(@Autowired private val controller: ProfileController,
                                     @Autowired private val mongoRepo: ProfileMongoRepository) {

    @BeforeEach
    fun setup() {

        every { mongoRepo.save(any<Profile>()) } returns just(Profile("Kaue"))
        val source = iterate(0) { it + 1 }.map { Profile("Kaue") }
        every { mongoRepo.findAll() } returns fromStream(source).take(1)

    }

    @Test
    fun loadContext() {

        controller.saveMultiProfiles(100, Profile("Kaue"))
        val flux = controller.listAll().body ?: throw RuntimeException("flux expected")

        create(flux)
                .thenConsumeWhile { it.name == "Kaue" }
                .verifyComplete()

    }

    @AfterEach
    fun cleanup() {
        clearAllMocks()
    }

}