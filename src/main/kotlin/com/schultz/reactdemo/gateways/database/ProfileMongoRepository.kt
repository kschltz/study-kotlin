package com.schultz.reactdemo.gateways.database

import com.schultz.reactdemo.domain.Profile
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service

@Service
interface ProfileMongoRepository : MongoRepository<Profile, String> {

    fun findByName(name: String): Profile
}