package importexport;

import model.Entity;

import java.io.IOException;
import java.util.List;

public interface IImportExport {

    // ova metoda cita sve iz odabranog fajla
    // svaki entitet pretvara u <Entity> objekat i dodaje u listu entiteta
    // koristicemo je na pocetku da procitamo fajl po fajl celu bazu a i kada nam treba neki odredjeni fajl
    List<Entity> importFileEntities(String sourcePath) throws IOException;
    String importFileString (String sourcePath) throws IOException;
    void exportFile(String destinationPath, String data) throws IOException;
}
