package br.com.tbg.ptg.nominacaov2acl.infra.entrada;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import br.com.tbg.ptg.nominacaov2acl.application.events.NominacaoEntradaAdicionadaEvent;
import br.com.tbg.ptg.nominacaov2acl.application.events.NominacaoSaidaAdicionadaEvent;

@Service
public class RabbitmqListener {
	    
    @RabbitListener(queues = "fila-nominacao-v2-acl", exclusive = false)
    public void receberNominacaoSaida(String evento) {
        System.out.println("Notificação recebida: " + evento); 
      
    }
    
    public static NominacaoEntradaAdicionadaEvent nominacaoEntradaAdicionadaEvent(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, NominacaoEntradaAdicionadaEvent.class);
    }
    
    public static NominacaoSaidaAdicionadaEvent nominacaoSaidaAdicionadaEvent(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, NominacaoSaidaAdicionadaEvent.class);
    }
         
}
