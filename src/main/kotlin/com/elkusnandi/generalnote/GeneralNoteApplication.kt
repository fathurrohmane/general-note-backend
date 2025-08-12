package com.elkusnandi.generalnote

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(
    scanBasePackages = ["com.elkusnandi.common", "com.elkusnandi.playground"]
)
@EnableJpaRepositories(
    basePackages = ["com.elkusnandi.common.repository", "com.elkusnandi.playground.repository"]
)
@EntityScan(
    basePackages = ["com.elkusnandi.common.entity", "com.elkusnandi.playground.entity"]
)
class GeneralNoteApplication

fun main(args: Array<String>) {
    runApplication<GeneralNoteApplication>(*args)
}
