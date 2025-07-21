package com.fitcore.users.infrastructure.service

import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs  
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.beans.factory.annotation.Value
import com.fitcore.users.infrastructure.config.MinioConfig
import org.slf4j.LoggerFactory
import java.util.UUID

@Service
class StorageService(
    private val client: MinioClient,
    private val minioConfig: MinioConfig,
    @Value("\${minio.bucket}") private val bucket: String
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun uploadProfile(userId: String, file: MultipartFile): String {
        val objectName = "users/$userId/${UUID.randomUUID()}-${file.originalFilename}"
        logger.info("Uploading file to MinIO, bucket: $bucket, object: $objectName")
        
        client.putObject(
            PutObjectArgs.builder()
                .bucket(bucket)
                .`object`(objectName)
                .stream(file.inputStream, file.size, -1)
                .contentType(file.contentType)
                .build()
        )
        logger.info("Upload successful, returning key: $objectName")
        return objectName
    }

    fun getPresignedUrl(objectName: String): String {
        // Construir URL pública direta em vez de URL assinada
        val baseUrl = minioConfig.externalUrl ?: minioConfig.url
        val publicUrl = "$baseUrl/$bucket/$objectName"
        
        logger.info("Generated public URL: $publicUrl")
        return publicUrl
    }
    
    // Novo método para deletar um objeto do bucket
    fun deleteProfile(objectKey: String) {
        logger.info("Deleting object from MinIO: $objectKey")
        client.removeObject(
            RemoveObjectArgs.builder()
                .bucket(bucket)
                .`object`(objectKey)
                .build()
        )
        logger.info("Successfully deleted object: $objectKey")
    }
}