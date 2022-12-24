package fr.hyriode.limbo.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IOUtil {

    public static boolean createDirectory(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

}
