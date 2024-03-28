package br.com.tbg.ptg.nominacaov2.application.services;

import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import br.com.tbg.ptg.nominacaov2.application.dto.NominacaoDTO;
import br.com.tbg.ptg.nominacaov2.domain.models.NominacaoModel;
import br.com.tbg.ptg.nominacaov2.infra.eventos.NominacaoEntradaAdicionadaEvent;
import br.com.tbg.ptg.nominacaov2.infra.eventos.NominacaoSaidaAdicionadaEvent;

@Service
public class ApplicationService {
	 Gson gson = new Gson();
	
	 public String criaNominacaoSaidaAdicionadaEvent(NominacaoDTO dto) {		
	    return gson.toJson(NominacaoSaidaAdicionadaEvent.builder()
												    		.id(dto.getId())
												    		.dataOperaciona(dto.getDiaOperacionalInicial())
												    		.volume(dto.getQuantidadeDiáriaNominada_QDN())
												    		.nomeCarregador(dto.getCarregador())
												    		.contraparte(dto.getCarregadorContraparte())
												    		.fluxo(dto.getFluxo())
											    		.build());
	}
	
	public String criaNominacaoEntradaAdicionadaEvent(NominacaoDTO dto) {		
	    return gson.toJson(NominacaoEntradaAdicionadaEvent.builder()
													    		.id(dto.getId())
													    		.dataOperaciona(dto.getDiaOperacionalInicial())
													    		.contraparte(dto.getCarregadorContraparte())
													    		.volume(dto.getQuantidadeDiáriaNominada_QDN())
													    		.nomeCarregador(dto.getCarregador())
													    		.fluxo(dto.getFluxo())
													    	.build());
	}
	
	public String criaNominacaoEntradaDSQAdicionadaEvent(NominacaoDTO dto) {	
		if(dto.getCarregador().equals(dto.getCarregadorContraparte())) {
			
		}
	    return gson.toJson(NominacaoEntradaAdicionadaEvent.builder()
													    		.id(dto.getId())
													    		.dataOperaciona(dto.getDiaOperacionalInicial())
													    		.contraparte(dto.getCarregadorContraparte())
													    		.volume(dto.getDesequilibrio_DQS())
													    		.nomeCarregador(dto.getCarregador())
													    		.fluxo(dto.getFluxo())
													    	.build());
	}
	
	public NominacaoModel deDtoParaNominacaoModel(NominacaoDTO nominacaoDTO) {
		return NominacaoModel.builder()
								.id(nominacaoDTO.getId())
								.carregador(nominacaoDTO.getCarregador())
								.carregadorContraparte(nominacaoDTO.getCarregadorContraparte())
								.ciclo(nominacaoDTO.getCiclo())
								.contrato(nominacaoDTO.getContrato())
								.desequilibrio_DQS(nominacaoDTO.getDesequilibrio_DQS())
								.diaOperacionalInicial(nominacaoDTO.getDiaOperacionalInicial())
								.fluxo(nominacaoDTO.getFluxo())
								.instalacao(nominacaoDTO.getInstalacao())
								.quantidadeDiáriaNominada_QDN(nominacaoDTO.getQuantidadeDiáriaNominada_QDN())
							.build();
	}
	
	public String notificaAclNominacaoV2(NominacaoDTO nominacaoDTO) {
		return gson.toJson(nominacaoDTO.builder()
											.id(nominacaoDTO.getId())
											.carregador(nominacaoDTO.getCarregador())
											.carregadorContraparte(nominacaoDTO.getCarregadorContraparte())
											.ciclo(nominacaoDTO.getCiclo())
											.contrato(nominacaoDTO.getContrato())
											.desequilibrio_DQS(nominacaoDTO.getDesequilibrio_DQS())
											.diaOperacionalInicial(nominacaoDTO.getDiaOperacionalInicial())
											.fluxo(nominacaoDTO.getFluxo())
											.instalacao(nominacaoDTO.getInstalacao())
											.quantidadeDiáriaNominada_QDN(nominacaoDTO.getQuantidadeDiáriaNominada_QDN())
										.build());
	}
}
