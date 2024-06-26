package com.bedrockk.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;
import lombok.Value;

@Value
public class LoopExpression extends StringHolder implements Expression {

    Expression count;
    Expression body;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        int loop = (int) count.evaluate(scope, environment).asDouble();
        MoScope subScope = new MoScope();

        while (loop > 0) {
            body.evaluate(subScope, environment);
            loop--;

            if (subScope.getReturnValue() != null) {
                return subScope.getReturnValue();
            } else if (subScope.isBreak()) {
                break;
            }
        }

        return DoubleValue.ZERO;
    }
}
