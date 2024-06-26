package com.bedrockk.molang.parser.parselet;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.ast.ThisExpression;
import com.bedrockk.molang.parser.MoLangParser;
import com.bedrockk.molang.parser.PrefixParselet;
import com.bedrockk.molang.parser.tokenizer.Token;

public class ThisParselet implements PrefixParselet {

    @Override
    public Expression parse(MoLangParser parser, Token token) {
        return new ThisExpression();
    }
}
