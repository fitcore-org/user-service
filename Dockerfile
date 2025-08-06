# Etapa 1: Build da aplicação com Gradle e cache ativado
FROM gradle:8.7.0-jdk17 AS build
WORKDIR /app
COPY . .
# Adiciona --build-cache e --parallel para acelerar o build
RUN gradle bootJar --no-daemon --build-cache --parallel

# Esta etapa usa uma ferramenta do Spring para separar o JAR em camadas para o cache do Docker
FROM build as extractor
WORKDIR /app
RUN java -Djarmode=layertools -jar build/libs/*SNAPSHOT.jar extract

# Etapa 2: Imagem final, montada em camadas para máximo aproveitamento do cache
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia as camadas separadamente
COPY --from=extractor /app/dependencies/ ./
COPY --from=extractor /app/spring-boot-loader/ ./
COPY --from=extractor /app/snapshot-dependencies/ ./
COPY --from=extractor /app/application/ ./

EXPOSE 8081

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]