package com.fitcore.users.infrastructure.config

import io.minio.MinioClient
import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "minio")
class MinioConfig {
    lateinit var url: String
    lateinit var accessKey: String
    lateinit var secretKey: String
    lateinit var bucket: String

    var externalUrl: String? = null

    @Bean
    fun minioClient(): MinioClient {
        val client = MinioClient.builder()
            .endpoint(url)
            .credentials(accessKey, secretKey)
            .build()
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build())
        }
        return client
    }
}
