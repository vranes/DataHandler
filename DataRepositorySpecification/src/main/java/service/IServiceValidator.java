package service;

public interface IServiceValidator {
    boolean validate(String entity, String objectMetaSchema) throws Exception;

    // na raspolaganju stoji da se prosiri interface za druge data file formate
    // xml,yaml..
}
