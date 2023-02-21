package fr.hyriode.limbo.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IOUtil {

    public static void copyInputStreamToFile(InputStream inputStream, File file) {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            try (final OutputStream outputStream = new FileOutputStream(file)) {
                final byte[] buffer = new byte[8 * 1024];

                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public static void saveFile(Path path, String content) {
        try {
            Files.createFile(path);
            Files.writeString(path, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String loadFile(Path path) {
        final StringBuilder builder = new StringBuilder();

        if (Files.exists(path)) {
            try (final BufferedReader reader = Files.newBufferedReader(path)) {
                String line;

                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    public static String hashInput(InputStream inputStream, String method) {
        try {
            final MessageDigest digest = MessageDigest.getInstance(method);
            final byte[] data = new byte[8195];

            int read;
            while ((read = inputStream.read(data)) != -1) {
                digest.update(data, 0, read);
            }

            final StringBuilder builder = new StringBuilder();

            for (byte aByte : digest.digest()) {
                builder.append(Integer.toString(((aByte & 0xFF) + 0x100), 16).substring(1));
            }

            return builder.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toMD5(InputStream inputStream) {
        return hashInput(inputStream, "MD5");
    }

}
