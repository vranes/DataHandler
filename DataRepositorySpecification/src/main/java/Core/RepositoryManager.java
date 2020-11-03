package Core;

import importexport.IImportExport;
import service.AbstractStorage;
import service.IObjectConverter;

public class RepositoryManager {

    private static IImportExport importExport;
    private static AbstractStorage storage;
    private static IObjectConverter objectConverter;

    public static void registerImportExport(IImportExport importExport) {
        importExport = importExport;
    }

    public static void registerStorage(AbstractStorage storage) {
        storage = storage;
    }

    public static void registerObjectConverter(IObjectConverter objectConverter) {
        objectConverter = objectConverter;
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
