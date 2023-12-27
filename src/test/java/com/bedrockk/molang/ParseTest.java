package com.bedrockk.molang;

import com.bedrockk.molang.runtime.MoLangRuntime;
import com.bedrockk.molang.runtime.MoScope;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@DisplayName("Parse Tests")
public class ParseTest {

    @Test
    @DisplayName("Parse File 1")
    public void parse1() throws IOException {
        Assertions.assertDoesNotThrow(() -> MoLang.parse(getClass().getClassLoader().getResourceAsStream("expr1.txt")));
    }

    @Test
    @DisplayName("Parse File ")
    public void parse2() throws IOException {
        Assertions.assertDoesNotThrow(() -> MoLang.parse(getClass().getClassLoader().getResourceAsStream("expr2.txt")));
    }

    @Test
    @DisplayName("Parse File 3")
    public void parse3() throws IOException {
        Assertions.assertDoesNotThrow(() -> MoLang.parse(getClass().getClassLoader().getResourceAsStream("expr3.txt")));
    }

    @Test
    @DisplayName("Parse File 4")
    public void parse4() throws IOException {
        Assertions.assertDoesNotThrow(() -> MoLang.parse(getClass().getClassLoader().getResourceAsStream("expr4.txt")));
    }

    @Test
    public void parse5() {
        MoLangRuntime runtime = new MoLangRuntime();
        MoLang.createParser("temp.vari[0] = 1").parseExpression().evaluate(new MoScope(), runtime.getEnvironment());
        System.out.println("Henlo");
    }
}
