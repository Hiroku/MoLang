package com.bedrockk.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoParams;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.MoValue;
import lombok.Value;

import java.util.*;
import java.util.stream.Collectors;

@Value
public class FuncCallExpression extends StringHolder implements Expression {

    Expression name;
    Expression[] args;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        List<Expression> params = Arrays.asList(args);
        ArrayList<String> names = new ArrayList<>();
        if (name instanceof NameExpression) {
            names.addAll(((NameExpression) name).getNames());
        } else {
            Collections.addAll(names, name.evaluate(scope, environment).asString().split("\\."));
        }

        ArrayList<MoValue> paramsParsed = new ArrayList<>(params.size());
        for (Expression param : params) {
            paramsParsed.add(param.evaluate(scope, environment));
        }

        return environment.getValue(names.iterator(), new MoParams(environment, paramsParsed));
    }
}
