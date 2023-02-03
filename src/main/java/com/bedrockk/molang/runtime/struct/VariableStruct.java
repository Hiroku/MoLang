package com.bedrockk.molang.runtime.struct;

import com.bedrockk.molang.runtime.MoParams;
import com.bedrockk.molang.runtime.value.MoValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Getter
@RequiredArgsConstructor
public class VariableStruct implements MoStruct {
    private final Map<String, MoValue> map;

    public VariableStruct() {
        this.map = new HashMap<>();
    }

    @Override
    public void set(Deque<String> names, MoValue value) {
        String main = names.poll();

        if (names.size() > 0 && main != null) {
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
    public MoValue get(Deque<String> names, MoParams params) {
        String main = names.poll();

        if (names.size() > 0 && main != null) {
            Object struct = map.get(main);

            if (struct instanceof MoStruct) {
                return ((MoStruct) struct).get(names, MoParams.EMPTY);
            }
        }

        return map.get(main);
    }

    @Override
    public void clear() {
        map.clear();
    }
}
