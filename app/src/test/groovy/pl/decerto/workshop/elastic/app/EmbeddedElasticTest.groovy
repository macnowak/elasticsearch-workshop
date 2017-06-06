package pl.decerto.workshop.elastic.app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.elasticsearch.client.Client
import pl.decerto.workshop.elastic.app.infrastructure.ElasticConfig
import pl.decerto.workshop.elastic.app.infrastructure.ElasticSearchAdminApi
import pl.decerto.workshop.elastic.app.infrastructure.ElasticSearchAdminApiImpl
import pl.decerto.workshop.elastic.app.infrastructure.ElasticSearchProperties
import spock.lang.Specification

class EmbeddedElasticTest extends Specification {

	public static final OdsEmbeddedElastic elastic = new OdsEmbeddedElastic()

	static ElasticSearchProperties properties

	static Client client

	static ObjectMapper mapper

	static ElasticSearchAdminApi adminApi

	void setupSpec() {
		properties = new ElasticSearchProperties(host: OdsEmbeddedElastic.HOST, port: OdsEmbeddedElastic.TCP_PORT, clusterName: OdsEmbeddedElastic.CLUSTER_NAME)
		client = new ElasticConfig().client(properties)
		mapper = new ObjectMapper().registerModule(new JavaTimeModule())
		adminApi = new ElasticSearchAdminApiImpl(client)

	}

	boolean indexExists(String name) {
		client.admin().indices().prepareExists(name).get().exists
	}

}
