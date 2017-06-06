package pl.decerto.workshop.elastic.app.party

import pl.decerto.workshop.elastic.app.ExternalElasticTest
import spock.lang.Unroll

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

import static pl.decerto.workshop.elastic.app.party.Party.Fields.NAME
import static pl.decerto.workshop.elastic.app.party.SearchCriteria.Pager.of
import static pl.decerto.workshop.elastic.app.party.SearchCriteria.Sorter.asc
import static pl.decerto.workshop.elastic.app.party.SearchCriteria.Sorter.desc
import static pl.decerto.workshop.elastic.app.party.SearchCriteria.builder

class PartySearchElasticApiImplTest extends ExternalElasticTest {

	private PartySearchElasticApi api

	void setup() {
		new PartyIndexConfig(adminApi: adminApi).initPartyIndex()
		new PartyElasticApiImpl(client, mapper).index(getParties("/party_test_data.json"))
		client.admin().indices().prepareRefresh(Party.PARTY).get()
		api = new PartySearchElasticApiImpl()

	}

	def "should find party by firsName in party index"() {
		when:
		def parties = api.search(builder().firstName(firstName).build())

		then:
		parties.size() == matchingParties.size()
		parties.stream().allMatch({ it -> matchingParties.contains(it.partyId) })

		where:
		firstName || matchingParties
		"Paweł"   || [3L, 11L]
	}

	@Unroll
	def "should find party by exact name in elastic : #name"() {
		when:
		def parties = api.search(builder().exactName(name).build())

		then:
		parties.size() == matchingParties.size()
		parties.stream().allMatch({ it -> matchingParties.contains(it.partyId) })

		where:
		name             || matchingParties
		"Nowak"          || [4L]
		"Nowak-Kowalski" || [26L]
		"Kowalski"       || [18L, 19L]

	}

	@Unroll
	def "should find party by part of name in elastic : #name"() {
		when:
		def parties = api.search(builder().name(name).build())

		then:
		parties.size() == matchingParties.size()
		parties.stream().allMatch({ it -> matchingParties.contains(it.partyId) })

		where:
		name                 || matchingParties
		"Kowal"              || [18L, 19L, 26L]
		"Kowalski"           || [18L, 19L, 26L]
		"Kowalsk"            || [18L, 19L, 26L]
		"Nowak"              || [4L, 26L]
		"Zaleska-barczewska" || [15L]
		"Zaleska"            || [15L]
	}

	@Unroll()
	def "should find party by city in elastic : #cityName"() {
		when:
		def parties = api.search(builder().city(cityName).build())

		then:
		parties.size() == matchingParties.size()
		parties.stream().allMatch({ it -> matchingParties.contains(it.partyId) })

		where:
		cityName   || matchingParties
		"warszawa" || [1L, 2L, 13L, 17L, 19L]
		"arsz"     || [1L, 2L, 13L, 17L, 19L]
		"Solec"    || [7L, 12L, 14L]

	}

	@Unroll
	def "should find party by street name in elastic : #streetName"() {
		when:
		def parties = api.search(builder().street(streetName).build())

		then:
		parties.size() == matchingParties.size()
		parties.stream().allMatch({ it -> matchingParties.contains(it.partyId) })

		where:
		streetName             || matchingParties
		"Stanów zjednoczonych" || [7L, 18L, 19L, 20L]
		"Stanów"               || [7L, 18L, 19L, 20L]
		"ŚW. ANTONIEGO"        || [19L]
		"ANTONIEGO"            || [19L]
		"35-LECIA PRL"         || [8L]
	}

	@Unroll
	def "should find party by street name and city in elastic : #streetName #city"() {
		when:
		def parties = api.search(builder().street(streetName).city(city).build())

		then:
		parties.size() == matchingParties.size()
		parties.stream().allMatch({ it -> matchingParties.contains(it.partyId) })

		where:
		streetName             | city                        || matchingParties
		"Stanów zjednoczonych" | "Ełk"                       || []
		"Stanów zjednoczonych" | "Warszawa"                  || [19L]
		"ŚW. ANTONIEGO"        | "Jakas-dziwna-miejscowosć2" || [19L]
		"35-LECIA PRL"         | "Ełk"                       || [8L]
	}

	def "should find party ordered by name in party index ASC"() {
		when:
		def parties = api.search(builder().sorter(asc(NAME)).pager(of(0, 2)).build())

		then:
		parties
		parties.get(0).partyId == 22L
		parties.get(1).partyId == 23L
	}

	def "should find party ordered by name in party index DESC"() {
		when:
		def parties = api.search(builder().sorter(desc(NAME)).pager(of(0, 2)).build())

		then:
		parties
		parties.get(0).partyId == 25L
		parties.get(1).partyId == 9L
	}

	List<Party> getParties(String fileName) {
		return Files.readAllLines(Paths.get(PartyElasticApiImplTest.class.getResource(fileName).toURI()))
				.stream()
				.map({ line -> mapper.readValue(line, Party.class) })
				.collect(Collectors.toList())
	}
}
