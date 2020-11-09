package service;

import Exceptions.FormatException;

public class CustomReader {

    private int pos;
    private String string = null;
    private char beginWord;
    private char endWord;
    private char assign;

    public CustomReader(String string, char beginWord, char endWord, char assign){
        this.pos = -1;
        this.string = string;
        this.beginWord = beginWord;
        this.endWord = endWord;
        this.assign = assign;
    }

    public char peek(){
        readSpace();
        if(pos+1 >= string.length()) return ' ';
        return string.charAt(pos+1);
    }

    public void readSpace(){
        if(pos+1 >= string.length()) return;

        while (string.charAt(pos+1) == '\n'
                || string.charAt(pos+1) == ' '
                || string.charAt(pos+1) == '\t')
            pos++;
    }

    public char read(){
        if(pos+1 >= string.length()) return ' ';
        return string.charAt(++pos);
    }

    public void read(char c) throws FormatException{
        readSpace();
        if (read() != c)
            throw new FormatException("Custom Format exception, looked for: " + c + ", found: " + string.charAt(pos) + ".");
    }

    public String nextName() throws FormatException {
        String str = "";
        read(beginWord);
        while (peek() != endWord){
            str += read();
        }
        read(endWord);
        return str;
    }

    public String nextString() throws FormatException {
        String str = "";
        read(assign);
        read(beginWord);
        while (peek() != endWord){
            str += read();
        }
        read(endWord);
        return str;
    }
}
