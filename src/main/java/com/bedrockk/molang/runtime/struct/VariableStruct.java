package com.bedrockk.molang.runtime.struct;

import com.bedrockk.molang.runtime.MoParams;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class VariableStruct implements MoStruct {
    private final Map<String, MoValue> map;

    public VariableStruct() {
        this.map = new HashMap<>();
    }

    @Override
    public void set(Iterator<String> names, MoValue value) {
        String main = names.next();

        if (names.hasNext() && main != null) {
            Object struct = map.get(main);

            if (!(struct instanceof MoStruct)) {
                struct = new VariableStruct();
            }

            ((MoStruct) struct).set(names, value);

            map.put(main, (MoStruct) struct);
        } else {
            map.put(main, value);
        }
    }

    public void setDirectly(String name, MoValue value) {
        this.map.put(name, value);
    }

    @Override
    public MoValue get(Iterator<String> names, MoParams params) {
        String main = names.next();

        if (names.hasNext() && main != null) {
            Object struct = map.get(main);

            if (struct instanceof MoStruct) {
                return ((MoStruct) struct).get(names, MoParams.EMPTY);
            }
        }

        return map.getOrDefault(main, new DoubleValue(0.0));
    }

    @Override
    public void clear() {
        map.clear();
    }
}
