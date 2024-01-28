package com.cocoa.brownie

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BrownieApplication

fun main(args: Array<String>) {
    runApplication<BrownieApplication>(*args)
}
