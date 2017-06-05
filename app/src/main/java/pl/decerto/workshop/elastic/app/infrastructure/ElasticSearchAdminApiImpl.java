package pl.decerto.workshop.elastic.app.infrastructure;

import org.elasticsearch.client.Client;

class ElasticSearchAdminApiImpl implements ElasticSearchAdminApi {

	private final Client client;

	ElasticSearchAdminApiImpl(Client client) {
		this.client = client;
	}

	@Override
	public void createIndex(String name, String indexConfigSource) {
		if (indexExists(name)) {
			return;
		}
		client.admin().indices().prepareCreate(name).setSource(indexConfigSource).get();
	}

	private boolean indexExists(String name) {
		return client.admin().indices().prepareExists(name).get().isExists();
	}
}
