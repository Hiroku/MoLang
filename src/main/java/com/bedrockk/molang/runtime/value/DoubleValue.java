package com.bedrockk.molang.runtime.value;

public class DoubleValue implements MoValue {

    public final static DoubleValue ZERO = new DoubleValue(0.0);
    public final static DoubleValue ONE = new DoubleValue(1.0);

    public double value;

    public DoubleValue(Object value) {
        if (value instanceof Boolean) {
            this.value = (boolean) value ? 1.0 : 0.0;
        } else if (value instanceof Number) {
            this.value = ((Number)value).doubleValue();
        } else {
            this.value = 1.0;
        }
    }

    @Override
    public Double value() {
        return value;
    }

    @Override
    public String asString() {
        if (value == (long) value) {
            return String.format("%d", (long) value);
        } else {
            return String.format("%s", value);
        }
    }

    @Override
    public double asDouble() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) || (obj instanceof DoubleValue && ((DoubleValue)obj).asDouble() == value) || (obj instanceof StringValue && ((StringValue)obj).asString().equals(asString()));
    }
}
