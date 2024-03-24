package br.com.tbg.ptg.matchengine.infra.entrada;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import br.com.tbg.ptg.matchengine.application.events.NominacaoEntradaAdicionadaEvent;
import br.com.tbg.ptg.matchengine.application.events.NominacaoSaidaAdicionadaEvent;
import br.com.tbg.ptg.matchengine.domain.services.MatchService;

@Service
public class RabbitmqListener {
	
	@Autowired
	private MatchService serviceDomain;
	
    @RabbitListener(queues = "fila-nominacao-entrada", exclusive = false)
    public void receberNominacaoEntrada(String evento) {
        System.out.println("Mensagem recebida: " + nominacaoEntradaAdicionadaEvent(evento));
        serviceDomain.eventoNominacaoEntradaRecebito(nominacaoEntradaAdicionadaEvent(evento));
    }
    
    @RabbitListener(queues = "fila-nominacao-saida", exclusive = false)
    public void receberNominacaoSaida(String evento) {
        System.out.println("Mensagem recebida: " + nominacaoSaidaAdicionadaEvent(evento)); 
        serviceDomain.eventoNominacaoSaidaRecebito(nominacaoSaidaAdicionadaEvent(evento));
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
