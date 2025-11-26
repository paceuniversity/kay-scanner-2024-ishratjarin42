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
            char tempChar = nextChar;
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
                // Single '/' is Division Operator
                t.setType("Operator");
                t.setValue(String.valueOf(tempChar));
                return t;
            }
        }
        
        // 2. Separators
        if (isSeparator(nextChar)) {
            t.setType("Separator");
            t.setValue(String.valueOf(nextChar));
            nextChar = readChar();
            return t;
        }

        // 3. Explicit Single-Character Other Tokens (Handles [ ] @ .)
        if (nextChar == '[' || nextChar == ']' || nextChar == '@' || nextChar == '.') {
            t.setType("Other");
            t.setValue(String.valueOf(nextChar));
            nextChar = readChar();
            return t;
        }
        
        // 3.5. Guaranteed Single-Character Operators (FIX FOR -5 ISSUE)
        // These tokens DO NOT have a double-character form, so they should use the simple consume pattern.
        if (nextChar == '+' || nextChar == '-' || nextChar == '%' || nextChar == '^' || nextChar == '~') {
            t.setType("Operator");
            t.setValue(String.valueOf(nextChar));
            nextChar = readChar();
            return t;
        }

        // 4. Lookahead Operators / Others (Only tokens that can be 1 or 2 chars, or have complex single-char rules)
        if (isLookaheadOperator(nextChar)) {
            char currentChar = nextChar;
            
            // Advance nextChar for the lookahead before the switch starts (Standard approach)
            nextChar = readChar(); 

            switch (currentChar) {
                case '<':
                case '>':
                    t.setType("Operator");
                    if (nextChar == '=') {
                        t.setValue(String.valueOf(currentChar) + nextChar); 
                        nextChar = readChar(); // Consume the '='
                    } else {
                        t.setValue(String.valueOf(currentChar));
                        // nextChar holds the lookahead, correct for next token
                    }
                    return t;

                case '!':
                    // ! is Operator (as fixed previously), != and !| are Operator
                    if (nextChar == '=' || nextChar == '|') {
                        t.setType("Operator"); 
                        t.setValue(String.valueOf(currentChar) + nextChar); 
                        nextChar = readChar(); // Consume lookahead character
                    } else {
                        t.setType("Operator"); 
                        t.setValue(String.valueOf(currentChar));
                        // nextChar holds the lookahead, correct for next token
                    }
                    return t;

                case '*': 
                    // Fix for doubleStarSymbolIsOperatorOperator() test: Treat ** as two separate tokens.
                    t.setType("Operator");
                    if (nextChar == '*') {
                        t.setValue(String.valueOf(currentChar)); // Return the first *
                        // DO NOT consume the second '*', let the next call handle it.
                    } else {
                        t.setValue(String.valueOf(currentChar));
                        // nextChar holds the lookahead, correct for next token
                    }
                    return t;
                    
                case '=':
                    // = is Other, == is Operator
                    if (nextChar == '=') {
                        t.setType("Operator");
                        t.setValue("=="); 
                        nextChar = readChar(); // Consume '='
                    } else {
                        t.setType("Other"); // Single =
                        t.setValue(String.valueOf(currentChar));
                        // nextChar holds the lookahead, correct for next token
                    }
                    return t;

                case ':':
                    // : is Other, := is Operator
                    if (nextChar == '=') {
                        t.setType("Operator");
                        t.setValue(":="); 
                        nextChar = readChar(); // Consume '='
                    } else {
                        t.setType("Other"); // Single :
                        t.setValue(String.valueOf(currentChar));
                        // nextChar holds the lookahead, correct for next token
                    }
                    return t;

                case '|':
                    // | is Other, || is Operator
                    if (nextChar == '|') {
                        t.setType("Operator");
                        t.setValue("||");
                        nextChar = readChar(); // Consume '|'
                    } else {
                        t.setType("Other"); // Single |
                        t.setValue(String.valueOf(currentChar));
                        // nextChar holds the lookahead, correct for next token
                    }
                    return t;

                case '&':
                    // & is Other, && is Operator
                    if (nextChar == '&') {
                        t.setType("Operator");
                        t.setValue("&&");
                        nextChar = readChar(); // Consume '&'
                    } else {
                        t.setType("Other"); // Single &
                        t.setValue(String.valueOf(currentChar));
                        // nextChar holds the lookahead, correct for next token
                    }
                    return t;
                
                default:
                    // Should not happen if isLookaheadOperator is defined correctly
                    t.setType("Other");
                    t.setValue(String.valueOf(currentChar));
                    // nextChar holds the lookahead, correct for next token
                    return t;
            }
        }

        // 5. Identifier / Keyword / Boolean Literal
        if (isLetter(nextChar)) {
            t.setType("Identifier");
            while (isLetter(nextChar) || isDigit(nextChar)) {
                t.setValue(t.getValue() + nextChar);
                nextChar = readChar();
            }
            
            String value = t.getValue();

            if (value.equals("True") || value.equals("False")) {
                 t.setType("Literal");
            } else if (isKeyword(value)) {
                t.setType("Keyword");
            }
            
            return t; 
        }

        // 6. Integer Literal
        if (isDigit(nextChar)) {
            t.setType("Literal");
            while (isDigit(nextChar)) {
                t.setValue(t.getValue() + nextChar);
                nextChar = readChar();
            }

            return t;
        }

        // 7. Final catch-all for single "Other" characters (If it gets here, it's an unclassified non-whitespace character)
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

    // New helper to classify operators that require lookahead
    private boolean isLookaheadOperator(char c) {
        return (c == '*' || c == '<' || c == '>' || c == '=' || c == '!' || c == '|' || c == '&' || c == ':');
    }
    
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

    // This is now only used in the division logic, since the single-character operators are separated.
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
