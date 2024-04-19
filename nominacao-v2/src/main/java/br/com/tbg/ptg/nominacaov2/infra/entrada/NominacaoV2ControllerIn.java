package br.com.tbg.ptg.nominacaov2.infra.entrada;

import br.com.tbg.ptg.nominacaov2.application.dto.NominacaoDTO;
import br.com.tbg.ptg.nominacaov2.domain.services.NominacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nominacao-v2")
public class NominacaoV2ControllerIn {

	@Autowired
	private NominacaoService nominacaoService;
    
    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody NominacaoDTO nominacaoDTO) {
    	nominacaoService.salvar(nominacaoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(nominacaoDTO);
    }
    
    @PostMapping("/editar")
    public ResponseEntity<?> editar(@RequestBody NominacaoDTO nominacaoDTO) {
    	nominacaoService.editar(nominacaoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(nominacaoDTO);
    }
}
