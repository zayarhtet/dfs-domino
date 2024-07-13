package resource;

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

public class ResourceLoader {
    public static InputStream loadResource(String resName){
        return ResourceLoader.class.getClassLoader().getResourceAsStream(resName);
    }

    public static List<String> getResourcePaths(String directory) throws IOException, URISyntaxException {
        List<String> resourcePaths = new ArrayList<>();
        ClassLoader classLoader = ResourceLoader.class.getClassLoader();

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

    public static void main(String[] args) {
        // Example of loading a resource
        String resourceName = "resource/levels/01.txt";
        InputStream inputStream = loadResource(resourceName);

        if (inputStream != null) {
            // Process the InputStream
            System.out.println("Loaded resource: " + resourceName);
        } else {
            System.out.println("Resource not found: " + resourceName);
        }
    }
}