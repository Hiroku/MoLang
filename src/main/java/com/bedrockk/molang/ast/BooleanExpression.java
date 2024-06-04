package com.bedrockk.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;
import lombok.Value;

@Value
public class BooleanExpression extends StringHolder implements Expression {

    boolean value;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        return value ? DoubleValue.ONE : DoubleValue.ZERO;
    }
}
