package br.com.tbg.ptg.nominacaov2.domain.services;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tbg.ptg.nominacaov2.application.dto.NominacaoDTO;
import br.com.tbg.ptg.nominacaov2.application.services.ApplicationService;
import br.com.tbg.ptg.nominacaov2.domain.models.NominacaoModel;
import br.com.tbg.ptg.nominacaov2.domain.repository.NominacaoRepository;
import br.com.tbg.ptg.nominacaov2.infra.saida.RabbitiMqEnventOut;

@Service
public class NominacaoService {

    @Autowired
    private RabbitiMqEnventOut rabbitMQService;

    @Autowired
    private NominacaoRepository nominacaoRepository;

    @Autowired
    private ApplicationService applicationService;

    public void salvar(NominacaoDTO nominacaoDTO) {
    	validarDataRN1(LocalDate.parse(nominacaoDTO.getDiaOperacionalInicial()));
        if (!nominacaoPorPeriodoRN3(nominacaoDTO)) {
            NominacaoModel nominacaoModel = applicationService.deDtoParaNominacaoModel(nominacaoDTO);
            nominacaoRepository.save(nominacaoModel);
            rabbitMQService.verificaFluxoCriaEvento(nominacaoDTO);
            rabbitMQService.notificarAclNominacao(nominacaoDTO);
        }
    }

    public boolean nominacaoPorPeriodoRN3(NominacaoDTO nominacaoDTO) {
        if (nominacaoDTO.getDiaOperacionalFinal() != null && !nominacaoDTO.getDiaOperacionalFinal().isBlank()) {
            ArrayList<NominacaoDTO> listaNominacaoPorPeriodo = new ArrayList<>();
            LocalDate dataFinal = LocalDate.parse(nominacaoDTO.getDiaOperacionalFinal());
            for (LocalDate dataInicial = LocalDate.parse(nominacaoDTO.getDiaOperacionalInicial());
                 !dataInicial.isAfter(dataFinal);
                 dataInicial = dataInicial.plusDays(1)) {
                NominacaoDTO novaNominacao = NominacaoDTO.builder()
                        .carregador(nominacaoDTO.getCarregador())
                        .carregadorContraparte(nominacaoDTO.getCarregadorContraparte())
                        .ciclo(nominacaoDTO.getCiclo())
                        .contrato(nominacaoDTO.getContrato())
                        .desequilibrio_DQS(nominacaoDTO.getDesequilibrio_DQS())
                        .diaOperacionalInicial(dataInicial.toString())
                        .fluxo(nominacaoDTO.getFluxo())
                        .instalacao(nominacaoDTO.getInstalacao())
                        .quantidadeDiáriaNominada_QDN(nominacaoDTO.getQuantidadeDiáriaNominada_QDN())
                        .build();
                listaNominacaoPorPeriodo.add(novaNominacao);
            }
            listaNominacaoPorPeriodo.forEach(nominacao -> {
                rabbitMQService.verificaFluxoCriaEvento(nominacao);
                rabbitMQService.notificarAclNominacao(nominacaoDTO);
                nominacaoRepository.save(applicationService.deDtoParaNominacaoModel(nominacao));
            });
            return true;
        }
        return false;
    }
    
    public void validarDataRN1(LocalDate data) {
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataLimite = dataAtual.plusDays(180);
        
        if (data.isAfter(dataLimite)) {
            throw new IllegalArgumentException("Não é permitido incluir uma nomeação com data maior que 180 dias da data atual.");
        }
    }
}
