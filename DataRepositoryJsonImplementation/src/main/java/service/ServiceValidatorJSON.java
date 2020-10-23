package service;

import utils.JsonValidationUtils;

public class ServiceValidatorJSON implements IServiceValidator {

    private static ServiceValidatorJSON instance;

    // Singleton pattern
    public synchronized static ServiceValidatorJSON get() {
        if (instance == null)
            instance = new ServiceValidatorJSON();
        return instance;
    }

    @Override
    public boolean validate(String json, String jsonMetaSchema) throws Exception {
        return JsonValidationUtils.isJsonValid(jsonMetaSchema, json);
    }
}
