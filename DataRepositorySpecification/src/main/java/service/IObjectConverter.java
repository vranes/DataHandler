package service;
public interface IObjectConverter {

    String convertObjectToFormat(Object object) throws Exception;

    Object convertFormatToObject(String entity, Class<?> classOf) throws Exception;
}
