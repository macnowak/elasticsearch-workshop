package pl.decerto.workshop.elastic.app.infrastructure;

/**
 * Interfejs obiektow indeksowanych w elasticu
 */
public interface ElasticSearchDocument {

	String getId();

	String getIndex();

	String getType();
}
