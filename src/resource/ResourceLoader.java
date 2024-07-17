package resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

public interface ResourceLoader {
    InputStream loadResource(String resName) throws FileNotFoundException;
    List<String> getResourcePaths(String directory) throws IOException, URISyntaxException;
}
