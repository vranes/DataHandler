package utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileUtils {

    public static String fileToString(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        String absolutePath = new File("").getAbsolutePath() + filePath;
        File file = new File(absolutePath);
        file.setReadable(true);
        if(file.exists()){
            System.out.println(absolutePath);
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
                System.out.println("gggggggg");

                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new String();
    }

    public static void stringToFile(String filePath, String data) {

        String absolutePath = new File("").getAbsolutePath() + filePath;
        File file = new File(absolutePath);
        file.setWritable(true);
        System.out.println(absolutePath);
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            // convert string to byte array
            byte[] bytes = data.getBytes();
            // write byte array to file
            bos.write(bytes);
            bos.close();
            fos.close();
            System.out.print("Data written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
