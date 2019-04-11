package com.schultz.reactdemo

import com.schultz.reactdemo.domain.Profile
import com.schultz.reactdemo.usecases.profile.InsertProfile
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service

@Service
class DBBoot(private val insertProfile: InsertProfile) : CommandLineRunner {
    override fun run(vararg args: String?) {

        repeat(1000000, { saveProfile("Kaue") })
    }

    private fun saveProfile(name: String) {
        val profile = Profile(name)
        insertProfile.execute(profile)
                .subscribe({})
    }
}