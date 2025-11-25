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
        
        // Check for EOF first
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
                // It's a single '/' operator
                t.setType("Operator");
                t.setValue("/");
                return t;
            }
        }

        // Operators
        if (isOperator(nextChar)) {
            t.setType("Operator");
            t.setValue(t.getValue() + nextChar);
            char firstChar = nextChar;
            nextChar = readChar(); // Advance to check for second character

            // Check for two-character operators
            String twoCharOp = "" + firstChar + nextChar;

            if (twoCharOp.equals("==") || twoCharOp.equals("!=") || 
                twoCharOp.equals("<=") || twoCharOp.equals(">=") ||
                twoCharOp.equals("||") || twoCharOp.equals("&&") ||
                twoCharOp.equals("**") || twoCharOp.equals(":=")) { // Added ** and :=
                
                t.setValue(twoCharOp);
                nextChar = readChar(); // Advance past the second character
                return t;
            } 
            // Handle single characters that START a two-character sequence, but are alone (e.g., '|' when '||' isn't next)
            else if (firstChar == '|' || firstChar == '&' || firstChar == '!' || firstChar == ':') {
                // Assuming these single symbols are "Other" or single operators if not followed by a second char
                // The tests indicated '|', '&', and '!' alone might be "Other", so we set the type back
                // We keep the value as the single character (t.getValue() already has the firstChar)
                if (firstChar == '|' || firstChar == '&' || firstChar == '!') {
                     t.setType("Other");
                }
                // If it's '+', '-', '*', etc., it remains "Operator" and we return it
                
                // nextChar is already advanced in the operator block, so we return
                return t;
            }
            
            // Default: If it's a single-character operator (+, -, *, <, >, etc.)
            // We already consumed the first character, so we return the token
            return t;
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
            
            // FIX #1: Return immediately
            return t;
        }

        // Integer Literal
        if (isDigit(nextChar)) {
            t.setType("Literal");
            while (isDigit(nextChar)) {
                t.setValue(t.getValue() + nextChar);
                nextChar = readChar();
            }

            // FIX #2: Return immediately
            return t;
        }

        // Final catch-all for any single unhandled character as "Other"
        t.setType("Other");
        t.setValue(String.valueOf(nextChar));
        nextChar = readChar();
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
            case "main":      // <-- ADDED
            case "integer":   // <-- ADDED
            case "bool":      // <-- ADDED
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
