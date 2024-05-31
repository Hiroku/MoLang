package com.bedrockk.molang.parser.parselet;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.ast.StringExpression;
import com.bedrockk.molang.parser.MoLangParser;
import com.bedrockk.molang.parser.PrefixParselet;
import com.bedrockk.molang.parser.tokenizer.Token;
import com.bedrockk.molang.runtime.value.StringValue;

public class StringParselet implements PrefixParselet {

    @Override
    public Expression parse(MoLangParser parser, Token token) {
        return new StringExpression(new StringValue(token.getText()));
    }
}
