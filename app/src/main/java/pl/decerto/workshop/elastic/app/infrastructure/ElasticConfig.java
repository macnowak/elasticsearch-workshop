package pl.decerto.workshop.elastic.app.infrastructure;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = ElasticSearchProperties.class)
class ElasticConfig {

	@Bean
	public Client client(ElasticSearchProperties elasticSearchProperties) throws UnknownHostException {
		Settings settings = Settings.builder()
			.put("client.transport.sniff", elasticSearchProperties.isSniffingEnabled())
			.put("cluster.name", elasticSearchProperties.getClusterName())
			.build();
		TransportClient transportClient = TransportClient.builder().settings(settings).build();
		transportClient
			.addTransportAddress(
				new InetSocketTransportAddress(new InetSocketAddress(elasticSearchProperties.getHost(), elasticSearchProperties.getPort()))
			);
		return transportClient;
	}

	@Bean
	public ElasticSearchAdminApi elasticSearchAdminApi(Client client) {
		return new ElasticSearchAdminApiImpl(client);
	}

}
