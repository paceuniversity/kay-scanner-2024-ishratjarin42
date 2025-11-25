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
        
        // Handle EOF before trying to process characters
        if (isEof) {
            t.setType("Eof");
            return t;
        }

        // Handle comments and division operator
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

        // Operators
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
                        t.setValue(t.getValue() + nextChar); // e.g., '=='
                        nextChar = readChar();
                    } else if (currentChar == '!' || currentChar == '=') {
                         // Fix: Single '!' and '=' are 'Other' based on passing tests
                         t.setType("Other"); 
                    }
                    return t;

                case '|':
                    nextChar = readChar();
                    if (nextChar == '|') {
                        t.setValue(t.getValue() + nextChar);
                        nextChar = readChar();
                    } else {
                        t.setType("Other"); // Fix: Single '|' is 'Other'
                    }
                    return t;

                case '&':
                    nextChar = readChar();
                    if (nextChar == '&') {
                        t.setValue(t.getValue() + nextChar);
                        nextChar = readChar();
                    } else {
                        t.setType("Other"); // Fix: Single '&' is 'Other'
                    }
                    return t;
                
                case '*': 
                case ':':
                    char firstChar = currentChar;
                    nextChar = readChar();
                    if ((firstChar == '*' && nextChar == '*') || (firstChar == ':' && nextChar == '=')) {
                        t.setValue(t.getValue() + nextChar); // ** or :=
                        nextChar = readChar();
                    } else {
                        // Fix: Single ':' is 'Other'
                        if (firstChar == ':') {
                           t.setType("Other"); 
                        }
                        // Single '*' remains "Operator"
                    }
                    return t;

                default: // Handles +, -, %, ^, ~
                    nextChar = readChar();
                    return t;
            }
        }

        // Separators
        if (isSeparator(nextChar)) {
            t.setType("Separator");
            t.setValue(String.valueOf(nextChar));
            nextChar = readChar();
            return t;
        }

        // Identifier / Keyword / Boolean Literal
        if (isLetter(nextChar)) {
            t.setType("Identifier");
            while (isLetter(nextChar) || isDigit(nextChar)) {
                t.setValue(t.getValue() + nextChar);
                nextChar = readChar();
            }

            if (isKeyword(t.getValue())) {
                t.setType("Keyword");
            } else if (t.getValue().equals("true") || t.getValue().equals("false")) {
                t.setType("Literal");
            }
            
            // FIX: Return immediately after processing token
            return t;
        }

        // Integer Literal
        if (isDigit(nextChar)) {
            t.setType("Literal");
            while (isDigit(nextChar)) {
                t.setValue(t.getValue() + nextChar);
                nextChar = readChar();
            }

            // FIX: Return immediately after processing token
            return t;
        }

        // Fallback for single "Other" characters (non-whitespace, non-operator, non-separator, non-start-of-identifier/literal)
        if (!isEof) {
            t.setValue(String.valueOf(nextChar));
            nextChar = readChar();
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
            case "main":      // <-- ADDED FIX
            case "integer":   // <-- ADDED FIX
            case "bool":      // <-- ADDED FIX
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
        // This is primarily used in comment/whitespace skipping, not core tokenization logic now
        return (isWhiteSpace(c) || isOperator(c) || isSeparator(c) || isEof);
    }

    private void skipWhiteSpace() {
        while (!isEof && isWhiteSpace(nextChar)) {
            nextChar = readChar();
        }
    }

    private boolean isSeparator(char c) {
        return (c == '(' || c == ')' ||
                c == '{' || c == '}' ||
                c == '[' || c == ']' ||
                c == ',' || c == ';');
    }

    private boolean isOperator(char c) {
        // Includes all possible starting characters for operators
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
