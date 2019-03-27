package com.schultz.reactdemo.gateways.http

import com.schultz.reactdemo.usecases.RandomNumberGenerator
import org.springframework.cglib.core.Local
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.SynchronousSink
import reactor.util.function.Tuple2
import java.time.Duration
import java.time.LocalDateTime
import java.util.stream.Stream

@RestController("/random")
class RandomController(val randGen: RandomNumberGenerator) {

    @GetMapping("/int",produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getRandoms(): Flux<Int>{
        return randGen.generate(100)
                .checkpoint("In the controller")
                .delayElements(Duration.ofSeconds(6))
    }

    @GetMapping(value = ["/time"],produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getRandomsTime(): Flux<Tuple2<Int,String>>{
        val delayedRandInts = randGen.generate(100)
                .log()
                .checkpoint("In the controller")
                .delayElements(Duration.ofSeconds(1))

        val timeStream = Flux.fromStream(Stream.generate { LocalDateTime.now().toString() })

        return delayedRandInts.zipWith(timeStream).cache(10).log()
    }
}