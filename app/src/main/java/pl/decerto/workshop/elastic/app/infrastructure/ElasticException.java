package pl.decerto.workshop.elastic.app.infrastructure;

public class ElasticException extends RuntimeException {

	private static final String INDEX_CREATION_FAILURE = "Error while creating index : %s , %s : %s";

	protected ElasticException(String message, Throwable cause) {
		super(message, cause);
	}

	public static ElasticException indexCreationFailure(String indexName, String fileName, Throwable error) {
		return new ElasticException(String.format(INDEX_CREATION_FAILURE, indexName, fileName, error.getMessage()), error);
	}

}
