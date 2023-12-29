package com.bedrockk.molang.ast.binaryop;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.ast.BinaryOpExpression;
import com.bedrockk.molang.ast.StringExpression;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;

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
        if (left instanceof StringExpression || right instanceof StringExpression) {
            return new DoubleValue(left.evaluate(scope, environment).asString() + right.evaluate(scope, environment).asString());
        } else {
            return new DoubleValue(left.evaluate(scope, environment).asDouble() + right.evaluate(scope, environment).asDouble());
        }
    }
}
