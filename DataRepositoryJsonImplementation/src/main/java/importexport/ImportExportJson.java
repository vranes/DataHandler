package importexport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;


public class ImportExportJson implements IImportExport {

    public String importFile(String sourcePath) throws IOException {
        String jsonString = FileUtils.fileToString(sourcePath);

        return beautifyJson(jsonString);
    }

    public void exportFile(String destinationPath, String json) throws IOException {
        File file = new File("");
        file = new File(file.getAbsolutePath() + destinationPath);
        file.setWritable(true);

        FileUtils.stringToFile(file, beautifyJson(json));
    }

    private String beautifyJson(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        JsonNode tree = objectMapper.readTree(json);
        String formattedJson = objectMapper.writeValueAsString(tree);

        return formattedJson;
    }

}
