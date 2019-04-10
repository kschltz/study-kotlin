package com.schultz.reactdemo.usecases.random

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.util.*

@Service
class RandomNumberGenerator {

    fun generate(limit: Int): Flux<Int> {
        val random = Random()
        return Flux.generate{ sink -> sink.next(random.nextInt(limit))}
    }
}