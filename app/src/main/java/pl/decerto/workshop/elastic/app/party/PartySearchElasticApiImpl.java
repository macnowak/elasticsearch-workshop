package pl.decerto.workshop.elastic.app.party;

import static pl.decerto.workshop.elastic.app.party.Party.PARTY;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

@Slf4j
class PartySearchElasticApiImpl implements PartySearchElasticApi {

	private final Client client;

	private final ObjectMapper mapper;

	PartySearchElasticApiImpl(Client client, ObjectMapper mapper) {
		this.client = client;
		this.mapper = mapper;
	}

	@Override
	public List<Party> search(SearchCriteria criteria) {
		return doSearch(criteria)
			.map(SearchHit::getSourceAsString)
			.map(this::toObject)
			.collect(Collectors.toList());

	}

	private Stream<SearchHit> doSearch(SearchCriteria criteria) {
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(PARTY)
			.setQuery(QuerySupplier.getQuery(criteria));

		if (criteria.getPager() != null) {
			searchRequestBuilder
				.setFrom(criteria.getPager().getPageNumber())
				.setSize(criteria.getPager().getPageSize());
		}

		if (criteria.getSorter() != null) {
			searchRequestBuilder
				.addSort(new FieldSortBuilder(criteria.getSorter().getField()).order(criteria.getSorter().isAsc() ? SortOrder.ASC : SortOrder.DESC));
		}

		log.info("Query :" + searchRequestBuilder.internalBuilder());

		return Arrays.stream(searchRequestBuilder.get().getHits().getHits());
	}

	private Party toObject(String s) {
		try {
			return mapper.readValue(s, Party.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
