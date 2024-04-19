FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/nominacao-v2-0.0.1-SNAPSHOT.jar /app/nominacao-v2.jar

# Exponha a porta em que o aplicativo Spring está sendo executado
EXPOSE 8020

CMD ["java", "-jar", "nominacao-v2.jar"]
