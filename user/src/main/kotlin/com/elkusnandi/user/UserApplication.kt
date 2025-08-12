package com.elkusnandi.user

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(
    scanBasePackages = ["com.elkusnandi.common"]
)
@EnableJpaRepositories(basePackages = ["com.elkusnandi.common.repository"])
@EntityScan(basePackages = ["com.elkusnandi.common.entity"])
internal object UserApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(UserApplication::class.java, *args)
    }
}