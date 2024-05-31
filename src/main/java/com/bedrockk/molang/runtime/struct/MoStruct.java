package com.bedrockk.molang.runtime.struct;

import com.bedrockk.molang.runtime.MoParams;
import com.bedrockk.molang.runtime.value.MoValue;

import java.util.Iterator;

public interface MoStruct extends MoValue {

    void set(Iterator<String> keys, MoValue value);

    MoValue get(Iterator<String> names, MoParams params);

    void clear();

    @Override
    default MoStruct value() {
        return this;
    }
}
