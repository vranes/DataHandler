package service;

import Exceptions.FormatException;

/**
 *  Interface for a simple format-object converter
 */
public interface IObjectConverter {
    /**
     *  Method that receives an object and returns a string representation in the required format
     */
    String objectToFormat(Object object) throws FormatException;
    /**
     *   Method that receives a string of an object in a certain format
     *   and returns an object instance of the reqired class
     */
    Object formatToObject(String entity, Class<?> classOf) throws FormatException;
}
