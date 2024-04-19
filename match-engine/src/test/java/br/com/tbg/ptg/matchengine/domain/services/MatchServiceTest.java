package br.com.tbg.ptg.matchengine.domain.services;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.tbg.ptg.matchengine.application.events.NominacaoEntradaAdicionadaEvent;
import br.com.tbg.ptg.matchengine.application.events.NominacaoSaidaAdicionadaEvent;
import br.com.tbg.ptg.matchengine.application.services.ApplicationService;
import br.com.tbg.ptg.matchengine.application.services.RegraMenorDoParService;
import br.com.tbg.ptg.matchengine.domain.models.MatchModel;
import br.com.tbg.ptg.matchengine.domain.repository.IMatchRepository;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {

    @Mock
    private RegraMenorDoParService regraMenorDoParService;

    @Mock
    private IMatchRepository matchRepository;

    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private MatchService matchService;

    @Test
    public void testEventoNominacaoEntradaRecebito() {
        NominacaoEntradaAdicionadaEvent event = new NominacaoEntradaAdicionadaEvent();
        MatchModel matchModel = new MatchModel(); 
        List<MatchModel> listaMatchsDeHoje = new ArrayList<>();
        listaMatchsDeHoje.add(matchModel);

        when(applicationService.verificaSeExisteContraparteEvendoRecebido(anyList(), eq(event))).thenReturn(matchModel);
        when(regraMenorDoParService.aplicar(matchModel)).thenReturn(matchModel);

        matchService.eventoNominacaoEntradaRecebito(event);

        verify(regraMenorDoParService).aplicar(matchModel);
        verify(matchRepository).save(any());
    }

    @Test
    public void testEventoNominacaoSaidaRecebito() {
        // Arrange
        NominacaoSaidaAdicionadaEvent event = new NominacaoSaidaAdicionadaEvent();
        MatchModel matchModel = new MatchModel(); // Criando um MatchModel válido
        List<MatchModel> listaMatchsDeHoje = new ArrayList<>();
        listaMatchsDeHoje.add(matchModel);

        // Configurando o comportamento dos mocks
        when(applicationService.verificaSeExisteContraparteEvendoRecebido(anyList(), eq(event))).thenReturn(matchModel);
        when(regraMenorDoParService.aplicar(matchModel)).thenReturn(matchModel);

        // Act
        matchService.eventoNominacaoSaidaRecebito(event);

        // Assert
        verify(regraMenorDoParService).aplicar(matchModel);
        verify(matchRepository).save(any());
    }
}
