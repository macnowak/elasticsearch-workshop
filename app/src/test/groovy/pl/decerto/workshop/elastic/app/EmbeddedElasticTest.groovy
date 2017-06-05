package pl.decerto.workshop.elastic.app

import org.elasticsearch.client.Client
import pl.decerto.workshop.elastic.app.infrastructure.ElasticConfig
import pl.decerto.workshop.elastic.app.infrastructure.ElasticSearchProperties
import spock.lang.Specification

class EmbeddedElasticTest extends Specification {

	public static final OdsEmbeddedElastic elastic = new OdsEmbeddedElastic()

	static ElasticSearchProperties properties

	static Client client

	void setupSpec() {
		properties = new ElasticSearchProperties(host: OdsEmbeddedElastic.HOST, port: OdsEmbeddedElastic.TCP_PORT, clusterName: OdsEmbeddedElastic.CLUSTER_NAME)
		client = new ElasticConfig().client(properties)
	}

	boolean indexExists(String name) {
		client.admin().indices().prepareExists(name).get().exists
	}

}
