package pl.decerto.workshop.elastic.app.party;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
class SearchCriteria {

	private String firstName;

	private String name;

	private String exactName;

	private String city;

	private String street;

	private Sorter sorter;

	private Pager pager;

	@AllArgsConstructor
	@Getter
	public static class Sorter {

		private final String field;

		private final boolean asc;

		public static Sorter asc(String field) {
			return new Sorter(field, true);
		}

		public static Sorter desc(String field) {
			return new Sorter(field, false);
		}

	}

	@Data
	@AllArgsConstructor(staticName = "of")
	public static class Pager {

		private final int pageNumber;

		private final int pageSize;

	}

}
