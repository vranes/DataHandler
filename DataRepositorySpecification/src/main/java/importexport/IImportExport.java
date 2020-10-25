package importexport;

import java.io.IOException;

public interface IImportExport {

    // ova metoda treba da pri pokretanju ucita sve iz cele odabrane baze, fajl po fajl
    // svaki entitet da pretvori u <Entity> objekat i da ih sacuva u listi entiteta
    void importFile(String sourcePath) throws IOException;
    void exportFile(String destinationPath, String json) throws IOException;
}
