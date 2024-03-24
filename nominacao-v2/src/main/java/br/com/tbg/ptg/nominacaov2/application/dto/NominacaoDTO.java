package br.com.tbg.ptg.nominacaov2.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.google.gson.Gson;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NominacaoDTO {
	
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
	
}
