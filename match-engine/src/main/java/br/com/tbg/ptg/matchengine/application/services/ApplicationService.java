package br.com.tbg.ptg.matchengine.application.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import br.com.tbg.ptg.matchengine.application.events.NominacaoEntradaAdicionadaEvent;
import br.com.tbg.ptg.matchengine.application.events.NominacaoSaidaAdicionadaEvent;
import br.com.tbg.ptg.matchengine.domain.models.MatchModel;
import br.com.tbg.ptg.matchengine.domain.models.MatchModel.NominacaoModel;

@Service
public class ApplicationService {

	public NominacaoModel deEventoParaModel(NominacaoEntradaAdicionadaEvent event) {

		return NominacaoModel.builder().id(event.getId()).nomeCarregador(event.getNomeCarregador())
				.contraparte(event.getContraparte()).volume(event.getVolume()).build();
	}

	public NominacaoModel deEventoParaModel(NominacaoSaidaAdicionadaEvent event) {

		return NominacaoModel.builder().id(event.getId()).nomeCarregador(event.getNomeCarregador())
				.contraparte(event.getContraparte()).volume(event.getVolume()).build();
	}

	public MatchModel verificaSeExisteContraparteEvendoRecebido(List<MatchModel> listaMatchsDeHoje,
			NominacaoEntradaAdicionadaEvent eventNominacaoEntrada) {

		for (MatchModel match : listaMatchsDeHoje) {
			for (NominacaoModel nominacaoSaida : match.getNominacaoSaidaList()) {
				if (eventNominacaoEntrada.getNomeCarregador().equals(nominacaoSaida.getContraparte())
						&& eventNominacaoEntrada.getContraparte().equals(nominacaoSaida.getNomeCarregador())) {
					match.getNominacaoEntradaList().add(deEventoParaModel(eventNominacaoEntrada));
					return match;
				}
			}
			for (NominacaoModel nominacaoEntrada : match.getNominacaoEntradaList()) {
				if (eventNominacaoEntrada.getNomeCarregador().equals(nominacaoEntrada.getNomeCarregador())
						&& eventNominacaoEntrada.getContraparte().equals(nominacaoEntrada.getContraparte())) {
					match.getNominacaoEntradaList().add(deEventoParaModel(eventNominacaoEntrada));
					return match;
				}
			}

		}

		return MatchModel.builder().nominacaoEntradaList(List.of(deEventoParaModel(eventNominacaoEntrada))).build();
	}

	public MatchModel verificaSeExisteContraparteEvendoRecebido(List<MatchModel> listaMatchsDeHoje,
			NominacaoSaidaAdicionadaEvent eventNominacaoSaida) {

		for (MatchModel match : listaMatchsDeHoje) {
			for (NominacaoModel nominacaoSaida : match.getNominacaoEntradaList()) {
				if (eventNominacaoSaida.getNomeCarregador().equals(nominacaoSaida.getContraparte())
						&& nominacaoSaida.getNomeCarregador().equals(eventNominacaoSaida.getContraparte())

				) {
					match.getNominacaoSaidaList().add(deEventoParaModel(eventNominacaoSaida));
					return match;
				}

			}
			for (NominacaoModel nominacaoSaida : match.getNominacaoSaidaList()) {
				if (eventNominacaoSaida.getNomeCarregador().equals(nominacaoSaida.getNomeCarregador())
						&& eventNominacaoSaida.getContraparte().equals(nominacaoSaida.getContraparte())) {
					match.getNominacaoSaidaList().add(deEventoParaModel(eventNominacaoSaida));
					return match;
				}
			}

		}

		return MatchModel.builder().id(UUID.randomUUID().toString())
				.nominacaoSaidaList(List.of(deEventoParaModel(eventNominacaoSaida))).dataOperacional(LocalDate.now())
				.build();
	}

}
