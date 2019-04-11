package com.schultz.reactdemo.gateways.http.profile

import com.schultz.reactdemo.domain.Profile
import com.schultz.reactdemo.usecases.profile.GetProfileByName
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/profile")
class ProfileController(private val profileByName: GetProfileByName) {


    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getProfile(@RequestParam("name") name: String,
                   @RequestParam("duration", defaultValue = "15") duration: Long)
            : ResponseEntity<Flux<Profile>> {

        val flux = profileByName.execute(name)
                .doOnError({ throwable -> print("error: ${throwable.message}") })
                .doOnComplete { print("\n\nterminated\n\n") }
        return ResponseEntity.ok(flux)
    }
}