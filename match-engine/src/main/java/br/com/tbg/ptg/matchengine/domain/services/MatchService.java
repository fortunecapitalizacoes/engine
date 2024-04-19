package br.com.tbg.ptg.matchengine.domain.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.tbg.ptg.matchengine.application.events.NominacaoEntradaAdicionadaEvent;
import br.com.tbg.ptg.matchengine.application.events.NominacaoSaidaAdicionadaEvent;
import br.com.tbg.ptg.matchengine.application.services.ApplicationService;
import br.com.tbg.ptg.matchengine.application.services.RegraMenorDoParService;
import br.com.tbg.ptg.matchengine.domain.models.MatchModel;
import br.com.tbg.ptg.matchengine.domain.models.MatchModel.NominacaoModel;
import br.com.tbg.ptg.matchengine.domain.repository.IMatchRepository;

@Service
public class MatchService {
	
	@Autowired
	private RegraMenorDoParService regraMenorDoParService;
	
	@Autowired
	private IMatchRepository matchRepository;
	
	@Autowired
	private ApplicationService applicationService;

	public void eventoNominacaoEntradaRecebito(NominacaoEntradaAdicionadaEvent event) {
			var listaMatchs = buscaMatchsPorPeriodo(LocalDate.now(), LocalDate.now().plusDays(190));
			if(!editar(listaMatchs, event)) {
				var matchModel = applicationService.verificaSeExisteContraparteEvendoRecebido(listaMatchs, event);
				gravarMatch(regraMenorDoParService.aplicar(matchModel));
			}		
	}
	
	private boolean editar(List<MatchModel> listaMatchs, NominacaoEntradaAdicionadaEvent event) {
	    boolean matchEncontrado = false;
	    for (MatchModel match : listaMatchs) {
	        for (NominacaoModel nominacao : match.getNominacaoEntradaList()) {
	            if (nominacao.getId().equals(event.getId())) {
	                nominacao.setVolume(event.getVolume());
	                matchEncontrado = true;
	                matchRepository.save(match);
	                break; // Uma vez que encontramos e atualizamos a nominacao, podemos sair do loop interno
	            }
	        }
	    }
	    return matchEncontrado;
	}
	
	private boolean editar(List<MatchModel> listaMatchs, NominacaoSaidaAdicionadaEvent event) {
	    boolean matchEncontrado = false;
	    for (MatchModel match : listaMatchs) {
	        for (NominacaoModel nominacao : match.getNominacaoSaidaList()) {
	            if (nominacao.getId().equals(event.getId())) {
	                nominacao.setVolume(event.getVolume());
	                matchEncontrado = true;
	                matchRepository.save(match);
	                break; // Uma vez que encontramos e atualizamos a nominacao, podemos sair do loop interno
	            }
	        }
	    }
	    return matchEncontrado;
	}


	public void eventoNominacaoSaidaRecebito(NominacaoSaidaAdicionadaEvent event) {
		var listaMatchs = buscaMatchsPorPeriodo(LocalDate.now(), LocalDate.now().plusDays(190));
		if(!editar(listaMatchs, event)) {
			var matchModel = applicationService.verificaSeExisteContraparteEvendoRecebido(listaMatchs, event);
			gravarMatch(regraMenorDoParService.aplicar(matchModel));
		}
	}

	private void gravarMatch(MatchModel match) {
		matchRepository.save(addIdDataOperacionalCasoNaoPossua(match));
	}

	private MatchModel addIdDataOperacionalCasoNaoPossua(MatchModel match) {
	    match.setDataOperacional(match.getDataOperacional() == null ? LocalDate.now() : match.getDataOperacional());
	    match.setId(match.getId() == null ? UUID.randomUUID().toString() : match.getId());
	    return match;
	}

	public List<MatchModel> todosOsMatchsPorDiaOperacional(LocalDate date) {
		return matchRepository.findByDataOperacional(date);
	}
	
	public List<MatchModel> buscaMatchsPorPeriodo(LocalDate date1, LocalDate date2) {
		return matchRepository.findByDataOperacionalBetween(date1, date2);
	}

	public List<MatchModel> totosOsMaths() {
		return matchRepository.findAll();
	}
}
