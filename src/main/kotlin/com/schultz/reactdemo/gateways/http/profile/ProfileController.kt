package com.schultz.reactdemo.gateways.http.profile

import com.schultz.reactdemo.domain.Profile
import com.schultz.reactdemo.gateways.database.ProfileDBGateway
import com.schultz.reactdemo.usecases.profile.GetProfileByName
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.LocalDateTime

@RestController
@RequestMapping("/profile")
class ProfileController(private val profileByName: GetProfileByName,
                        private val profileGateway: ProfileDBGateway) {


    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getProfile(@RequestParam("name") name: String,
                   @RequestParam("duration", defaultValue = "15") duration: Long)
            : ResponseEntity<Flux<Profile>> {

        val flux = profileByName.execute(name)
                .doOnError({ throwable -> print("error: ${throwable.message}") })
                .doOnSubscribe { print("Started listing at: ${LocalDateTime.now()}") }
                .doOnComplete { print("\n\nterminated listing at: ${LocalDateTime.now()}\n\n") }
        return ResponseEntity.ok(flux)
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveProfile(@RequestBody profile: Profile): ResponseEntity<Mono<Profile>> {
        val saved = profileGateway.save(profile)

        return ResponseEntity.ok(saved)
    }

    @GetMapping(value = ["/all"], produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun listAll(): ResponseEntity<Flux<Profile>> {
        val listProfiles = profileGateway.listProfiles()
                .delayElements(Duration.ofMillis(100))

        return ResponseEntity.ok(listProfiles)
    }
}