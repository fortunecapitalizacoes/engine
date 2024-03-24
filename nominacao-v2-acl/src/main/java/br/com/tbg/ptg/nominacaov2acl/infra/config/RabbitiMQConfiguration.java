package br.com.tbg.ptg.nominacaov2acl.infra.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitiMQConfiguration {
	 @Bean
	    public Queue criaNovaFila() {
	        return new Queue("fila-nominacao-v2-acl");
	    }
}
