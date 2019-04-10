package com.schultz.reactdemo

import com.schultz.reactdemo.domain.Profile
import com.schultz.reactdemo.usecases.profile.InsertProfile
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service

@Service
class DBBoot(private val insertProfile: InsertProfile) : CommandLineRunner {
    override fun run(vararg args: String?) {

        listOf("Kaue", "Jeremias", "Manoel").forEach { name ->
            val profile = Profile(name)
            val savedProfile = insertProfile.execute(profile)
            print("Name: $savedProfile")
        }

    }
}