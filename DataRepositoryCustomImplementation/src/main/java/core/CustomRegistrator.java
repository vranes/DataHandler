package core;

import importexport.ImportExportCustom;
import service.ObjectConverterCustom;
import service.StorageCustom;

public class CustomRegistrator {
    static {
        RepositoryManager.registerImportExport(ImportExportCustom.getInstance());
        RepositoryManager.registerStorage(StorageCustom.getInstance());
        RepositoryManager.registerObjectConverter(ObjectConverterCustom.getInstance());
    }
}
