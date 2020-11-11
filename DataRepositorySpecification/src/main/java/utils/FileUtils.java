package utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Class that works directly with files - supports reading and writing operations
 */
public class FileUtils {

    /**
     * Returns string content of a file
     */
    public static String fileToString(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        String absolutePath = filePath;
        File file = new File(absolutePath);
        file.setReadable(true);
        if(!file.exists()){
            file.createNewFile();
            return new String();
        }
        Stream<String> stream = Files.lines(Paths.get(absolutePath), StandardCharsets.UTF_8);
        stream.forEach(s -> contentBuilder.append(s).append("\n"));
        return contentBuilder.toString();
    }

    /**
     *  Writes string content to the file
     */
    public static void stringToFile(String filePath, String data) throws IOException {

        String absolutePath = filePath;
        File file = new File(absolutePath);
        file.setWritable(true);

        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        // convert string to byte array
        byte[] bytes = data.getBytes();
        // write byte array to file
        bos.write("".getBytes());
        bos.write(bytes);
        bos.close();
        fos.close();
    }

}
