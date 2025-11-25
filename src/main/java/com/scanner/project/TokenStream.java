Run classroom-resources/autograding-command-grader@v1
  with:
    test-name: Scanner Test
    command: gradle test
    timeout: 10

Welcome to Gradle 9.2.0!

Here are the highlights of this release:
 - Windows ARM support
 - Improved publishing APIs
 - Better guidance for dependency verification failures

For more details see https://docs.gradle.org/9.2.0/release-notes.html

Starting a Gradle Daemon (subsequent builds will be faster)
> Task :compileJava
> Task :processResources NO-SOURCE
> Task :classes
> Task :compileTestJava
> Task :processTestResources
> Task :testClasses

> Task :test FAILED

ScannerTest > num22IsLiteral() PASSED

ScannerTest > slashIsOther() PASSED

ScannerTest > multiplicationSignIsOperator() PASSED

ScannerTest > secretTest1() FAILED
    org.opentest4j.AssertionFailedError at ScannerTest.java:630

ScannerTest > secretTest2() PASSED

ScannerTest > secretTest3() FAILED
    org.opentest4j.AssertionFailedError at ScannerTest.java:648

ScannerTest > secretTest4() PASSED

ScannerTest > secretTest5() FAILED
    org.opentest4j.AssertionFailedError at ScannerTest.java:664

ScannerTest > secretTest6() FAILED
    org.opentest4j.AssertionFailedError at ScannerTest.java:673

ScannerTest > secretTest7() PASSED

ScannerTest > secretTest8() PASSED

ScannerTest > secretTest9() FAILED
    org.opentest4j.AssertionFailedError at ScannerTest.java:701

ScannerTest > num1IsLiteral() PASSED

ScannerTest > leftSquareBracketSymbolIsOther() PASSED

ScannerTest > aIsIdentifier() PASSED

ScannerTest > ifIsKeyword() PASSED

ScannerTest > num21IsLiteral() PASSED

ScannerTest > num0IsLiteral() PASSED

ScannerTest > num20IsLiteral() PASSED

ScannerTest > num19IsLiteral() PASSED

ScannerTest > minusSignIsOperator() PASSED

ScannerTest > num256IsLiteral() PASSED

ScannerTest > num18IsLiteral() PASSED

ScannerTest > mainIsKeyword() PASSED

ScannerTest > exclamationPointEqualsSignIsOperator() PASSED

ScannerTest > ampersandIsOther() PASSED

ScannerTest > num54932IsLiteral() PASSED

ScannerTest > num5823943294892IsLiteral() PASSED

ScannerTest > num17IsLiteral() PASSED

ScannerTest > doubleAmpersandIsOperator() PASSED

ScannerTest > doubleStarSymbolIsOperatorOperator() PASSED

ScannerTest > trueIsIdentifier() PASSED

ScannerTest > num5838323IsLiteral() PASSED

ScannerTest > num16IsLiteral() PASSED

ScannerTest > equalsSignIsOther() PASSED

ScannerTest > elseIsKeyword() PASSED

ScannerTest > counterIsIdentifier() PASSED

ScannerTest > doubleEqualsSignIsOperator() PASSED

ScannerTest > plusSignIsOperator() PASSED

ScannerTest > num15IsLiteral() PASSED

ScannerTest > lessThanSignIsOperator() PASSED

ScannerTest > doIsIdentifier() PASSED

ScannerTest > num14IsLiteral() PASSED

ScannerTest > num342894252085IsLiteral() PASSED

ScannerTest > colonEqualsSignIsOperator() PASSED

ScannerTest > num13IsLiteral() PASSED

ScannerTest > commaIsSeparator() PASSED

ScannerTest > greaterThanSignEqualsSignIsOperator() PASSED

ScannerTest > voidIsIdentifier() PASSED

ScannerTest > num12IsLiteral() PASSED

ScannerTest > divisionSignIsOperator() PASSED

ScannerTest > num100IsLiteral() PASSED

ScannerTest > num1doubleEqualsSignNum2IsLiteralOperatorLiteral() PASSED

ScannerTest > num11IsLiteral() PASSED

ScannerTest > xIsIdentifier() PASSED

ScannerTest > greaterThanSignIsOperator() PASSED

ScannerTest > num393920582IsLiteral() PASSED

ScannerTest > num10IsLiteral() PASSED

ScannerTest > FalseIsLiteral() PASSED

ScannerTest > num595686866830384IsLiteral() PASSED

ScannerTest > leftBracketIsSeparator() PASSED

ScannerTest > pipeSymbolIsOther() PASSED

ScannerTest > rightSquareBracketIsOther() PASSED

ScannerTest > num44820105839845032IsLiteral() PASSED

ScannerTest > aaIsIdentifier() PASSED

ScannerTest > num9IsLiteral() PASSED

ScannerTest > num30IsLiteral() PASSED

ScannerTest > num58834324234IsLiteral() PASSED

ScannerTest > num29IsLiteral() PASSED

ScannerTest > num8IsLiteral() PASSED

ScannerTest > num6594IsLiteral() PASSED

ScannerTest > num28IsLiteral() PASSED

ScannerTest > secretTest10() FAILED
    org.opentest4j.AssertionFailedError at ScannerTest.java:708

ScannerTest > TRUEIsIdentifier() PASSED

ScannerTest > semicolonIsSeparator() PASSED

ScannerTest > falseIsIdentifier() PASSED

109 tests completed, 10 failed

ScannerTest > doubleVerticalBarIsOperator() PASSED

ScannerTest > num543922IsLiteral() PASSED

ScannerTest > num7IsLiteral() PASSED

ScannerTest > a3IsIdentifier() PASSED

ScannerTest > num27IsLiteral() PASSED

ScannerTest > num432205294320580285IsLiteral() PASSED

ScannerTest > num6IsLiteral() PASSED

ScannerTest > exclamationPointBarIsOperator() FAILED
    org.opentest4j.AssertionFailedError at ScannerTest.java:104

ScannerTest > num26IsLiteral() PASSED

ScannerTest > X3IsIdentifier() PASSED

ScannerTest > capitalXLowercaseYNum1IsIdentifier() FAILED
    org.opentest4j.AssertionFailedError at ScannerTest.java:617

ScannerTest > integerIsKeyword() PASSED

ScannerTest > num5IsLiteral() PASSED

ScannerTest > num48329483248322IsLiteral() PASSED

ScannerTest > num25IsLiteral() PASSED

ScannerTest > num2PeriodNum5IsOtherLiteral() FAILED
    org.opentest4j.AssertionFailedError at ScannerTest.java:611

ScannerTest > whileIsKeyword() PASSED

ScannerTest > colonIsOther() PASSED

ScannerTest > num4IsLiteral() PASSED

ScannerTest > num4823980580280508IsLiteral() PASSED

ScannerTest > boolIsKeyword() PASSED

ScannerTest > num4894309432IsLiteral() PASSED

ScannerTest > num85403345IsLiteral() PASSED

ScannerTest > num24IsLiteral() PASSED

ScannerTest > num3IsLiteral() PASSED

ScannerTest > negativeSignNum5IsOperatorLiteral() FAILED
    org.opentest4j.AssertionFailedError at ScannerTest.java:623

ScannerTest > rightBracketIsSeparator() PASSED

ScannerTest > num23IsLiteral() PASSED

ScannerTest > lessThanSignEqualsSignIsOperator() PASSED

ScannerTest > rightParenthesisIsSeparator() PASSED

ScannerTest > num2IsLiteral() PASSED

ScannerTest > atSignIsOther() PASSED

ScannerTest > TrueIsLiteral() PASSED

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':test'.
> There were failing tests. See the report at: file:///home/runner/work/kay-scanner-2024-ishratjarin42/kay-scanner-2024-ishratjarin42/build/reports/tests/test/index.html

* Try:
> Run with --scan to generate a Build Scan (powered by Develocity).

BUILD FAILED in 34s
4 actionable tasks: 4 executed
