package com.bedrockk.molang.ast.binaryop;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.ast.BinaryOpExpression;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;

import java.util.List;

public class CoalesceExpression extends BinaryOpExpression {

    public CoalesceExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public String getSigil() {
        return "??";
    }

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        MoValue evalLeft = left.evaluate(scope, environment);
        String leftString = evalLeft.asString();
        List<String> leftNames = List.of(leftString.split("\\."));

        MoValue value = environment.getValue(leftNames.iterator());

        if (value == null || value.equals(DoubleValue.ZERO)) {
            return right.evaluate(scope, environment);
        } else {
            return evalLeft;
        }
    }
}
