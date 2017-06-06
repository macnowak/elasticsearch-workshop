package pl.decerto.workshop.elastic.app.party;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.decerto.workshop.elastic.app.infrastructure.ElasticSearchDocument;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Party implements ElasticSearchDocument {

	public static final String PARTY = "party";

	private Long partyId;

	private String firstName;

	private String name;

	private String label;

	private String nip;

	private String pesel;

	private String regon;

	private LocalDate birthDate;

	private Set<Address> addresses;

	@Override
	@JsonIgnore
	public String getId() {
		return String.valueOf(partyId);
	}

	@Override
	@JsonIgnore
	public String getIndex() {
		return PARTY;
	}

	@Override
	@JsonIgnore
	public String getType() {
		return PARTY;
	}

	public static final class Fields {

		public static final String FIRST_NAME = "firstName";

		public static final String NAME = "name";

		public static final String NAME_AUTOCOMPLETE = "name.autocomplete";

		public static final String ADDRESSES = "addresses";

		private Fields() {
		}
	}
}
