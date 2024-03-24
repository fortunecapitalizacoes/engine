package br.com.tbg.ptg.nominacaov2.infra.saida;

import static br.com.tbg.ptg.nominacaov2.application.constantes.ConstantesApplicationUtil.ENTRADA;
import static br.com.tbg.ptg.nominacaov2.application.constantes.ConstantesApplicationUtil.SAIDA;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.tbg.ptg.nominacaov2.application.dto.NominacaoDTO;
import br.com.tbg.ptg.nominacaov2.application.services.ApplicationService;

@Service
@Component
public class RabbitiMqEnventOut {
	private final RabbitTemplate rabbitTemplate;
    private final ApplicationService applicationService;
 
     
    @Autowired
    public RabbitiMqEnventOut(RabbitTemplate rabbitTemplate, ApplicationService applicationService) {
        this.rabbitTemplate = rabbitTemplate;
        this.applicationService = applicationService;
    }
    
    //fluxo = entrada ou saida
    public void verificaFluxoCriaEvento(NominacaoDTO dto) {
    	dto.setId(UUID.randomUUID().toString());
    	if(dto.getFluxo().equals(ENTRADA)) {
    		enviarMensagemNominacaoEntrada(applicationService.criaNominacaoEntradaAdicionadaEvent(dto));
    	}
    	
    	if(dto.getFluxo().equals(SAIDA)) {
    		enviarMensagemNominacaoSaida(applicationService.criaNominacaoSaidaAdicionadaEvent(dto));
    	}
    }
        
    private void enviarMensagemNominacaoEntrada(String mensagem) {
        rabbitTemplate.convertAndSend("fila-nominacao-entrada", mensagem);
        System.out.println("Mensagem enviada: " + mensagem);
    }
    
    private void enviarMensagemNominacaoSaida(String mensagem) {
        rabbitTemplate.convertAndSend("fila-nominacao-saida", mensagem);
        System.out.println("Mensagem enviada: " + mensagem);
    }
    
    public void notificarAclNominacao(NominacaoDTO dto) {
    	rabbitTemplate.convertAndSend("fila-nominacao-v2-acl", applicationService.notificaAclNominacaoV2(dto));
    }
   
}
