package resource;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class FilePathResourceLoader implements ResourceLoader {
    private static FilePathResourceLoader instance;

    private FilePathResourceLoader() {}

    @Override
    public InputStream loadResource(String resName) throws FileNotFoundException {
        try {
            return new FileInputStream(resName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileNotFoundException(resName + " is not found.");
        }
    }

    @Override
    public List<String> getResourcePaths(String directory) throws IOException, URISyntaxException {
        List<String> filepaths = new ArrayList<>();
        File folder = new File(directory);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        filepaths.add(file.getPath());
                    }
                }
            }
        }
        return filepaths;
    }

    public static FilePathResourceLoader newInstance() {
        if (instance == null) {
            instance = new FilePathResourceLoader();
        }
        return instance;
    }
}
