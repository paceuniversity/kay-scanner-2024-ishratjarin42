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
        if (nextChar == '/') {
            char tempChar = nextChar;
            nextChar = readChar();
            if (nextChar == '/') {
                // Skip comment until end of line
                while (!isEof && !isEndOfLine(nextChar)) {
                    nextChar = readChar();
                }
                if (!isEof) {
                    nextChar = readChar(); // Consume the newline/end-of-line
                }
                // Rerun nextToken after skipping comment
                return nextToken();
            } else {
                // Single '/' is Division Operator
                t.setType("Operator");
                t.setValue(String.valueOf(tempChar));
                // nextChar is already the lookahead, so we just return.
                return t;
            }
        }
        
        // 2. Separators
        if (isSeparator(nextChar)) {
            t.setType("Separator");
            t.setValue(String.valueOf(nextChar));
            nextChar = readChar(); // Consume and load next
            return t;
        }

        // 3. Simple Single-Character Operators (FIX for -5 failure)
        // These are handled explicitly to avoid the lookahead logic stream skip.
        if (nextChar == '+' || nextChar == '-' || nextChar == '%' || nextChar == '^' || nextChar == '~') {
            t.setType("Operator");
            t.setValue(String.valueOf(nextChar));
            nextChar = readChar(); // Consume and load next
            return t;
        }

        // 4. Lookahead/Complex Operators and Others (Two-character tokens, or single tokens that need context)
        if (isLookaheadChar(nextChar)) {
            char currentChar = nextChar;
            
            // Advance nextChar for the lookahead before the switch starts
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
                        // nextChar holds the lookahead
                    }
                    return t;

                case '!':
                    // ! is Operator, != and !| are Operator
                    if (nextChar == '=' || nextChar == '|') {
                        t.setType("Operator"); 
                        t.setValue(String.valueOf(currentChar) + nextChar); 
                        nextChar = readChar(); // Consume lookahead character
                    } else {
                        t.setType("Operator"); 
                        t.setValue(String.valueOf(currentChar));
                        // nextChar holds the lookahead
                    }
                    return t;

                case '*': 
                    t.setType("Operator");
                    if (nextChar == '*') {
                        t.setValue(String.valueOf(currentChar)); // Return the first *
                        // nextChar holds the second '*'
                    } else {
                        t.setValue(String.valueOf(currentChar));
                        // nextChar holds the lookahead
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
                        // nextChar holds the lookahead
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
                        // nextChar holds the lookahead
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
                        // nextChar holds the lookahead
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
                        // nextChar holds the lookahead
                    }
                    return t;
                
                case '[':
                case ']':
                case '@':
                case '.':
                    // Explicit Single-Character Other Tokens (FIX for num2PeriodNum5IsOtherLiteral)
                    t.setType("Other");
                    t.setValue(String.valueOf(currentChar));
                    // nextChar holds the lookahead
                    return t;
                
                default:
                    // This block should ideally not be reached if isLookaheadChar is accurate
                    t.setType("Other");
                    t.setValue(String.valueOf(currentChar));
                    // nextChar holds the lookahead
                    return t;
            }
        }

        // 5. Identifier / Keyword / Boolean Literal (FIX for capitalXLowercaseYNum1IsIdentifier)
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

        // 7. Final catch-all for single "Other" characters 
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

    private boolean isLookaheadChar(char c) {
        return (c == '*' || c == '<' || c == '>' || c == '=' || c == '!' || c == '|' || c == '&' || c == ':' ||
                c == '[' || c == ']' || c == '@' || c == '.');
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

    // This helper is no longer strictly necessary but kept for completeness
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
