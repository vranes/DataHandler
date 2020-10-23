package service;
public interface IObjectConverter {

    String objectToFormat(Object object) throws Exception;
    Object formatToObject(String entity, Class<?> classOf) throws Exception;
}
