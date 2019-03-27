package com.schultz.reactdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactDemoApplication

fun main(args: Array<String>) {
	runApplication<ReactDemoApplication>(*args)
}
