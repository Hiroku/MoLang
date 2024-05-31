package com.bedrockk.molang.runtime;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.runtime.struct.ArrayStruct;
import com.bedrockk.molang.runtime.struct.ContextStruct;
import com.bedrockk.molang.runtime.struct.QueryStruct;
import com.bedrockk.molang.runtime.struct.VariableStruct;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoLangRuntime {
    private final MoLangEnvironment environment = new MoLangEnvironment();
    private final Map<String, MoValue> noContext = new HashMap<>();

    public MoLangRuntime() {
        environment.setStruct("math", MoLangMath.LIBRARY);
        environment.setStruct("temp", new VariableStruct());
        environment.setStruct("variable", new VariableStruct());
        environment.setStruct("array", new ArrayStruct());
        environment.setStruct("query", new QueryStruct(new HashMap<>()));
    }

    public MoValue execute(Expression expression) {
        return execute(expression, noContext);
    }

    public MoValue execute(Expression expression, Map<String, MoValue> context) {
        var scope = new MoScope();
        if (!context.isEmpty()) {
            environment.setStruct("context", new ContextStruct(context));
        }
        MoValue result = expression.evaluate(scope, environment);
        environment.temp.clear();
        environment.removeStruct("context");
        return scope.getReturnValue() != null ? scope.getReturnValue() : result;
    }

    public MoValue execute(List<Expression> expressions) {
        return execute(expressions, new HashMap<>());
    }

    public MoValue execute(List<Expression> expressions, Map<String, MoValue> context) {
//        var traverser = new ExprTraverser();
//        traverser.getVisitors().add(new ExprConnectingVisitor());
//        traverser.traverse(expressions);

        if (!context.isEmpty()) {
            environment.setStruct("context", new ContextStruct(context));
        }

        MoValue result = DoubleValue.ZERO;
        var scope = new MoScope();

        if (expressions.size() == 1) {
            result = expressions.get(0).evaluate(scope, environment);
            environment.temp.clear();
            environment.removeStruct("context");
            return scope.getReturnValue() != null ? scope.getReturnValue() : result;
        }

        for (Expression expression : expressions) {
            if (scope.getReturnValue() != null) {
                break;
            }
            result = expression.evaluate(scope, environment);
        }

        environment.temp.clear();
        environment.removeStruct("context");

        return scope.getReturnValue() != null ? scope.getReturnValue() : result;
    }

    public MoLangEnvironment getEnvironment() {
        return environment;
    }
}
