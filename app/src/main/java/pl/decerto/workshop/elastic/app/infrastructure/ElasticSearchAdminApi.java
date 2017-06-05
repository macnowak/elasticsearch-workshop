package pl.decerto.workshop.elastic.app.infrastructure;

public interface ElasticSearchAdminApi {

	void createIndex(String name, String indexConfigSource);

}
