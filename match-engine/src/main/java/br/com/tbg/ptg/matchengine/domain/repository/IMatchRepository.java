package br.com.tbg.ptg.matchengine.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.tbg.ptg.matchengine.domain.models.MatchModel;

public interface IMatchRepository extends MongoRepository<MatchModel, String> {
	@Query("{'dataOperacional' : ?0}")
	List<MatchModel> findByDataOperacional(LocalDate dataOperacional);

	@Query("{'dataOperacional': {$gte: ?0, $lte: ?1}}")
	List<MatchModel> findByDataOperacionalBetween(LocalDate dataOperacionalInicial, LocalDate dataOperacionalFinal);
}
