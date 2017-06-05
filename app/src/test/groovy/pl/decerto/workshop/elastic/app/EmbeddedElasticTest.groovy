package pl.decerto.workshop.elastic.app

import pl.decerto.workshop.elastic.app.infrastructure.ElasticSearchProperties
import spock.lang.Specification

class EmbeddedElasticTest extends Specification {

	public static final OdsEmbeddedElastic elastic = new OdsEmbeddedElastic()

	ElasticSearchProperties properties

	void setup() {
		properties = new ElasticSearchProperties(host: OdsEmbeddedElastic.HOST, port: OdsEmbeddedElastic.TCP_PORT, clusterName: OdsEmbeddedElastic.CLUSTER_NAME)
	}

}
