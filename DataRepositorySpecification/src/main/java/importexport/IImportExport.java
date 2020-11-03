package importexport;

import model.Entity;

import java.io.IOException;
import java.util.List;

public interface IImportExport {

    // ova metoda cita sve iz odabranog fajla
    // svaki entitet pretvara u <Entity> objekat i dodaje u listu entiteta
    // koristicemo je na pocetku da procitamo fajl po fajl celu bazu a i kada nam treba neki odredjeni fajl

    String importFile (String sourcePath);
    List<Entity> importEntities (String sourcePath);
    void exportFile (String destinationPath, String data);
    void exportEntities (String destinationPath, List<Entity> entities);

}
