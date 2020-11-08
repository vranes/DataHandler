package service;

import Exceptions.FormatException;

public class CustomReader {

    private int pos;
    private String string = null;
    private char beginObject = '(';
    private char endObject = ')';
    private char beginList = '[';
    private char endList = ']';
    private char beginMap = '{';
    private char endMap = '}';
    private char beginWord = '\'';
    private char endWord = '\'';
    private char delimiter = ';';
    private char assign = '-';

    public CustomReader(int pos, String string){
        pos = pos;
        string = string;
    }

    public char peek(){
        return string.charAt(pos+1);
    }

    public char read(){
        return string.charAt(++pos);
    }

    public void read(char c) throws FormatException{
        if (string.charAt(++pos) != c)
            throw new FormatException("Custom Format exception");
    }

    public String nextName() throws FormatException {
        String str = "";
        if (read() == '\''){
            while (peek() != '\''){
                str += read();
            }
            read();
            return str;
        }
        else throw new FormatException("Custom Format exception");
    }

    public String nextString() throws FormatException {
        String str = "";
        if(read() != assign || read() != '\'')
            throw new FormatException("Custom Format exception");

        while (peek() != '\''){
            str += read();
        }
        read();
        return str;
    }
}
