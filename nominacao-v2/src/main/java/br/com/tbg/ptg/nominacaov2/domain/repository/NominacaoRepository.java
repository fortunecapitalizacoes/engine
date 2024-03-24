package br.com.tbg.ptg.nominacaov2.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import br.com.tbg.ptg.nominacaov2.domain.models.NominacaoModel;

@Repository
public interface NominacaoRepository extends MongoRepository<NominacaoModel, String> {
 
}
