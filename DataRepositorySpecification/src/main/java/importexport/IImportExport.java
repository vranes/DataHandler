package importexport;
import model.Entity;
import java.util.List;

/**
 *  Interface for importing and exporting files in required format. Relies on FileUtils class methods.
 */
public interface IImportExport {


    /**
     * Returns a list of all entities from a file
     */
    List<Entity> importEntities (String sourcePath);
    /**
     * Writes the formatted string representation of the passed entities list to the file
     */
    void exportEntities (String destinationPath, List<Entity> entities);

}
