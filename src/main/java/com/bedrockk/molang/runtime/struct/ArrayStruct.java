package com.bedrockk.molang.runtime.struct;

import com.bedrockk.molang.runtime.MoParams;
import com.bedrockk.molang.runtime.value.MoValue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Map;

public class ArrayStruct extends VariableStruct {

    public ArrayStruct() {}

    public ArrayStruct(Map<String, MoValue> map) {
        super(map);
    }

    @Override
    public void set(Deque<String> names, MoValue value) {
        // Last part always must be a integer
        ArrayList<String> namesList = new ArrayList<>();
        int numberIndex = names.size() - 1;
        int i = 0;
        for (String name : names) {
            if (i == numberIndex) {
                namesList.add(String.valueOf(Integer.parseInt(name)));
            } else {
                namesList.add(name);
            }
            i++;
        }

        super.set(new ArrayDeque<>(namesList), value);
    }
}
