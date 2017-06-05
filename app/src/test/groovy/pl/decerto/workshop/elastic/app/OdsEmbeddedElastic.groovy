package pl.decerto.workshop.elastic.app

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import pl.allegro.tech.embeddedelasticsearch.EmbeddedElastic
import pl.allegro.tech.embeddedelasticsearch.PopularProperties

import java.nio.file.Paths
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit

class OdsEmbeddedElastic {

	private static final Logger LOG = LoggerFactory.getLogger(OdsEmbeddedElastic.class.getName())

	static final ELASTICSEARCH_VERSION = "2.4.4"
	static final CLUSTER_NAME = "testCluster"
	static final ELASTICSEARCH_NAME = "elasticsearch"
	static final ICU_PLUGIN_NAME = "analysis-icu"
	static final ICU_PLUGIN_FILE_NAME = ICU_PLUGIN_NAME + '-' + ELASTICSEARCH_VERSION + '.zip'

	static final HOST = "localhost"
	static final TCP_PORT = ThreadLocalRandom.current().nextInt(9001, 9999)
	private final EmbeddedElastic embeddedElastic
	private static pluginPath

	OdsEmbeddedElastic() {
		this.embeddedElastic = getEmbeddedElasticBuilder()
				.build()
				.start()
	}

	OdsEmbeddedElastic(String pluginPath) {
		this.pluginPath = pluginPath
		this.embeddedElastic = getEmbeddedElasticBuilder()
				.build()
				.start()
	}

	private static EmbeddedElastic.Builder getEmbeddedElasticBuilder() {
		def plugin = getIcuPlugin()
		LOG.info(" Instaling plugin :" + plugin)

		EmbeddedElastic.Builder builder = EmbeddedElastic.builder()
				.withSetting(PopularProperties.CLUSTER_NAME, CLUSTER_NAME)
				.withSetting(PopularProperties.TRANSPORT_TCP_PORT, TCP_PORT)
				.withPlugin(plugin)
				.withStartTimeout(1, TimeUnit.MINUTES)

		getElastic().ifPresent({ str -> builder.withDownloadUrl(str) })
		return builder
	}

	private static Optional<URL> getElastic() {
		def esFileName = ELASTICSEARCH_NAME + '-' + ELASTICSEARCH_VERSION
		Optional<URL> path = Optional.ofNullable(OdsEmbeddedElastic.class.getClassLoader().getResource(esFileName + '.zip'))
		path.ifPresent({ p -> LOG.info(esFileName + " found in path: " + p) })
		return path
	}

	private static String getIcuPlugin() {
		if (pluginPath) {
			return Paths.get((pluginPath + File.separator + ICU_PLUGIN_FILE_NAME)).toUri().toString()
		}

		return Optional.ofNullable(OdsEmbeddedElastic.class.getClassLoader().getResource(ICU_PLUGIN_FILE_NAME))
				.map({ r -> r.toURI() })
				.orElseGet({ ICU_PLUGIN_NAME })
	}
}
