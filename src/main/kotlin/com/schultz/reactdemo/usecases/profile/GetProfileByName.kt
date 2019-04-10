package com.schultz.reactdemo.usecases.profile

import com.schultz.reactdemo.domain.Profile
import com.schultz.reactdemo.gateways.database.ProfileDBGateway
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class GetProfileByName(private val profileDBGateway: ProfileDBGateway) {

    fun execute(name: String): Flux<Profile> {
        return profileDBGateway.getProfileByName(name)
    }
}