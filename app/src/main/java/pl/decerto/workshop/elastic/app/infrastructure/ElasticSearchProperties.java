package pl.decerto.workshop.elastic.app.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Klasa przechowująca parametry połączenia z ES
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchProperties {

	private String host;

	private int port;

	private String httpUrl;

	private boolean sniffingEnabled;

	private String clusterName;

}
