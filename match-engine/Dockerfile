FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/match-engine-0.0.1-SNAPSHOT.jar /app/match-engine.jar

# Exponha a porta em que o aplicativo Spring está sendo executado
EXPOSE 8010

CMD ["java", "-jar", "match-engine.jar"]
