package br.com.tbg.ptg.matchengine.application.services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.tbg.ptg.matchengine.application.events.NominacaoEntradaAdicionadaEvent;
import br.com.tbg.ptg.matchengine.application.events.NominacaoSaidaAdicionadaEvent;
import br.com.tbg.ptg.matchengine.domain.models.MatchModel;
import br.com.tbg.ptg.matchengine.domain.models.MatchModel.NominacaoModel;

public class ApplicationServiceTest {

    private ApplicationService applicationService;
    private NominacaoEntradaAdicionadaEvent eventNominacaoEntrada;
    private NominacaoSaidaAdicionadaEvent eventNominacaoSaida;

    @BeforeEach
    public void setUp() {
        applicationService = new ApplicationService();
        eventNominacaoEntrada = mock(NominacaoEntradaAdicionadaEvent.class);
        eventNominacaoSaida = mock(NominacaoSaidaAdicionadaEvent.class);
    }

    @Test
    public void testVerificaSeExisteContraparteQuandoNominacaoEntradaAdicionadaEvent() {
        when(eventNominacaoEntrada.getNomeCarregador()).thenReturn("Petrobras");
        when(eventNominacaoEntrada.getContraparte()).thenReturn("MSgas");
        when(eventNominacaoEntrada.getDataOperaciona()).thenReturn(LocalDate.now().toString());


        List<MatchModel> listaMatchsDBMOCK = new ArrayList<>();
        MatchModel matchModel = new MatchModel();
        matchModel.setDataOperacional(LocalDate.now());
        NominacaoModel nominacaoSaida = new NominacaoModel();
        nominacaoSaida.setNomeCarregador("MSgas");
        nominacaoSaida.setContraparte("Petrobras");
        matchModel.getNominacaoSaidaList().add(nominacaoSaida);
        listaMatchsDBMOCK.add(matchModel);

        MatchModel result = applicationService.verificaSeExisteContraparteEvendoRecebido(listaMatchsDBMOCK, eventNominacaoEntrada);

        assertNotNull(result);
        assertEquals(1, result.getNominacaoEntradaList().size());
        assertEquals("Petrobras", result.getNominacaoEntradaList().get(0).getNomeCarregador());
        assertEquals("MSgas", result.getNominacaoEntradaList().get(0).getContraparte());
    }
    
       
    @Test
    public void testDoisNominacaoSaidaComMesmosValores() {
        when(eventNominacaoEntrada.getNomeCarregador()).thenReturn("Petrobras");
        when(eventNominacaoEntrada.getContraparte()).thenReturn("MSgas");
        when(eventNominacaoEntrada.getDataOperaciona()).thenReturn(LocalDate.now().toString());


        List<MatchModel> listaMatchsDBMOCK = new ArrayList<>();
        MatchModel matchModel = new MatchModel();
        matchModel.setDataOperacional(LocalDate.now());
        NominacaoModel nominacaoSaida1 = new NominacaoModel();
        nominacaoSaida1.setNomeCarregador("MSgas");
        nominacaoSaida1.setContraparte("Petrobras");
        NominacaoModel nominacaoSaida2 = new NominacaoModel();
        nominacaoSaida2.setNomeCarregador("MSgas");
        nominacaoSaida2.setContraparte("Petrobras");

        matchModel.getNominacaoSaidaList().add(nominacaoSaida1);
        matchModel.getNominacaoSaidaList().add(nominacaoSaida2);

        listaMatchsDBMOCK.add(matchModel);

        MatchModel result = applicationService.verificaSeExisteContraparteEvendoRecebido(listaMatchsDBMOCK, eventNominacaoEntrada);

        assertNotNull(result);
        assertEquals(2, result.getNominacaoSaidaList().size());
    }
    
    @Test
    public void testDoisNominacaoSaidaComMesmosValoresComEventoNaoEncontrado() {
        when(eventNominacaoEntrada.getNomeCarregador()).thenReturn("MSgas");
        when(eventNominacaoEntrada.getContraparte()).thenReturn("Petrobras");
        when(eventNominacaoEntrada.getDataOperaciona()).thenReturn(LocalDate.now().toString());

        List<MatchModel> listaMatchsDBMOCK = new ArrayList<>();
        MatchModel matchModel = new MatchModel();
        matchModel.setDataOperacional(LocalDate.now());
        NominacaoModel nominacaoSaida1 = new NominacaoModel();
        nominacaoSaida1.setNomeCarregador("MSgas");
        nominacaoSaida1.setContraparte("Petrobras");
        NominacaoModel nominacaoSaida2 = new NominacaoModel();
        nominacaoSaida2.setNomeCarregador("MSgas");
        nominacaoSaida2.setContraparte("Petrobras");

        matchModel.getNominacaoSaidaList().add(nominacaoSaida1);
        matchModel.getNominacaoSaidaList().add(nominacaoSaida2);

        listaMatchsDBMOCK.add(matchModel);
        
        MatchModel result = applicationService.verificaSeExisteContraparteEvendoRecebido(listaMatchsDBMOCK, eventNominacaoEntrada);

        assertNotNull(result);
        assertNull(result.getNominacaoSaidaList());
        assertEquals("MSgas", result.getNominacaoEntradaList().get(0).getNomeCarregador());
        assertEquals("Petrobras", result.getNominacaoEntradaList().get(0).getContraparte());
    }

    //Saida
    
    
    @Test
    public void testVerificaSeExisteContraparteQuandoNominacaoSaidaAdicionadaEvent() {
        when(eventNominacaoSaida.getNomeCarregador()).thenReturn("Petrobras");
        when(eventNominacaoSaida.getContraparte()).thenReturn("MSgas");
        when(eventNominacaoSaida.getDataOperaciona()).thenReturn(LocalDate.now().toString());

        List<MatchModel> listaMatchsDBMOCK = new ArrayList<>();
        MatchModel matchModel = new MatchModel();
        matchModel.setDataOperacional(LocalDate.now());
        NominacaoModel nominacaoEntrda = new NominacaoModel();
        nominacaoEntrda.setNomeCarregador("MSgas");
        nominacaoEntrda.setContraparte("Petrobras");
        matchModel.getNominacaoEntradaList().add(nominacaoEntrda);
        listaMatchsDBMOCK.add(matchModel);

        MatchModel result = applicationService.verificaSeExisteContraparteEvendoRecebido(listaMatchsDBMOCK, eventNominacaoSaida);

        assertNotNull(result);
        assertEquals(1, result.getNominacaoEntradaList().size());
        assertEquals("MSgas", result.getNominacaoEntradaList().get(0).getNomeCarregador());
        assertEquals("Petrobras", result.getNominacaoEntradaList().get(0).getContraparte());
    }
    
    @Test
    public void testVerificaSeExisteContraparteQuandoNominacaoSaidaAdicionadaEventTest() {
        when(eventNominacaoSaida.getNomeCarregador()).thenReturn("Petrobras");
        when(eventNominacaoSaida.getContraparte()).thenReturn("MSgas");
        when(eventNominacaoSaida.getDataOperaciona()).thenReturn(LocalDate.now().toString());
        List<MatchModel> listaMatchsDBMOCK = new ArrayList<>();
        MatchModel matchModel = new MatchModel();
        matchModel.setDataOperacional(LocalDate.now());
        NominacaoModel nominacaoEntrda = new NominacaoModel();
        nominacaoEntrda.setNomeCarregador("Petrobras");
        nominacaoEntrda.setContraparte("MSgas");
        matchModel.getNominacaoEntradaList().add(nominacaoEntrda);
        listaMatchsDBMOCK.add(matchModel);

        MatchModel result = applicationService.verificaSeExisteContraparteEvendoRecebido(listaMatchsDBMOCK, eventNominacaoSaida);

        assertNotNull(result);
        assertNull(result.getNominacaoEntradaList());
        assertEquals(1, result.getNominacaoSaidaList().size());
        assertEquals("Petrobras", result.getNominacaoSaidaList().get(0).getNomeCarregador());
        assertEquals("MSgas", result.getNominacaoSaidaList().get(0).getContraparte());
    }
    
    //DSQ Entrada
    
    @Test
    public void testVerificaSeExisteContraparteQuandoNominacaoSaidaAdicionadaEventDSQEntradaTest() {
        when(eventNominacaoEntrada.getNomeCarregador()).thenReturn("Petrobras");
        when(eventNominacaoEntrada.getContraparte()).thenReturn("Petrobras");
        when(eventNominacaoEntrada.getDataOperaciona()).thenReturn(LocalDate.now().toString());

        List<MatchModel> listaMatchsDBMOCK = new ArrayList<>();
        MatchModel matchModel = new MatchModel();
        matchModel.setDataOperacional(LocalDate.now());
        NominacaoModel nominacaoSaida = new NominacaoModel();
        nominacaoSaida.setNomeCarregador("Petrobras");
        nominacaoSaida.setContraparte("Petrobras");
        matchModel.getNominacaoEntradaList().add(nominacaoSaida);
        listaMatchsDBMOCK.add(matchModel);

        MatchModel result = applicationService.verificaSeExisteContraparteEvendoRecebido(listaMatchsDBMOCK, eventNominacaoEntrada);

        assertNotNull(result);
        assertEquals(2, result.getNominacaoEntradaList().size());
        assertEquals("Petrobras", result.getNominacaoEntradaList().get(0).getNomeCarregador());
        assertEquals("Petrobras", result.getNominacaoEntradaList().get(0).getContraparte());
    }

 //DSQ Saida
    
    @Test
    public void testVerificaSeExisteContraparteQuandoNominacaoSaidaAdicionadaEventDSQSaidaTest() {
        when(eventNominacaoSaida.getNomeCarregador()).thenReturn("Petrobras");
        when(eventNominacaoSaida.getContraparte()).thenReturn("Petrobras");
        when(eventNominacaoSaida.getDataOperaciona()).thenReturn(LocalDate.now().toString());
        List<MatchModel> listaMatchsDBMOCK = new ArrayList<>();
        MatchModel matchModel = new MatchModel();
        matchModel.setDataOperacional(LocalDate.now());
        NominacaoModel nominacaoEntrada = new NominacaoModel();
        nominacaoEntrada.setNomeCarregador("Petrobras");
        nominacaoEntrada.setContraparte("Petrobras");
        matchModel.getNominacaoSaidaList().add(nominacaoEntrada);
        listaMatchsDBMOCK.add(matchModel);

        MatchModel result = applicationService.verificaSeExisteContraparteEvendoRecebido(listaMatchsDBMOCK, eventNominacaoSaida);

        assertNotNull(result);
        assertTrue(result.getNominacaoEntradaList().isEmpty());
        assertEquals(2, result.getNominacaoSaidaList().size());
        assertEquals("Petrobras", result.getNominacaoSaidaList().get(0).getNomeCarregador());
        assertEquals("Petrobras", result.getNominacaoSaidaList().get(0).getContraparte());
    }

   
}
