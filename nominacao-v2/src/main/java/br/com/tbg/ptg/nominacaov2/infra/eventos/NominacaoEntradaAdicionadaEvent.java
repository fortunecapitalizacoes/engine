package br.com.tbg.ptg.nominacaov2.infra.eventos;

import org.springframework.data.annotation.Id;

import com.google.gson.Gson;

import br.com.tbg.ptg.nominacaov2.application.dto.NominacaoDTO;
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
	private String volume;
	private String contraparte;
	private String dataOperaciona;
	private String fluxo;

}
