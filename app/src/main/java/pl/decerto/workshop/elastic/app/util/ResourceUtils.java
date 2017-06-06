package pl.decerto.workshop.elastic.app.util;

import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;

public class ResourceUtils {

	public static String getResource(String filePath) throws IOException {
		return IOUtils.toString(ResourceUtils.class.getResourceAsStream(filePath), Charset.defaultCharset());
	}

}
