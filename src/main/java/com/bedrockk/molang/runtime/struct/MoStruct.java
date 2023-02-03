package com.bedrockk.molang.runtime.struct;

import com.bedrockk.molang.runtime.MoParams;
import com.bedrockk.molang.runtime.value.MoValue;

import java.util.Deque;

public interface MoStruct extends MoValue {

    void set(Deque<String> keys, MoValue value);

    MoValue get(Deque<String> names, MoParams params);

    void clear();

    @Override
    default MoStruct value() {
        return this;
    }
}
