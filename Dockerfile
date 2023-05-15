# Use a imagem base do OpenJDK 11
FROM adoptopenjdk:11-jre-hotspot

# Copie o arquivo JAR da aplicação para dentro do contêiner
COPY target/store-products-0.0.1-SNAPSHOT.jar /app/app.jar

# Defina o diretório de trabalho como o diretório raiz da aplicação
WORKDIR /app

# Exponha a porta que a aplicação está escutando
EXPOSE 8080

# Defina o comando de execução para iniciar a aplicação
CMD ["java", "-jar", "app.jar"]
