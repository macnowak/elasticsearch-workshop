package pl.decerto.workshop.elastic.app.infrastructure

import pl.decerto.workshop.elastic.app.EmbeddedElasticTest

class ElasticConfigTest extends EmbeddedElasticTest {

	def "should connect to elastic and get health message"() {
		expect:
		new ElasticConfig()
				.client(properties)
				.admin()
				.cluster()
				.prepareHealth("_all")
				.get()
				.getClusterName() == properties.clusterName
	}
}
