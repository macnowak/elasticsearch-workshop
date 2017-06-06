package pl.decerto.workshop.elastic.app.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.Client;

@Slf4j
class ElasticSearchAdminApiImpl implements ElasticSearchAdminApi {

	private final Client client;

	ElasticSearchAdminApiImpl(Client client) {
		this.client = client;
	}

	@Override
	public void createIndex(String name, String indexConfigSource) {
		if (indexExists(name)) {
			log.info("Index {} already exists, skipping", name);
			return;
		}
		log.info("Creating index {} from with source {}", name, StringUtils.abbreviate(indexConfigSource, 100));
		client.admin().indices().prepareCreate(name).setSource(indexConfigSource).get();
	}

	private boolean indexExists(String name) {
		log.info("Checking existence of : {}", name);
		return client.admin().indices().prepareExists(name).get().isExists();
	}
}
