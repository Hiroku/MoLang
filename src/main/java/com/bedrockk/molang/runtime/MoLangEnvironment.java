package com.bedrockk.molang.runtime;

import com.bedrockk.molang.runtime.struct.MoStruct;
import com.bedrockk.molang.runtime.struct.VariableStruct;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;
import lombok.Value;

import java.util.*;

@Value
public class MoLangEnvironment implements MoValue {

    Map<String, MoStruct> structs = new HashMap<>();

    public MoValue getValue(Deque<String> names) {
        return getValue(names, MoParams.EMPTY);
    }

    public MoValue getValue(Deque<String> names, MoParams params) {
        String main = names.poll();

        MoStruct struct = structs.get(main);
        if (struct != null) {
            return struct.get(names, params);
        }
        return new DoubleValue(0.0);
    }

    public void setValue(Deque<String> names, MoValue value) {
        String main = names.poll();

        MoStruct struct = structs.get(main);
        if (struct != null) {
            struct.set(names, value);
        }
    }

    public void setSimpleVariable(String name, MoValue value) {
        ((VariableStruct) structs.get("variable")).setDirectly(name, value);
    }

    @Override
    public Object value() {
        return this;
    }
}
