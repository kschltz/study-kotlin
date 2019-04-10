package com.schultz.reactdemo.gateways.database

import com.schultz.reactdemo.domain.Profile
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ProfileDBGateway(private val repository: ProfileMongoRepository) {

    fun save(profile: Profile): Profile {
        return repository.insert(profile)
    }

    fun update(profile: Profile): Profile {
        return repository.save(profile)
    }

    fun getProfileByName(name: String): Flux<Profile> {
        return Flux.generate { sink -> sink.next(repository.findByName(name)) }
    }

    fun listProfiles(pageable: Pageable): Page<Profile> {
        return repository.findAll(pageable)
    }

}