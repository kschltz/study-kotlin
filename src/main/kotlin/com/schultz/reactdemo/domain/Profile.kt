package com.schultz.reactdemo.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Profile(var name: String) {
    @Id
    var uuid: String = UUID.randomUUID().toString()
}
