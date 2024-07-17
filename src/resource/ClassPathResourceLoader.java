package resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClassPathResourceLoader implements ResourceLoader {
    private static ClassPathResourceLoader instance;

    private ClassPathResourceLoader() {}

    public InputStream loadResource(String resName) throws FileNotFoundException {
        InputStream is = ClassPathResourceLoader.class.getClassLoader().getResourceAsStream(resName);
        if (is == null) { throw new FileNotFoundException(resName + " is not found."); }
        return is;
    }

    public List<String> getResourcePaths(String directory) throws IOException, URISyntaxException {
        List<String> resourcePaths = new ArrayList<>();
        ClassLoader classLoader = ClassPathResourceLoader.class.getClassLoader();

        URL dirURL = classLoader.getResource(directory);
        System.out.println(dirURL);
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
            Path dirPath = Paths.get(dirURL.toURI());
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "*.txt")) {
                for (Path entry : stream) {
                    resourcePaths.add(directory + "/" + entry.getFileName().toString());
                }
            }
        }
        return resourcePaths;
    }

    public static ClassPathResourceLoader newInstance() {
        if (instance == null) {
            instance = new ClassPathResourceLoader();
        }
        return instance;
    }
}