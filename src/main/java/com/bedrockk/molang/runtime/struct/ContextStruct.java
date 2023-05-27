package com.bedrockk.molang.runtime.struct;

import com.bedrockk.molang.runtime.value.MoValue;

import java.util.Deque;
import java.util.Iterator;
import java.util.Map;

public class ContextStruct extends VariableStruct {

    public ContextStruct() {}

    public ContextStruct(Map<String, MoValue> map) {
        super(map);
    }

    @Override
    public void set(Iterator<String> names, MoValue value) {
        throw new RuntimeException("Tried to set a value in read-only context struct");
    }
}
