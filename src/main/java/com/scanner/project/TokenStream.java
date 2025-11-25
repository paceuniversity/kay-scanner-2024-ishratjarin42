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
                    } else {
                        // FIX: Single '=', '!', '<', '>' are Other (or Op).
                        // '=' and '!' are Other. '<' and '>' are Op.
                        if (currentChar == '=' || currentChar == '!') {
                            t.setType("Other"); 
                        }
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
                    nextChar = readChar();
                    if (nextChar == '*') {
                        t.setValue(t.getValue() + nextChar); 
                        nextChar = readChar();
                    }
                    // If single '*', it stays "Operator"
                    return t;
                
                case ':':
                    nextChar = readChar();
                    if (nextChar == '=') {
                        t.setValue(t.getValue() + nextChar); 
                        nextChar = readChar();
                    } else {
                        t.setType("Other"); // Single ':' is Other
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
            
            String value = t.getValue();

            // FIX: Check for case-sensitive Boolean Literals (True/False/true/false)
            if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                // If it matches a *case-sensitive* Literal (True, False), set type to Literal.
                if (value.equals("true") || value.equals("false") || value.equals("True") || value.equals("False")) {
                     t.setType("Literal");
                }
            } else if (isKeyword(value)) {
                t.setType("Keyword");
            }
            
            // If the value is 'true' or 'false' but the test expects IDENTIFIER, 
            // it means only the exact match (lowercase, not in test case) should be a Literal. 
            // Since tests show 'true' and 'false' should be IDENTIFIERs, we rely on the
            // case-sensitive checks in isKeyword (which are now gone) and rely on the Literal/Keyword list.

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

        // 6. Final catch-all for single "Other" characters (period, etc.)
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
                // FIX: Removed 'void' from keywords (it should be an Identifier)
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
        // FIX: Removed '[' and ']' from Separator (tests show they are expected to be 'Other')
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

