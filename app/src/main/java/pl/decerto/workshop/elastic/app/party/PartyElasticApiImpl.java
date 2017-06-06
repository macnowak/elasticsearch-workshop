package pl.decerto.workshop.elastic.app.party;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Client;

class PartyElasticApiImpl implements PartyElasticApi {


	private final Client client;

	private final ObjectMapper mapper;

	PartyElasticApiImpl(Client client, ObjectMapper mapper) {
		this.client = client;
		this.mapper = mapper;
	}

	public Iterable<Party> index(Collection<Party> documents) {
		BulkRequestBuilder builder = client.prepareBulk();

		documents.forEach(d -> builder.add(new IndexRequest(d.getIndex(), d.getType(), d.getId()).source(toJson(d))));

		BulkResponse response = builder.get();

		if (response.hasFailures()) {
			throw new RuntimeException("Exception during bulk indexing");
		}

		return documents;
	}

	private String toJson(Party d) {
		try {
			return mapper.writeValueAsString(d);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
