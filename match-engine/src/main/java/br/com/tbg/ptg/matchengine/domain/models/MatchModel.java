package br.com.tbg.ptg.matchengine.domain.models;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Document(collection = "matches")
@NoArgsConstructor
@AllArgsConstructor
public class MatchModel {
    @Id
    private String id;
    private List<NominacaoModel> nominacaoEntradaList = new ArrayList<>();
    private List<NominacaoModel> nominacaoSaidaList = new ArrayList<>();
    private LocalDate dataOperacional;

	
	    
    
    
    
    
    @Data
	    @Builder
	    @NoArgsConstructor
	    @AllArgsConstructor
	    public static class NominacaoModel {
	        private String id;
	        private String nomeCarregador;
	        private double volume;
	        private String contraparte;
	    }
}
