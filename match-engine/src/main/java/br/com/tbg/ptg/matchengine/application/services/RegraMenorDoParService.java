package br.com.tbg.ptg.matchengine.application.services;

import org.springframework.stereotype.Service;
import br.com.tbg.ptg.matchengine.domain.models.MatchModel;
import br.com.tbg.ptg.matchengine.domain.models.MatchModel.NominacaoModel;
import java.util.List;

@Service
public class RegraMenorDoParService {

    private static final double SEM_MARGEM = 0.0;
    private static final String SEM_FLUXO = "";

    public MatchModel aplicar(MatchModel match) {
        var margem = SEM_MARGEM;
        var fluxo = SEM_FLUXO;
       
        if (validar(match)) {
            margem = gerarMargem(match);
            System.out.println("margem: " + margem);
            fluxo = gerarFluxo(match);
            System.out.println("fluxo: " + fluxo);
            return aplicarMargem(match, fluxo, margem);
        }
       
        return match;
    }
    
    private MatchModel aplicarMargem(MatchModel match, String fluxo, double margem) {
    	
    	if(fluxo.equals("Entrada")) {
    		match.getNominacaoEntradaList().forEach(nominacao -> {
    			nominacao.setVolume( nominacao.getVolume() * margem);
    		});
    	}
    	
    	if(fluxo.equals("Saída")) {
    		match.getNominacaoSaidaList().forEach(nominacao -> {
    			nominacao.setVolume(nominacao.getVolume() * margem);
    		});
    	}
    	    	
    	return match;
    }

    private double gerarMargem(MatchModel matchModel) {
        if (matchModel == null) {
            throw new IllegalArgumentException("MatchModel é nulo.");
        }

        var totalVolumeEntrada = calcularTotalVolume(matchModel.getNominacaoEntradaList());
        var totalVolumeSaida = calcularTotalVolume(matchModel.getNominacaoSaidaList());

        if (totalVolumeEntrada == 0 || totalVolumeSaida == 0) {
            return SEM_MARGEM;
        }

        return Math.min(totalVolumeEntrada, totalVolumeSaida) / Math.max(totalVolumeEntrada, totalVolumeSaida);
    }

    private String gerarFluxo(MatchModel matchModel) {
        if (matchModel == null) {
            throw new IllegalArgumentException("MatchModel é nulo.");
        }

        var totalVolumeEntrada = calcularTotalVolume(matchModel.getNominacaoEntradaList());
        var totalVolumeSaida = calcularTotalVolume(matchModel.getNominacaoSaidaList());

        if (totalVolumeEntrada > totalVolumeSaida) {
            return "Entrada";
        } else if (totalVolumeSaida > totalVolumeEntrada) {
            return "Saída";
        } else {
            return SEM_FLUXO;
        }
    }

    private double calcularTotalVolume(List<? extends NominacaoModel> nominacaoList) {
        var totalVolume = 0.0;
        if (nominacaoList != null) {
            for (NominacaoModel nominacao : nominacaoList) {
                totalVolume += nominacao.getVolume();
            }
        }
        return totalVolume;
    }

    public boolean validar(MatchModel matchModel) {
        if (matchModel == null) {
            return false;
        }
        List<NominacaoModel> entradaList = matchModel.getNominacaoEntradaList();
        List<NominacaoModel> saidaList = matchModel.getNominacaoSaidaList();

        return entradaList != null && !entradaList.isEmpty() && saidaList != null && !saidaList.isEmpty();
    }
}
