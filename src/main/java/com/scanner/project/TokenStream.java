package com.scanner.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TokenStream {

    // Instance variables 
    private boolean isEof = false; // is end of file
    private char nextChar = ' '; // next character in input stream
    private BufferedReader input;

    public boolean isEoFile() {
        return isEof;
    }

    // Constructor
    public TokenStream(String fileName) {
        try {
            input = new BufferedReader(new FileReader(fileName));
            // PRIME the stream
            nextChar = readChar();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            isEof = true;
        }
    }

    public Token nextToken() {
        Token t = new Token();
        t.setType("Other");
        t.setValue("");

        skipWhiteSpace();
        
        if (isEof) {
            t.setType("Eof");
            return t;
        }

        // 1. Handle comments and division operator
        while (nextChar == '/') {
            nextChar = readChar();
            if (nextChar == '/') {
                // Skip comment until end of line
                while (!isEof && !isEndOfLine(nextChar)) {
                    nextChar = readChar();
                }
                if (!isEof) {
                    nextChar = readChar();
                }
                skipWhiteSpace();
            } else {
                t.setType("Operator");
                t.setValue("/");
                return t;
            }
        }

        // 2. Operators
        if (isOperator(nextChar)) {
            t.setType("Operator");
            t.setValue(t.getValue() + nextChar);
            char currentChar = t.getValue().charAt(0);

            switch (currentChar) {
                case '<':
                case '>':
                case '=':
                case '!':
                    nextChar = readChar();
                    if (nextChar == '=') {
                        t.setValue(t.getValue() + nextChar); 
                        nextChar = readChar();
                    } else if (currentChar == '!' || currentChar == '=') {
                         t.setType("Other"); 
                    }
                    return t;

                case '|':
                    nextChar = readChar();
                    if (nextChar == '|') {
                        t.setValue(t.getValue() + nextChar);
                        nextChar = readChar();
                    } else {
                        t.setType("Other"); 
                    }
                    return t;

                case '&':
                    nextChar = readChar();
                    if (nextChar == '&') {
                        t.setValue(t.getValue() + nextChar);
                        nextChar = readChar();
                    } else {
                        t.setType("Other"); 
                    }
                    return t;
                
                case '*': 
                case ':':
                    char firstChar = currentChar;
                    nextChar = readChar();
                    if ((firstChar == '*' && nextChar == '*') || (firstChar == ':' && nextChar == '=')) {
                        t.setValue(t.getValue() + nextChar); 
                        nextChar = readChar();
                    } else {
                        if (firstChar == ':') {
                           t.setType("Other"); 
                        }
                        // Single '*' remains "Operator" and stream is already advanced by one
                    }
                    return t;

                default: // Handles +, -, %, ^, ~
                    nextChar = readChar();
                    return t;
            }
        }

        // 3. Separators
        if (isSeparator(nextChar)) {
            t.setType("Separator");
            t.setValue(String.valueOf(nextChar));
            nextChar = readChar();
            return t;
        }

        // 4. Identifier / Keyword / Boolean Literal
        if (isLetter(nextChar)) {
            t.setType("Identifier");
            while (isLetter(nextChar) || isDigit(nextChar)) {
                t.setValue(t.getValue() + nextChar);
                nextChar = readChar();
            }
            
            if (t.getValue().equals("true") || t.getValue().equals("false")) {
                t.setType("Literal");
            } else if (isKeyword(t.getValue())) {
                t.setType("Keyword");
            }

            return t; // Stream is already advanced one past the token
        }

        // 5. Integer Literal
        if (isDigit(nextChar)) {
            t.setType("Literal");
            while (isDigit(nextChar)) {
                t.setValue(t.getValue() + nextChar);
                nextChar = readChar();
            }

            return t; // Stream is already advanced one past the token
        }

        // 6. Final catch-all for single "Other" characters
        if (!isEof) {
            t.setValue(String.valueOf(nextChar));
            nextChar = readChar(); // Advance the stream past the 'Other' character
            t.setType("Other");
            return t;
        }

        return t;
    }

    private char readChar() {
        int i = 0;
        if (isEof)
            return (char) 0;

        try {
            i = input.read();
        } catch (IOException e) {
            System.exit(-1);
        }

        if (i == -1) {
            isEof = true;
            return (char) 0;
        }
        return (char) i;
    }

    // ================= HELPER METHODS =================

    private boolean isKeyword(String s) {
        if (s == null) return false;
        switch (s) {
            case "if":
            case "else":
            case "while":
            case "for":
            case "return":
            case "int":
            case "boolean":
            case "void":
            case "break":
            case "continue":
            case "class":
            case "new":
            case "main":      
            case "integer":   
            case "bool":      
                return true;
            default:
                return false;
        }
    }

    private boolean isWhiteSpace(char c) {
        return (c == ' ' || c == '\t' || c == '\r' || c == '\n' || c == '\f');
    }

    private boolean isEndOfLine(char c) {
        return (c == '\r' || c == '\n' || c == '\f');
    }

    private boolean isEndOfToken(char c) {
        return (isWhiteSpace(c) || isOperator(c) || isSeparator(c) || isEof);
    }

    private void skipWhiteSpace() {
        while (!isEof && isWhiteSpace(nextChar)) {
            nextChar = readChar();
        }
    }

    private boolean isSeparator(char c) {
        // Includes '[' and ']' which are often 'Other' or 'Separator'
        return (c == '(' || c == ')' ||
                c == '{' || c == '}' ||
                c == '[' || c == ']' || 
                c == ',' || c == ';');
    }

    private boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' ||
                c == '<' || c == '>' || c == '=' || c == '!' ||
                c == '|' || c == '&' || c == '^' || c == '~' || c == ':');
    }

    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z');
    }

    private boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    public boolean isEndofFile() {
        return isEof;
    }
}
