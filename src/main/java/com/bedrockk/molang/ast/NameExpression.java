package com.bedrockk.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.MoValue;
import lombok.Value;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Value
public class NameExpression extends StringHolder implements Expression {

    ArrayList<String> names;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        Deque<String> names = new ArrayDeque<>(getNames());
        return environment.getValue(names);
    }

    @Override
    public void assign(MoScope scope, MoLangEnvironment environment, MoValue value) {
        Deque<String> names = new ArrayDeque<>(getNames());
        environment.setValue(names, value);
    }
}
