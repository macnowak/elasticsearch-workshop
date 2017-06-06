package pl.decerto.workshop.elastic.app.party;

import java.util.Collection;

public interface PartyElasticApi {

	Iterable<Party> index(Collection<Party> documents);
}
