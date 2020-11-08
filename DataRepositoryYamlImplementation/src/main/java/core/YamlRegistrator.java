package core;

import importexport.ImportExportYaml;
import service.*;

public class YamlRegistrator {
    static {
        RepositoryManager.registerImportExport(ImportExportYaml.getInstance());
        RepositoryManager.registerStorage(StorageYaml.getInstance());
        RepositoryManager.registerObjectConverter(ObjectConverterYaml.getInstance());
    }
}
