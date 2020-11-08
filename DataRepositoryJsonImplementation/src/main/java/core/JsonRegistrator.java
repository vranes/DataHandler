package core;

import importexport.ImportExportJson;
import service.ObjectConverterJson;
import service.StorageJson;

public class JsonRegistrator {
    static {
        RepositoryManager.registerImportExport(ImportExportJson.getInstance());
        RepositoryManager.registerStorage(StorageJson.getInstance());
        RepositoryManager.registerObjectConverter(ObjectConverterJson.getInstance());
    }
}
