package com.scanner.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TokenStream {

    private boolean isEof = false;
    private char nextChar = ' ';
    private BufferedReader input;

    public boolean isEoFile() {
        return isEof;
    }

    public TokenStream(String fileName) {
        try {
            input = new BufferedReader(new FileReader(fileName));
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
            char currentChar = nextChar;
            
            // Check for multi-character operators that require special handling (**, !|)
            if (currentChar == '*') {
                nextChar = readChar();
                if (nextChar == '*') {
                    t.setValue("**"); 
                    nextChar = readChar();
                } else {
                    t.setValue(String.valueOf(currentChar));
                }
                return t;
            } else if (currentChar == '!') {
                nextChar = readChar();
                if (nextChar == '=' || nextChar == '|') { 
                    t.setValue(String.valueOf(currentChar) + nextChar);
                    nextChar = readChar();
                } else {
                    t.setValue(String.valueOf(currentChar));
                    t.setType("Other"); // Single '!' is 'Other'
                }
                return t;
            } 
            
            // For all other operators, perform single lookahead for one- or two-character tokens
            nextChar = readChar(); // Consume the currentChar
            t.setValue(String.valueOf(currentChar));

            if (currentChar == '=' || currentChar == '|' || currentChar == '&' || currentChar == ':' || currentChar == '<' || currentChar == '>') {
                
                // Check for secondary character in operators like ==, ||, &&, :=, <=, >=
                if (currentChar == nextChar) { 
                    // This handles ==, ||, &&
                    t.setValue(t.getValue() + nextChar);
                    nextChar = readChar();
                } else if ((currentChar == '<' || currentChar == '>') && nextChar == '=') { 
                    // This handles <= and >=
                    t.setValue(t.getValue() + nextChar);
                    nextChar = readChar();
                } else if (currentChar == ':' && nextChar == '=') { 
                    // This handles :=
                    t.setValue(t.getValue() + nextChar);
                    nextChar = readChar();
                }
            }
            
            // Final type assignment for single characters which are 'Other'
            if (t.getValue().equals("=") || t.getValue().equals("|") || t.getValue().equals("&") || t.getValue().equals(":")) {
                 t.setType("Other");
            }
            
            // Operators like +, -, %, ^, ~ are caught here as 'Operator'
            return t;
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
            
            String value = t.getValue();

            // Logic: True/False are Literals. All keywords are Keywords. All others (including true/false) are Identifiers.
            if (value.equals("True") || value.equals("False")) {
                 t.setType("Literal");
            } else if (isKeyword(value)) {
                t.setType("Keyword");
            }
            
            return t; 
        }

        // 5. Integer Literal
        if (isDigit(nextChar)) {
            t.setType("Literal");
            while (isDigit(nextChar)) {
                t.setValue(t.getValue() + nextChar);
                nextChar = readChar();
            }

            return t;
        }

        // 6. Final catch-all for single "Other" characters
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

    private void skipWhiteSpace() {
        while (!isEof && isWhiteSpace(nextChar)) {
            nextChar = readChar();
        }
    }

    private boolean isSeparator(char c) {
        return (c == '(' || c == ')' ||
                c == '{' || c == '}' ||
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


