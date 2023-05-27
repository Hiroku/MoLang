package com.bedrockk.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.MoValue;
import lombok.Value;

import java.util.*;

@Value
public class NameExpression extends StringHolder implements Expression {

    ArrayList<String> names;

    @Override
    public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
        return environment.getValue(getNames().iterator());
    }

    @Override
    public void assign(MoScope scope, MoLangEnvironment environment, MoValue value) {
        environment.setValue(getNames().iterator(), value);
    }
}
