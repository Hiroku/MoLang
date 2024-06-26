package com.bedrockk.molang.parser.parselet;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.ast.ContinueExpression;
import com.bedrockk.molang.parser.MoLangParser;
import com.bedrockk.molang.parser.PrefixParselet;
import com.bedrockk.molang.parser.tokenizer.Token;

public class ContinueParselet implements PrefixParselet {

    @Override
    public Expression parse(MoLangParser parser, Token token) {
        return new ContinueExpression();
    }
}
