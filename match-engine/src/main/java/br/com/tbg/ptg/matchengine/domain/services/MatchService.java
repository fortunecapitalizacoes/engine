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
		var listaMatchsDeHoje = todosOsMatchsPorDiaOperacional();
		var matchModel = applicationService.verificaSeExisteContraparteEvendoRecebido(listaMatchsDeHoje, event);
		gravarMatch(regraMenorDoParService.aplicar(matchModel));
	}

	public void eventoNominacaoSaidaRecebito(NominacaoSaidaAdicionadaEvent event) {
		var listaMatchsDeHoje = todosOsMatchsPorDiaOperacional();
		var matchModel = applicationService.verificaSeExisteContraparteEvendoRecebido(listaMatchsDeHoje, event);
		gravarMatch(regraMenorDoParService.aplicar(matchModel));
	}

	private void gravarMatch(MatchModel match) {
		matchRepository.save(addIdDataOperacionalCasoNaoPossua(match));
	}

	private MatchModel addIdDataOperacionalCasoNaoPossua(MatchModel match) {
	    match.setDataOperacional(match.getDataOperacional() == null ? LocalDate.now() : match.getDataOperacional());
	    match.setId(match.getId() == null ? UUID.randomUUID().toString() : match.getId());
	    return match;
	}

	public List<MatchModel> todosOsMatchsPorDiaOperacional() {
		return matchRepository.findByDataOperacional(LocalDate.now());
	}

	public List<MatchModel> totosOsMaths() {
		return matchRepository.findAll();
	}
}
