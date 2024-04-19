package br.com.tbg.ptg.matchengine.infra.saida;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tbg.ptg.matchengine.domain.services.MatchService;

@RestController
@RequestMapping("/match")
public class MatachControllerOut {

	@Autowired
	private MatchService serviceDomain;

	@GetMapping("/{dataOperacional}")
	public ResponseEntity<?> todosMatchsDoDia(
			@PathVariable("dataOperacional") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataOperacional) {
		return ResponseEntity.status(HttpStatus.OK).body(serviceDomain.todosOsMatchsPorDiaOperacional(dataOperacional));
	}

	@GetMapping("/periodo")
	public ResponseEntity<?> todosMatchsPorPeriodo() {
		var matchs = serviceDomain.buscaMatchsPorPeriodo(LocalDate.now(), LocalDate.now().plusDays(190));
		return ResponseEntity.ok(matchs);

	}
}
