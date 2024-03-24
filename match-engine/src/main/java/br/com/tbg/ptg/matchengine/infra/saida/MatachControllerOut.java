package br.com.tbg.ptg.matchengine.infra.saida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tbg.ptg.matchengine.domain.services.MatchService;

@RestController
@RequestMapping("/match")
public class MatachControllerOut {
		
	@Autowired
    private MatchService serviceDomain;
    
    @GetMapping
    public ResponseEntity<?> todosMatchsDoDia() {
        return ResponseEntity.status(HttpStatus.OK).body(serviceDomain.todosOsMatchsPorDiaOperacional());
    }
}
