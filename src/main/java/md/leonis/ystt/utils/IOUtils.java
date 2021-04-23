package md.leonis.ystt.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class IOUtils {

    public static void saveTextFile(List<String> sounds, Path path) throws IOException {

        createDirectories(path.getParent());
        Files.write(path, sounds);
    }

    public void saveToFile(String filePath, String fileName, List<String> lines) throws IOException {

        Path directory = Paths.get(filePath);

        Files.createDirectories(directory);
        Files.write(directory.resolve(fileName), lines, Charset.defaultCharset());
    }

    public static byte[] loadBytes(Path file) throws IOException {
        return Files.readAllBytes(file);
    }

    public static void backupFile(Path path) {
        backupFile(path.toFile());
    }

    public static void backupFile(File file) {
        if (file.exists()) {
            File backupFile = new File(file.getAbsolutePath() + ".bak");
            if (backupFile.exists()) {
                backupFile.delete();
            }
            file.renameTo(backupFile);
        }
    }

    public static void copyFile(Path src, Path dest) {
        try {
            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteFile(Path path) {
        try {
            Files.delete(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static long fileSize(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> loadTextFile(Path path) {
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void createDirectories(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            //TODO
            //LOGGER.error("Can't create directory: {}", path.toString(), e);
        }
    }
}
