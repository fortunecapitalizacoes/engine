package br.com.tbg.ptg.nominacaov2.application.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import br.com.tbg.ptg.nominacaov2.application.dto.NominacaoDTO;
import br.com.tbg.ptg.nominacaov2.application.exceptions.ExcecoesDeNegocio;

@Service
public class ServicoDeValidacao {
	
	public void aplicarTodas(NominacaoDTO nominacaoDTO) {
		validarDataRN1(LocalDate.parse(nominacaoDTO.getDiaOperacionalInicial()));
		validarQDNouDQSpreenchido(nominacaoDTO);
	//	validacaoNominacaoPorPeriodo(nominacaoDTO);
	}
	
	public void validarDataRN1(LocalDate data) {
		LocalDate dataAtual = LocalDate.now();
		LocalDate dataLimite = dataAtual.plusDays(180);

		if (data.isAfter(dataLimite)) {
			throw new ExcecoesDeNegocio(
					"Não é permitido incluir uma nomeação com data maior que 180 dias da data atual.");
		}
	}

	// Método para validar que apenas um dos campos pode ser preenchido
	public void validarQDNouDQSpreenchido(NominacaoDTO nominacaoDTO) {
	    if ((nominacaoDTO.getQuantidadeDiáriaNominada_QDN().isEmpty() && nominacaoDTO.getDesequilibrio_DQS().isEmpty() ) ||
	        (!nominacaoDTO.getQuantidadeDiáriaNominada_QDN().isEmpty()  && !nominacaoDTO.getDesequilibrio_DQS().isEmpty() )) {
	        throw new ExcecoesDeNegocio("Somente um dos campos deve ser preenchido: DQS ou QDN");
	    }
	}
	
	 public void validacaoNominacaoPorPeriodo(NominacaoDTO nominacaoDTO){
    	 String diaOperacionalFinal = nominacaoDTO.getDiaOperacionalFinal();
         String diaOperacionalInicial = nominacaoDTO.getDiaOperacionalInicial();

         long diasDeDiferenca = ChronoUnit.DAYS.between(LocalDate.parse(diaOperacionalInicial),LocalDate.parse(diaOperacionalFinal));
         
         if (diasDeDiferenca > 180) {
             throw new ExcecoesDeNegocio("O período de operação excede 180 dias.");
         }
    }
}
