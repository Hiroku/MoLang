package com.bedrockk.molang.parser.expression;

import com.bedrockk.molang.parser.Expression;
import com.bedrockk.molang.util.Util;
import lombok.Value;

@Value
public class FuncCallExpression implements Expression {

    Expression name;
    Expression[] args;
}
