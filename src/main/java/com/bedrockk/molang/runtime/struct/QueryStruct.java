package com.bedrockk.molang.runtime.struct;

import com.bedrockk.molang.runtime.MoParams;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public class QueryStruct implements MoStruct {

    public final HashMap<String, Function<MoParams, Object>> functions;

    @Override
    public MoValue get(Iterator<String> names, MoParams params) {
        String key = names.next();
        Function<MoParams, Object> func = functions.get(key);
        MoParams currentParams = names.hasNext() ? MoParams.EMPTY : params;
        if (func != null) {
            Object result = func.apply(currentParams);
            if (result instanceof MoStruct && names.hasNext()) {
                return ((MoStruct)result).get(names, params);
            } else {
                return MoValue.of(result);
            }
        }
        return DoubleValue.ZERO;
    }

    @Override
    public void set(Iterator<String> names, MoValue value) {
        throw new RuntimeException("Cannot set a value in query struct");
    }

    @Override
    public void clear() {
        functions.clear();
    }

    public QueryStruct addFunction(String name, Function<MoParams, Object> func) {
        functions.put(name, func);
        return this;
    }
}
