package com.bedrockk.molang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@DisplayName("Evaluator Test")
public class EvaluatorTest {

    private void eval(String file, double expected, boolean round) throws IOException {
        var parsed = MoLang.parse(getClass().getClassLoader().getResourceAsStream(file));
        var runtime = MoLang.createRuntime();
        var actual = runtime.execute(parsed).asDouble();

        Assertions.assertEquals(round ? Math.round(expected) : expected, round ? Math.round(actual) : actual);
    }

    @Test
    public void testEval3() throws IOException {
        eval("expr3.txt", (213 + 2 / 0.5 + 5 + 2 * 3), true);
    }

    @Test
    public void testEval4() throws IOException {
        eval("expr4.txt", (213 + 2 / 0.5 + 5 + 2 * 3) + 310.5 + (10 * Math.cos(270 * Math.PI / 180)) + 100, true);
    }

    @Test
    public void testEval5() throws IOException {
        eval("expr5.txt", 0, true);
    }

    @Test
    public void testEval6() throws IOException {
        eval("expr6.txt", 2, true);
    }

    @Test
    public void testEval7() throws IOException {
        eval("expr7.txt", 1, true);
//        var parsed = MoLang.parse(getClass().getClassLoader().getResourceAsStream("expr7.txt"));
//        var runtime = MoLang.createRuntime();
//        var actual = runtime.execute(parsed).asDouble()
//
//        Assertions.assertEquals(round ? Math.round(expected) : expected, round ? Math.round(actual) : actual);
    }
}
