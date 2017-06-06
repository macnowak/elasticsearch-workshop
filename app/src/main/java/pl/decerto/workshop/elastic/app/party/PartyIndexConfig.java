package pl.decerto.workshop.elastic.app.party;

import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import pl.decerto.workshop.elastic.app.infrastructure.ElasticSearchAdminApi;
import pl.decerto.workshop.elastic.app.util.ResourceUtils;

@Configuration
class PartyIndexConfig {

	public static final String PARTY_INDEX_CONFIG_FILE_NAME = "party-v0.json";

	public static final String PARTY_INDEX_NAME = "party-v0";

	@Autowired
	private ElasticSearchAdminApi adminApi;

	@PostConstruct
	public void initPartyIndex() throws IOException {
		adminApi.createIndex(PARTY_INDEX_NAME, ResourceUtils.getResource("/" + PARTY_INDEX_CONFIG_FILE_NAME));
	}

}
