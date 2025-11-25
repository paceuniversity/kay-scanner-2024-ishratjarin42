package com.scanner.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class ScannerTest {

    private static final String file1 = "src/test/resources/test.kay";

    /*
     * sets the TokenStream to the correct line of the input file
     */
    private TokenStream setup(int currLine) {
        TokenStream tks = new TokenStream(file1);
        for (int i = 0; i < currLine; i++) {
            tks.nextToken();
        }

        return tks;
    }
    
    @Test
    public void boolIsKeyword() {
        TokenStream tks = setup(0);
        assertEquals("Keyword", tks.nextToken().getType());
    }

    @Test
    public void elseIsKeyword() {
        TokenStream tks = setup(1);
        assertEquals("Keyword", tks.nextToken().getType());
    }

    @Test
    public void ifIsKeyword() {
        TokenStream tks = setup(2);
        assertEquals("Keyword", tks.nextToken().getType());
    }

    @Test
    public void integerIsKeyword() {
        TokenStream tks = setup(3);
        assertEquals("Keyword", tks.nextToken().getType());
    }

    @Test
    public void mainIsKeyword() {
        TokenStream tks = setup(4);
        assertEquals("Keyword", tks.nextToken().getType());
    }

    @Test
    public void whileIsKeyword() {
        TokenStream tks = setup(5);
        assertEquals("Keyword", tks.nextToken().getType());
    }

    public void leftParenthesisIsSeparator() {
        TokenStream tks = setup(6);
        assertEquals("Separator", tks.nextToken().getType());
    }

    @Test
    public void rightParenthesisIsSeparator() {
        TokenStream tks = setup(7);
        assertEquals("Separator", tks.nextToken().getType());
    }

    @Test
    public void leftBracketIsSeparator() {
        TokenStream tks = setup(8);
        assertEquals("Separator", tks.nextToken().getType());
    }

    @Test
    public void rightBracketIsSeparator() {
        TokenStream tks = setup(9);
        assertEquals("Separator", tks.nextToken().getType());
    }

    @Test
    public void semicolonIsSeparator() {
        TokenStream tks = setup(10);
        assertEquals("Separator", tks.nextToken().getType());
    }

    @Test
    public void commaIsSeparator() {
        TokenStream tks = setup(11);
        assertEquals("Separator", tks.nextToken().getType());
    }

    @Test
    public void doubleVerticalBarIsOperator() {
        TokenStream tks = setup(12);
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void exclamationPointBarIsOperator() {
        TokenStream tks = setup(13);
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void doubleAmpersandIsOperator() {
        TokenStream tks = setup(14);
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void exclamationPointEqualsSignIsOperator() {
        TokenStream tks = setup(15);
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void doubleEqualsSignIsOperator() {
        TokenStream tks = setup(16);
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void greaterThanSignEqualsSignIsOperator() {
        TokenStream tks = setup(17);
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void lessThanSignEqualsSignIsOperator() {
        TokenStream tks = setup(18);
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void lessThanSignIsOperator() {
        TokenStream tks = setup(19);
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void greaterThanSignIsOperator() {
        TokenStream tks = setup(20);
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void divisionSignIsOperator() {
        TokenStream tks = setup(21);
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void multiplicationSignIsOperator() {
        TokenStream tks = setup(22);
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void minusSignIsOperator() {
        TokenStream tks = setup(23);
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void plusSignIsOperator() {
        TokenStream tks = setup(24);
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void colonEqualsSignIsOperator() {
        TokenStream tks = setup(25);
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void aIsIdentifier() {
        TokenStream tks = setup(26);
        assertEquals("Identifier", tks.nextToken().getType());
    }

    @Test
    public void aaIsIdentifier() {
        TokenStream tks = setup(27);
        assertEquals("Identifier", tks.nextToken().getType());
    }

    @Test
    public void xIsIdentifier() {
        TokenStream tks = setup(28);
        assertEquals("Identifier", tks.nextToken().getType());
    }

    @Test
    public void counterIsIdentifier() {
        TokenStream tks = setup(29);
        assertEquals("Identifier", tks.nextToken().getType());
    }

    @Test
    public void a3IsIdentifier() {
        TokenStream tks = setup(30);
        assertEquals("Identifier", tks.nextToken().getType());
    }

    @Test
    public void doIsIdentifier() {
        TokenStream tks = setup(31);
        assertEquals("Identifier", tks.nextToken().getType());
    }

    @Test
    public void voidIsIdentifier() {
        TokenStream tks = setup(32);
        assertEquals("Identifier", tks.nextToken().getType());
    }

    @Test
    public void trueIsIdentifier() {
        TokenStream tks = setup(33);
        assertEquals("Identifier", tks.nextToken().getType());
    }

    @Test
    public void falseIsIdentifier() {
        TokenStream tks = setup(34);
        assertEquals("Identifier", tks.nextToken().getType());
    }

    @Test
    public void X3IsIdentifier() {
        TokenStream tks = setup(35);
        assertEquals("Identifier", tks.nextToken().getType());
    }

    @Test
    public void TRUEIsIdentifier() {
        TokenStream tks = setup(36);
        assertEquals("Identifier", tks.nextToken().getType());
    }

    @Test
    public void TrueIsLiteral() {
        TokenStream tks = setup(37);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void FalseIsLiteral() {
        TokenStream tks = setup(38);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num0IsLiteral() {
        TokenStream tks = setup(39);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num1IsLiteral() {
        TokenStream tks = setup(40);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num2IsLiteral() {
        TokenStream tks = setup(41);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num3IsLiteral() {
        TokenStream tks = setup(42);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num4IsLiteral() {
        TokenStream tks = setup(43);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num5IsLiteral() {
        TokenStream tks = setup(44);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num6IsLiteral() {
        TokenStream tks = setup(45);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num7IsLiteral() {
        TokenStream tks = setup(46);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num8IsLiteral() {
        TokenStream tks = setup(47);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num9IsLiteral() {
        TokenStream tks = setup(48);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num10IsLiteral() {
        TokenStream tks = setup(49);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num11IsLiteral() {
        TokenStream tks = setup(50);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num12IsLiteral() {
        TokenStream tks = setup(51);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num13IsLiteral() {
        TokenStream tks = setup(52);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num14IsLiteral() {
        TokenStream tks = setup(53);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num15IsLiteral() {
        TokenStream tks = setup(54);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num16IsLiteral() {
        TokenStream tks = setup(55);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num17IsLiteral() {
        TokenStream tks = setup(56);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num18IsLiteral() {
        TokenStream tks = setup(57);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num19IsLiteral() {
        TokenStream tks = setup(58);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num20IsLiteral() {
        TokenStream tks = setup(59);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num21IsLiteral() {
        TokenStream tks = setup(60);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num22IsLiteral() {
        TokenStream tks = setup(61);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num23IsLiteral() {
        TokenStream tks = setup(62);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num24IsLiteral() {
        TokenStream tks = setup(63);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num25IsLiteral() {
        TokenStream tks = setup(64);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num26IsLiteral() {
        TokenStream tks = setup(65);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num27IsLiteral() {
        TokenStream tks = setup(66);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num28IsLiteral() {
        TokenStream tks = setup(67);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num29IsLiteral() {
        TokenStream tks = setup(68);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num30IsLiteral() {
        TokenStream tks = setup(69);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num100IsLiteral() {
        TokenStream tks = setup(70);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num256IsLiteral() {
        TokenStream tks = setup(71);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num6594IsLiteral() {
        TokenStream tks = setup(72);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num54932IsLiteral() {
        TokenStream tks = setup(73);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num543922IsLiteral() {
        TokenStream tks = setup(74);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num5838323IsLiteral() {
        TokenStream tks = setup(75);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num85403345IsLiteral() {
        TokenStream tks = setup(76);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num393920582IsLiteral() {
        TokenStream tks = setup(77);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num4894309432IsLiteral() {
        TokenStream tks = setup(78);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num58834324234IsLiteral() {
        TokenStream tks = setup(79);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num342894252085IsLiteral() {
        TokenStream tks = setup(80);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num5823943294892IsLiteral() {
        TokenStream tks = setup(81);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num48329483248322IsLiteral() {
        TokenStream tks = setup(82);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num595686866830384IsLiteral() {
        TokenStream tks = setup(83);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num4823980580280508IsLiteral() {
        TokenStream tks = setup(84);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num44820105839845032IsLiteral() {
        TokenStream tks = setup(85);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void num432205294320580285IsLiteral() {
        TokenStream tks = setup(86);
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void equalsSignIsOther() {
        TokenStream tks = setup(87);
        assertEquals("Other", tks.nextToken().getType());
    }

    @Test
    public void atSignIsOther() {
        TokenStream tks = setup(88);
        assertEquals("Other", tks.nextToken().getType());
    }

    @Test
    public void ampersandIsOther() {
        TokenStream tks = setup(89);
        assertEquals("Other", tks.nextToken().getType());
    }

    @Test
    public void pipeSymbolIsOther() {
        TokenStream tks = setup(90);
        assertEquals("Other", tks.nextToken().getType());
    }

    @Test
    public void colonIsOther() {
        TokenStream tks = setup(91);
        assertEquals("Other", tks.nextToken().getType());
    }

    @Test
    public void slashIsOther() {
        TokenStream tks = setup(92);
        assertEquals("Other", tks.nextToken().getType());
    }

    @Test
    public void leftSquareBracketSymbolIsOther() {
        TokenStream tks = setup(93);
        assertEquals("Other", tks.nextToken().getType());
    }

    @Test
    public void rightSquareBracketIsOther() {
        TokenStream tks = setup(94);
        assertEquals("Other", tks.nextToken().getType());
    }

    @Test
    public void num1doubleEqualsSignNum2IsLiteralOperatorLiteral() {
        TokenStream tks = setup(95);
        assertEquals("Literal", tks.nextToken().getType());
        assertEquals("Operator", tks.nextToken().getType());
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void doubleStarSymbolIsOperatorOperator() {
        TokenStream tks = setup(98);
        assertEquals("Operator", tks.nextToken().getType());
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void num2PeriodNum5IsOtherLiteral() {
        TokenStream tks = setup(100);
        assertEquals("Other", tks.nextToken().getType());
    }

    @Test
    public void capitalXLowercaseYNum1IsIdentifier() {
        TokenStream tks = setup(101);
        assertEquals("Identifier", tks.nextToken().getType());
    }

    @Test
    public void negativeSignNum5IsOperatorLiteral() {
        TokenStream tks = setup(102);
        assertEquals("Operator", tks.nextToken().getType());
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void secretTest1() {
        TokenStream tks = setup(104);
        assertEquals("Literal", tks.nextToken().getType());
        assertEquals("Operator", tks.nextToken().getType());
        assertEquals("Operator", tks.nextToken().getType());
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void secretTest2() {
        TokenStream tks = setup(108);
        assertEquals("Literal", tks.nextToken().getType());
        assertEquals("Operator", tks.nextToken().getType());
        assertEquals("Literal", tks.nextToken().getType());
    }
    
    @Test
    public void secretTest3() {
        TokenStream tks = setup(111);
        assertEquals("Literal", tks.nextToken().getType());
        assertEquals("Operator", tks.nextToken().getType());
        assertEquals("Operator", tks.nextToken().getType());
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void secretTest4() {
        TokenStream tks = setup(115);
        assertEquals("Literal", tks.nextToken().getType());
        assertEquals("Operator", tks.nextToken().getType());
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void secretTest5() {
        TokenStream tks = setup(118);
        assertEquals("Other", tks.nextToken().getType());
        assertEquals("Operator", tks.nextToken().getType());
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void secretTest6() {
        TokenStream tks = setup(121);
        assertEquals("Literal", tks.nextToken().getType());
        assertEquals("Operator", tks.nextToken().getType());
        assertEquals("Other", tks.nextToken().getType());
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void secretTest7() {
        TokenStream tks = setup(125);
        assertEquals("Literal", tks.nextToken().getType());
        assertEquals("Separator", tks.nextToken().getType());
        assertEquals("Literal", tks.nextToken().getType());
        assertEquals("Separator", tks.nextToken().getType());
        assertEquals("Literal", tks.nextToken().getType());
    }

    @Test
    public void secretTest8() {
        TokenStream tks = setup(130);
        assertEquals("Operator", tks.nextToken().getType());
        assertEquals("Operator", tks.nextToken().getType());
        assertEquals("Identifier", tks.nextToken().getType());
        assertEquals("Operator", tks.nextToken().getType());
        assertEquals("Operator", tks.nextToken().getType());
    }

    @Test
    public void secretTest9() {
        TokenStream tks = setup(135);
        assertEquals("Operator", tks.nextToken().getType());
        assertEquals("Other", tks.nextToken().getType());
    }

    @Test
    public void secretTest10() {
        TokenStream tks = setup(137);
        assertEquals("Identifier", tks.nextToken().getType());
        assertEquals("Keyword", tks.nextToken().getType());
        assertEquals("Separator", tks.nextToken().getType());
        assertEquals("Separator", tks.nextToken().getType());
        assertEquals("Separator", tks.nextToken().getType());
        assertEquals("Identifier", tks.nextToken().getType());
        assertEquals("Identifier", tks.nextToken().getType());
        assertEquals("Separator", tks.nextToken().getType());
        assertEquals("Identifier", tks.nextToken().getType());
        assertEquals("Other", tks.nextToken().getType());
        assertEquals("Literal", tks.nextToken().getType());
        assertEquals("Separator", tks.nextToken().getType());
        assertEquals("Separator", tks.nextToken().getType());
    }
    
}
