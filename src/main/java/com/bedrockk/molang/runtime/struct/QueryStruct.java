package com.bedrockk.molang.runtime.struct;

import com.bedrockk.molang.runtime.MoParams;
import com.bedrockk.molang.runtime.value.MoValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Deque;
import java.util.Map;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public class QueryStruct implements MoStruct {

    private final Map<String, Function<MoParams, Object>> functions;

    @Override
    public MoValue get(Deque<String> names, MoParams params) {
        String key = names.poll();
        Function<MoParams, Object> func = functions.get(key);
        if (func != null) {
            return MoValue.of(func.apply(params));
        }
        return null;
    }

    @Override
    public void set(Deque<String> names, MoValue value) {
        throw new RuntimeException("Cannot set a value in query struct");
    }

    @Override
    public void clear() {
        functions.clear();
    }
}
