package com.schultz.reactdemo.gateways.database

import com.schultz.reactdemo.domain.Profile
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ProfileDBGateway(private val repository: ProfileMongoRepository) {

    fun save(profile: Profile): Mono<Profile> {
        return repository.insert(profile)
    }

    fun update(profile: Profile): Mono<Profile> {
        return repository.save(profile)
    }

    fun getProfileByName(name: String): Flux<Profile> {
        return repository.findByName(name)
    }

    fun listProfiles(): Flux<Profile> {
        return repository.findAll()
    }

}