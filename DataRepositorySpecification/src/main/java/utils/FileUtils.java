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
    public static String fileToString(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        String absolutePath = filePath;
        File file = new File(absolutePath);
        file.setReadable(true);

        if(file.exists()){
            try (Stream<String> stream = Files.lines(Paths.get(absolutePath),
                    StandardCharsets.UTF_8)) {
                stream.forEach(s -> contentBuilder.append(s).append("\n"));
                return contentBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new String();
    }

    /**
     *  Writes string content to the file
     */
    public static void stringToFile(String filePath, String data) {

        String absolutePath = filePath;
        File file = new File(absolutePath);
        file.setWritable(true);

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            // convert string to byte array
            byte[] bytes = data.getBytes();
            // write byte array to file
            bos.write("".getBytes());
            bos.write(bytes);
            bos.close();
            fos.close();
            System.out.print("Data written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
