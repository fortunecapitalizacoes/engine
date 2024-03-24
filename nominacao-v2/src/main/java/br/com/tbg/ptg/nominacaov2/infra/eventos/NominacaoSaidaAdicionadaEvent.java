package br.com.tbg.ptg.nominacaov2.infra.eventos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NominacaoSaidaAdicionadaEvent {
	
	private String id;
	private String nomeCarregador;
	private String volume;
	private String contraparte;
	private String dataOperaciona;
	private String fluxo;
		
}
