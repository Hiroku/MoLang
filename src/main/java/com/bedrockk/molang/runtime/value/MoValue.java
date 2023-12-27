package com.bedrockk.molang.runtime.value;

import com.bedrockk.molang.runtime.struct.ArrayStruct;
import com.bedrockk.molang.runtime.struct.VariableStruct;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Map;

public interface MoValue {
    static MoValue of(Object value) {
        if (value instanceof JsonObject) {
            VariableStruct struct = new VariableStruct();
            for (Map.Entry<String, JsonElement> entry : ((JsonObject) value).entrySet()) {
                struct.setDirectly(entry.getKey(), of(entry.getValue()));
            }
            return struct;
        } else if (value instanceof JsonPrimitive primitive) {
            if (primitive.isBoolean()) {
                return new DoubleValue(primitive.getAsBoolean());
            } else if (primitive.isNumber()) {
                return new DoubleValue(primitive.getAsNumber());
            } else {
                return new StringValue(primitive.getAsString());
            }
        } else if (value instanceof JsonArray) {
            ArrayStruct struct = new ArrayStruct();
            int i = 0;
            for (JsonElement element : (JsonArray) value) {
                struct.setDirectly(String.valueOf(i), of(element));
            }
            return struct;
        } else if (value instanceof MoValue) {
            return (MoValue) value;
        } else {
            return new DoubleValue(value);
        }
    }

    static JsonElement writeToJson(MoValue moValue) {
        if (moValue instanceof DoubleValue) {
            return new JsonPrimitive(((DoubleValue) moValue).value());
        } else if (moValue instanceof StringValue) {
            return new JsonPrimitive(((StringValue) moValue).value());
        } else if (moValue instanceof ArrayStruct) {
            JsonArray array = new JsonArray();
            for (MoValue value : ((ArrayStruct) moValue).getMap().values()) {
                JsonElement element = writeToJson(value);
                if (element != null) {
                    array.add(element);
                }
            }
            return array;
        } else if (moValue instanceof VariableStruct) {
            JsonObject object = new JsonObject();
            for (Map.Entry<String, MoValue> entry : ((VariableStruct) moValue).getMap().entrySet()) {
                JsonElement element = writeToJson(entry.getValue());
                if (element != null) {
                    object.add(entry.getKey(), element);
                }
            }
            return object;
        } else {
            return null;
        }
    }

    Object value();

    default String asString() {
        return this.toString();
    }

    default double asDouble() {
        return 1.0;
    }
}
