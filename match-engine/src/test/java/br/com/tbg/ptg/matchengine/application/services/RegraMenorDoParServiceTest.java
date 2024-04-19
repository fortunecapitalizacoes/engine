package br.com.tbg.ptg.matchengine.application.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import br.com.tbg.ptg.matchengine.domain.models.MatchModel;
import br.com.tbg.ptg.matchengine.domain.models.MatchModel.NominacaoModel;

public class RegraMenorDoParServiceTest {
		
	@Test
	public void testAplicar_ComMatchValido() {
		
		 RegraMenorDoParService service = new RegraMenorDoParService();

		NominacaoModel nominacaoSaida1 = NominacaoModel.builder().contraparte(null).id(null).nomeCarregador(null)
				.volume(100).build();

		NominacaoModel nominacaoSaida2 = NominacaoModel.builder().contraparte(null).id(null).nomeCarregador(null)
				.volume(100).build();

		NominacaoModel nominacaoEntrada1 = NominacaoModel.builder().contraparte(null).id(null).nomeCarregador(null)
				.volume(50).build();

		NominacaoModel nominacaoEntrada2 = NominacaoModel.builder().contraparte(null).id(null).nomeCarregador(null)
				.volume(50).build();

		List<NominacaoModel> listaNominacaoSaida = new ArrayList<>();
		listaNominacaoSaida.add(nominacaoSaida2);
		listaNominacaoSaida.add(nominacaoSaida1);

		List<NominacaoModel> listaNominacaoEntrada = new ArrayList<>();
							 listaNominacaoEntrada.add(nominacaoEntrada2);
							 listaNominacaoEntrada.add(nominacaoEntrada1);

		MatchModel match = MatchModel.builder().id("1").dataOperacional(LocalDate.now())
				.nominacaoEntradaList(listaNominacaoEntrada).nominacaoSaidaList(listaNominacaoSaida).build();

		 MatchModel result = service.aplicar(match);
		 
		 assertNotNull(result);
	     assertEquals(50, result.getNominacaoSaidaList().get(0).getVolume());
	     assertEquals(50, result.getNominacaoSaidaList().get(1).getVolume());
	     
	}
	
	@Test
	public void testAplicar_ComMatchValidoMaiorVolumeDiferentes() {
		
		 RegraMenorDoParService service = new RegraMenorDoParService();

		NominacaoModel nominacaoSaida1 = NominacaoModel.builder().contraparte(null).id(null).nomeCarregador(null)
				.volume(75).build();

		NominacaoModel nominacaoSaida2 = NominacaoModel.builder().contraparte(null).id(null).nomeCarregador(null)
				.volume(89).build();

		NominacaoModel nominacaoEntrada1 = NominacaoModel.builder().contraparte(null).id(null).nomeCarregador(null)
				.volume(50).build();

		NominacaoModel nominacaoEntrada2 = NominacaoModel.builder().contraparte(null).id(null).nomeCarregador(null)
				.volume(50).build();

		List<NominacaoModel> listaNominacaoSaida = new ArrayList<>();
		listaNominacaoSaida.add(nominacaoSaida2);
		listaNominacaoSaida.add(nominacaoSaida1);

		List<NominacaoModel> listaNominacaoEntrada = new ArrayList<>();
							 listaNominacaoEntrada.add(nominacaoEntrada2);
							 listaNominacaoEntrada.add(nominacaoEntrada1);

		MatchModel match = MatchModel.builder().id("1").dataOperacional(LocalDate.now())
				.nominacaoEntradaList(listaNominacaoEntrada).nominacaoSaidaList(listaNominacaoSaida).build();

		 MatchModel result = service.aplicar(match);
		 
		 assertNotNull(result);
	     assertEquals(54.26829268292683, result.getNominacaoSaidaList().get(0).getVolume());
	     assertEquals(45.73170731707317, result.getNominacaoSaidaList().get(1).getVolume());
	     assertEquals(100, result.getNominacaoSaidaList().get(0).getVolume() +  result.getNominacaoSaidaList().get(1).getVolume());
	}

	@Test
	public void testAplicar_ComMatchValidoMaiorVolumeDiferentesEntrada() {
		
		 RegraMenorDoParService service = new RegraMenorDoParService();

		NominacaoModel nominacaoSaida1 = NominacaoModel.builder().contraparte(null).id(null).nomeCarregador(null)
				.volume(50).build();

		NominacaoModel nominacaoSaida2 = NominacaoModel.builder().contraparte(null).id(null).nomeCarregador(null)
				.volume(50).build();

		NominacaoModel nominacaoEntrada1 = NominacaoModel.builder().contraparte(null).id(null).nomeCarregador(null)
				.volume(55).build();

		NominacaoModel nominacaoEntrada2 = NominacaoModel.builder().contraparte(null).id(null).nomeCarregador(null)
				.volume(89).build();

		List<NominacaoModel> listaNominacaoSaida = new ArrayList<>();
		listaNominacaoSaida.add(nominacaoSaida2);
		listaNominacaoSaida.add(nominacaoSaida1);

		List<NominacaoModel> listaNominacaoEntrada = new ArrayList<>();
							 listaNominacaoEntrada.add(nominacaoEntrada2);
							 listaNominacaoEntrada.add(nominacaoEntrada1);

		MatchModel match = MatchModel.builder().id("1").dataOperacional(LocalDate.now())
				.nominacaoEntradaList(listaNominacaoEntrada).nominacaoSaidaList(listaNominacaoSaida).build();

		 MatchModel result = service.aplicar(match);
		 
		 assertNotNull(result);
	     assertEquals(61.80555555555555, result.getNominacaoEntradaList().get(0).getVolume());
	     assertEquals(38.19444444444444, result.getNominacaoEntradaList().get(1).getVolume());
	     assertEquals(100, result.getNominacaoSaidaList().get(0).getVolume() +  result.getNominacaoSaidaList().get(1).getVolume());
	     
	}

}
