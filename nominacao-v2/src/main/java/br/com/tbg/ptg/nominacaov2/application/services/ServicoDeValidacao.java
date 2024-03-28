package br.com.tbg.ptg.nominacaov2.application.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import br.com.tbg.ptg.nominacaov2.application.dto.NominacaoDTO;
import br.com.tbg.ptg.nominacaov2.application.exceptions.ExcecoesDeNegocio;

@Service
public class ServicoDeValidacao {
	
	public void aplicarTodas(NominacaoDTO nominacaoDTO) {
		validarDataRN1(LocalDate.parse(nominacaoDTO.getDiaOperacionalInicial()));
		validarQDNouDQSpreenchido(nominacaoDTO);
	}
	
	public void validarDataRN1(LocalDate data) {
		LocalDate dataAtual = LocalDate.now();
		LocalDate dataLimite = dataAtual.plusDays(180);

		if (data.isAfter(dataLimite)) {
			throw new IllegalArgumentException(
					"Não é permitido incluir uma nomeação com data maior que 180 dias da data atual.");
		}
	}

	// Método para validar que apenas um dos campos pode ser preenchido
	public void validarQDNouDQSpreenchido(NominacaoDTO nominacaoDTO) {
		if ((nominacaoDTO.getQuantidadeDiáriaNominada_QDN() != null && nominacaoDTO.getDesequilibrio_DQS() != null)
				|| (nominacaoDTO.getQuantidadeDiáriaNominada_QDN() == null
						&& nominacaoDTO.getDesequilibrio_DQS() == null)) {
			throw new ExcecoesDeNegocio("Somente um dos campos deve ser preenchido ou DQS ou QDN");
		}
	}
}
