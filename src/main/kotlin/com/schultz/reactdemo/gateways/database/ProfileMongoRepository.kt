package com.schultz.reactdemo.gateways.database

import com.schultz.reactdemo.domain.Profile
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
interface ProfileMongoRepository : ReactiveMongoRepository<Profile, String> {

    fun findByName(name: String): Flux<Profile>
}