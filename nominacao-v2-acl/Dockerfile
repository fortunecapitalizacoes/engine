FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/nominacao-v2-acl-0.0.1-SNAPSHOT.jar /app/nominacao-v2-acl.jar

# Exponha a porta em que o aplicativo Spring está sendo executado
EXPOSE 8030

CMD ["java", "-jar", "nominacao-v2-acl.jar"]
