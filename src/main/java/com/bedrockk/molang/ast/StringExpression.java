package com.bedrockk.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.MoValue;
import com.bedrockk.molang.runtime.value.StringValue;
import lombok.Value;

@Value
public class StringExpression extends StringHolder implements Expression {

    StringValue string;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        return string;
    }
}
