package br.com.tbg.ptg.matchengine.application.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NominacaoEntradaAdicionadaEvent {
	private String id;
	private String nomeCarregador;
	private double volume;
	private String contraparte;
	private String dataOperaciona;
	private String fluxo;
	
}
