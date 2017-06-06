package pl.decerto.workshop.elastic.app.party;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static pl.decerto.workshop.elastic.app.party.Address.Fields.CITY_AUTOCOMPLETE;
import static pl.decerto.workshop.elastic.app.party.Address.Fields.STREET_AUTOCOMPLETE;
import static pl.decerto.workshop.elastic.app.party.Party.Fields.ADDRESSES;
import static pl.decerto.workshop.elastic.app.party.Party.Fields.FIRST_NAME;
import static pl.decerto.workshop.elastic.app.party.Party.Fields.NAME;
import static pl.decerto.workshop.elastic.app.party.Party.Fields.NAME_AUTOCOMPLETE;
import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

class QuerySupplier {

	private QuerySupplier() {
	}

	static QueryBuilder getQuery(SearchCriteria criteria) {
		return new Builder()
			.match(FIRST_NAME, criteria.getFirstName())
			.match(NAME, criteria.getExactName())
			.match(NAME_AUTOCOMPLETE, criteria.getName())
			.matchNested(ADDRESSES, CITY_AUTOCOMPLETE, criteria.getCity())
			.matchNested(ADDRESSES, STREET_AUTOCOMPLETE, criteria.getStreet())
			.build();

	}

	private static class Builder {

		private final List<QueryBuilder> queries = new ArrayList<>();

		Builder match(String field, String value) {
			if (value != null) {
				queries.add(matchQuery(field, value));
			}
			return this;
		}

		Builder matchNested(String root, String field, String value) {
			if (value != null) {
				queries.add(QueryBuilders.nestedQuery(root, matchQuery(root + "." + field, value)));
			}
			return this;
		}

		QueryBuilder build() {
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			queries.forEach(boolQueryBuilder::must);
			return boolQueryBuilder;
		}

	}
}