package com.schultz.reactdemo.gateways.http.profile

import com.schultz.reactdemo.domain.Profile
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.test.StepVerifier.create


@SpringBootTest
internal class ProfileControllerTest(@Autowired private val controller: ProfileController) {

    @Test
    fun loadContext() {

        controller.saveMultiProfiles(100, Profile("Kaue"))
        val flux = controller.listAll().body ?: throw RuntimeException("flux expected")

        create(flux)
                .thenConsumeWhile { it.name == "Kaue" }
                .verifyComplete()

    }
}