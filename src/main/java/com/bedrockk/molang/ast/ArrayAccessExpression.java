package com.bedrockk.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.MoValue;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collections;

@Value
public class ArrayAccessExpression extends StringHolder implements Expression {

    Expression array;
    Expression index;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        ArrayList<String> names = new ArrayList<>();
        if (array instanceof NameExpression) {
            names.addAll(((NameExpression) array).getNames());
        } else {
            Collections.addAll(names, array.evaluate(scope, environment).asString().split("\\."));
        }

        names.add(String.valueOf((int) index.evaluate(scope, environment).asDouble()));

        return environment.getValue(names.iterator());
    }

    @Override
    public void assign(MoScope scope, MoLangEnvironment environment, MoValue value) {
        ArrayList<String> names = new ArrayList<>();
        if (array instanceof NameExpression) {
            names.addAll(((NameExpression) array).getNames());
        } else {
            Collections.addAll(names, array.evaluate(scope, environment).asString().split("\\."));
        }

        names.add(String.valueOf((int) index.evaluate(scope, environment).asDouble()));

        environment.setValue(names.iterator(), value);
    }
}
