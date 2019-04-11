package com.schultz.reactdemo.usecases.profile

import com.schultz.reactdemo.domain.Profile
import com.schultz.reactdemo.gateways.database.ProfileDBGateway
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class InsertProfile(private val profileGateway: ProfileDBGateway) {

    fun execute(profile: Profile): Mono<Profile> {
        return profileGateway.save(profile)
    }
}