package edu.backend.taskapp.services

import com.cloudinary.Cloudinary
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CloudinaryConfig {

    @Bean
    fun cloudinary(): Cloudinary {
        val config: MutableMap<String, String> = mutableMapOf()
        config["cloud_name"] = "dtmkcpfjn"
        config["api_key"] = "192366146796999"
        config["api_secret"] = "NXRyLAEAAqTaw1WguIDLznc1vJI"
        return Cloudinary(config)
    }
}
