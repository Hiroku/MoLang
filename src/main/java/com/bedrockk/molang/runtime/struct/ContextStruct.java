package com.bedrockk.molang.runtime.struct;

import com.bedrockk.molang.runtime.value.MoValue;

import java.util.Iterator;
import java.util.Map;

public class ContextStruct extends VariableStruct {

    public ContextStruct() {}

    public ContextStruct(Map<String, MoValue> map) {
        super(map);
    }

    @Override
    public void set(Iterator<String> names, MoValue value) {
        String main = names.next();

        if (names.hasNext() && main != null) {
            MoValue struct = map.get(main);
            if (struct != null) {
                if (!(struct instanceof MoStruct)) {
                    throw new RuntimeException("Cannot set a value in context struct");
                } else {
                    ((MoStruct) struct).set(names, value);
                }
            } else {
                throw new RuntimeException("Cannot set a value in context struct");
            }
        } else {
            throw new RuntimeException("Cannot set a value in context struct");
        }
    }
}
