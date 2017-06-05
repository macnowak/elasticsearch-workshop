package pl.decerto.workshop.elastic.app.infrastructure

import org.apache.commons.io.IOUtils
import pl.decerto.workshop.elastic.app.EmbeddedElasticTest

import java.nio.charset.Charset

class ElasticSearchAdminApiImplTest extends EmbeddedElasticTest {

	String indexConfigFile = "/test_index-v0.json"
	ElasticSearchAdminApiImpl api

	void setup() {
		api = new ElasticSearchAdminApiImpl(client)
	}

	def "should create index in elastic "() {
		given:
		String indexName = "test_index-v0"

		when:
		api.createIndex(indexName, IOUtils.toString(this.getClass().getResourceAsStream(indexConfigFile), Charset.defaultCharset()))

		then:
		indexExists(indexName)
	}

	def "creating index should be idempotent"() {
		given:
		String indexName = "test_index-v1"

		when:
		api.createIndex(indexName, IOUtils.toString(this.getClass().getResourceAsStream(indexConfigFile), Charset.defaultCharset()))

		then:
		indexExists(indexName)

		when:
		api.createIndex(indexName, IOUtils.toString(this.getClass().getResourceAsStream(indexConfigFile), Charset.defaultCharset()))

		then:
		noExceptionThrown()
	}

}
