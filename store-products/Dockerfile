# Pegando imagem do dockerhub
FROM maven:3.8.4-openjdk-11 AS builder

# Definindo a pasta para receber o codigo
ENV APP_HOME=/app/src

RUN mkdir -p $APP_HOME
WORKDIR $APP_HOME

# Copiando codigo fonte para pasta criada
COPY . $APP_HOME

# Limpando a pasta de destino
RUN rm -rf target

# Buildando aplicação
RUN mvn clean install

FROM openjdk:11

COPY --from=builder /app/src/target/store-products-1.0.0-SNAPSHOT.jar /app/store-products.jar

ENTRYPOINT ["java", "-jar", "store-products.jar"]
