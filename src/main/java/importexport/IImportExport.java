package importexport;

import java.io.IOException;

public interface IImportExport {

    String importFile(String sourcePath) throws IOException;
    void exportFile(String destinationPath, String json) throws IOException;
}
