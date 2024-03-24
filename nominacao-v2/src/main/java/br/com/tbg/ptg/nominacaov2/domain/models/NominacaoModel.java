package br.com.tbg.ptg.nominacaov2.domain.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NominacaoModel {
	@Id
	private String id;
	private String diaOperacionalInicial;
	private String diaOperacionalFinal;
	private String ciclo; //1-diaria/2-intradiaria
	private String fluxo; // define se é entrada ou saida
	private String contrato;
	private String instalacao;
	private String quantidadeDiáriaNominada_QDN;
	private String desequilibrio_DQS;
	private String carregadorContraparte;
	private String carregador;
	
	//camposLogs
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
}
