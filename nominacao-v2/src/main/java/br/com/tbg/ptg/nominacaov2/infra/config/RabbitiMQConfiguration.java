package br.com.tbg.ptg.nominacaov2.infra.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitiMQConfiguration {

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqHost);
        return connectionFactory;
    }

    @Bean
    public Queue filaNominacaoEntrada() {
        return new Queue("fila-nominacao-entrada");
    }

    @Bean
    public Queue filaNominacaoSaida() {
        return new Queue("fila-nominacao-saida");
    }
}
