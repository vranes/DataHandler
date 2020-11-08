package core;

import importexport.IImportExport;
import service.AbstractStorage;
import service.IObjectConverter;

public class RepositoryManager {

    private static IImportExport importExport;
    private static AbstractStorage storage;
    private static IObjectConverter objectConverter;

    public static void registerImportExport(IImportExport importExportReg) {
        importExport = importExportReg;
    }

    public static void registerStorage(AbstractStorage storageReg) {
        storage = storageReg;
    }

    public static void registerObjectConverter(IObjectConverter objectConverterReg) {
        objectConverter = objectConverterReg;
    }

    public static IImportExport getImportExport() {
        return importExport;
    }

    public static AbstractStorage getStorage() {
        return storage;
    }

    public static IObjectConverter getObjectConverter() {
        return objectConverter;
    }
}
