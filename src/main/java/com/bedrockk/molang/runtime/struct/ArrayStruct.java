package com.bedrockk.molang.runtime.struct;

import com.bedrockk.molang.runtime.value.MoValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ArrayStruct extends VariableStruct {

    public ArrayStruct() {}

    public ArrayStruct(Map<String, MoValue> map) {
        super(map);
    }

    @Override
    public void set(Iterator<String> names, MoValue value) {
        // Last part always must be a integer
        ArrayList<String> namesList = new ArrayList<>();
        while (names.hasNext()) {
            String name = names.next();
            if (!names.hasNext()) {
                namesList.add(String.valueOf(Integer.parseInt(name)));
            } else {
                namesList.add(name);
            }
        }

        super.set(namesList.iterator(), value);
    }
}
