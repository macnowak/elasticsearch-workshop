package pl.decerto.workshop.elastic.app.party;

import java.util.List;

public interface PartySearchElasticApi {

	List<Party> search(SearchCriteria criteria);
}
