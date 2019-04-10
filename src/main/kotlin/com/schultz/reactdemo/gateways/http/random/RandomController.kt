package com.schultz.reactdemo.gateways.http.random

import com.schultz.reactdemo.usecases.random.RandomNumberGenerator
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.util.function.Tuple2
import java.time.Duration
import java.time.LocalDateTime
import java.util.stream.Stream

@RestController
@RequestMapping("/random")
class RandomController(val randGen: RandomNumberGenerator) {

    @GetMapping(produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getRandoms(@RequestParam(required = false, defaultValue = "100") threshold: Int,
                   @RequestParam(required = false, defaultValue = "1") delay: Long,
                   @RequestParam(required = false, defaultValue = "10") duration: Long)
            : ResponseEntity<Flux<Int>> {

        return ResponseEntity.ok(randGen.generate(threshold)
                .checkpoint("In the controller")
                .delayElements(Duration.ofSeconds(delay))
                .take(Duration.ofSeconds(duration)))
    }

    @GetMapping(value = ["/time"], produces = [MediaType.APPLICATION_STREAM_JSON_VALUE])
    fun getRandomsTime(): Flux<Tuple2<Int, String>> {
        val delayedRandInts = randGen.generate(100)
                .log()
                .checkpoint("In the controller")
                .delayElements(Duration.ofSeconds(1))

        val timeStream = Flux.fromStream(Stream.generate { LocalDateTime.now().toString() })

        return delayedRandInts.zipWith(timeStream).cache(10).log()

    }
}