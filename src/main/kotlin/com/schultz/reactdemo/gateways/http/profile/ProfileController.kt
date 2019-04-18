package com.schultz.reactdemo.gateways.http.profile

import com.schultz.reactdemo.domain.Profile
import com.schultz.reactdemo.gateways.database.ProfileDBGateway
import com.schultz.reactdemo.usecases.profile.GetProfileByName
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.util.stream.Stream

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
    fun saveMultiProfiles(@RequestParam(defaultValue = "1") count: Long,
                          @RequestBody profile: Profile): ResponseEntity<Any> {

        Flux.fromStream(Stream.iterate(0) { i -> i + 1 })
                .take(count)
                .doOnSubscribe { print("Started saving: ${LocalDateTime.now()}") }
                .doOnTerminate { print("\n\n\ndone Saving ${LocalDateTime.now()}") }
                .subscribe { profileGateway.save(profile.copy()).subscribe() }
        return ResponseEntity.ok().build()

    }

    @GetMapping(value = ["/all"], produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun listAll(): ResponseEntity<Flux<Profile>> {
        val listProfiles = profileGateway.listProfiles()

        return ResponseEntity.ok(listProfiles)
    }
}