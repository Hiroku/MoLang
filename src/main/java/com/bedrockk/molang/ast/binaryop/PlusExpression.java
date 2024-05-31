package com.bedrockk.molang.ast.binaryop;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.ast.BinaryOpExpression;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;
import com.bedrockk.molang.runtime.value.StringValue;

public class PlusExpression extends BinaryOpExpression {

    public PlusExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public String getSigil() {
        return "+";
    }

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        MoValue leftValue = left.evaluate(scope, environment);
        MoValue rightValue = right.evaluate(scope, environment);

        if (leftValue instanceof StringValue || rightValue instanceof StringValue) {
            return new StringValue(leftValue.asString() + rightValue.asString());
        } else {
            return new DoubleValue(leftValue.asDouble() + rightValue.asDouble());
        }
    }
}
