package pl.decerto.workshop.elastic.app

import pl.decerto.workshop.elastic.app.infrastructure.ElasticSearchProperties
import spock.lang.Specification

class ExternalElasticTest extends Specification {

	public static final String ELASTIC_HOST = "localhost"
	public static final int ELASTIC_API_PORT = 9300
	public static final String CLUSTER_NAME = "docker-elastic-workshop"
	ElasticSearchProperties properties

	void setup() {
		properties = new ElasticSearchProperties(host: ELASTIC_HOST, port: ELASTIC_API_PORT, clusterName: CLUSTER_NAME)
	}

}
