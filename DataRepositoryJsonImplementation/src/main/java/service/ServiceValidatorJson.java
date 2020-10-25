package service;

import utils.JsonValidationUtils;

public class ServiceValidatorJson implements IServiceValidator {

    private static ServiceValidatorJson instance;

    public synchronized static ServiceValidatorJson get() {
        if (instance == null)
            instance = new ServiceValidatorJson();
        return instance;
    }

    @Override
    public boolean validate(String json, String jsonMetaSchema) throws Exception {
        return JsonValidationUtils.isJsonValid(jsonMetaSchema, json);
    }
}
