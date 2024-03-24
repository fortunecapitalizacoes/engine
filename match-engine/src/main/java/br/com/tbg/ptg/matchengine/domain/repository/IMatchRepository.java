package br.com.tbg.ptg.matchengine.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.tbg.ptg.matchengine.domain.models.MatchModel;

public interface IMatchRepository extends MongoRepository<MatchModel, String>{
	List<MatchModel> findByDataOperacional(LocalDate dataOperacional);
}
