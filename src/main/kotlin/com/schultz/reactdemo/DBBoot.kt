package com.schultz.reactdemo

import com.schultz.reactdemo.domain.Profile
import com.schultz.reactdemo.usecases.profile.SaveProfile
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service

@Service
class DBBoot(private val SaveProfile: SaveProfile) : CommandLineRunner {
    override fun run(vararg args: String?) {
//
//        Flux.create { sink: FluxSink<String> ->
//            while (true) {
//                sink.next("Kaue")
//            }
//        }
//                .take(10000)
//                .doOnSubscribe{ print("Started savig: ${LocalDateTime.now()}")}
//                .doOnTerminate { print("\n\n\ndone Saving ${LocalDateTime.now()}") }
//                .subscribe { name -> saveProfile(name) }
    }

    private fun saveProfile(name: String) {
        val profile = Profile(name)
        SaveProfile.execute(profile)
                .subscribe({})
    }
}