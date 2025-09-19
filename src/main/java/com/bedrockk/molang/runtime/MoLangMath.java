package com.bedrockk.molang.runtime;

import com.bedrockk.molang.runtime.struct.QueryStruct;
import com.bedrockk.molang.runtime.value.DoubleValue;

import java.util.HashMap;
import java.util.function.Function;

public final class MoLangMath {

    public static final QueryStruct LIBRARY = new QueryStruct(new HashMap<String, Function<MoParams, Object>>(){
        {
            put("abs", params -> Math.abs(params.getDouble(0)));
            put("acos", params -> Math.acos(params.getDouble(0)) * 180 / Math.PI);
            put("sin", params -> Math.sin(params.getDouble(0) * Math.PI / 180));
            put("asin", params -> Math.asin(params.getDouble(0)) * 180 / Math.PI);
            put("atan", params -> Math.atan(params.getDouble(0)) * 180 / Math.PI);
            put("atan2", params -> Math.atan2(params.getDouble(0), params.getDouble(1)) * 180 / Math.PI);
            put("ceil", params -> Math.ceil(params.getDouble(0)));
            put("clamp", params -> Math.min(params.getDouble(2), Math.max(params.getDouble(0), params.getDouble(1))));
            put("cos", params -> Math.cos(params.getDouble(0) * Math.PI / 180));
            put("die_roll", params -> dieRoll(params.getDouble(0), params.getDouble(1), params.getDouble(2)));
            put("die_roll_integer", params -> dieRollInt(params.getInt(0), params.getInt(1), params.getInt(2)));
            put("exp", params -> Math.exp(params.getDouble(0)));
            put("mod", params -> params.getDouble(0) % params.getDouble(1));
            put("floor", params -> Math.floor(params.getDouble(0)));
            put("hermite_blend", params -> hermiteBlend(params.getInt(0)));
            put("lerp", params -> lerp(params.getDouble(0), params.getDouble(1), params.getDouble(2)));
            put("lerp_rotate", params -> lerpRotate(params.getDouble(0), params.getDouble(1), params.getDouble(2)));
            put("ln", params -> Math.log(params.getDouble(0)));
            put("max", params -> Math.max(params.getDouble(0), params.getDouble(1)));
            put("min", params -> Math.min(params.getDouble(0), params.getDouble(1)));
            put("pi", params -> Math.PI);
            put("pow", params -> Math.pow(params.getDouble(0), params.getDouble(1)));
            put("random", params -> random(params.getDouble(0), params.getDouble(1)));
            put("random_integer", params -> randomInt(params.getInt(0), params.getInt(1)));
            put("round", params -> Math.round(params.getDouble(0)));
            put("sqrt", params -> Math.sqrt(params.getDouble(0)));
            put("trunc", params -> Math.floor(params.getDouble(0)));
            put("d2r", params -> Math.toRadians(params.getDouble(0)));
            put("r2d", params -> Math.toDegrees(params.getDouble(0)));
            put("sign", params -> {
                double val = params.getDouble(0);
                return val >= 0 ? 1 : -1;
            });
            put("copy_sign", params -> {
                double magnitude = params.getDouble(0);
                double sign = params.getDouble(1);
                return Math.abs(magnitude) * (sign >= 0 ? 1 : -1);
            });
        }
    });

    public static double random(double low, double high) {
        return low + Math.random() * (high - low);
    }

    public static int randomInt(int low, int high) {
        return (int) Math.round(low + Math.random() * (high - low));
    }

    public static double dieRoll(double num, double low, double high) {
        int i = 0;
        int total = 0;
        while (i++ < num) total += random(low, high);

        return total;
    }

    public static int dieRollInt(int num, int low, int high) {
        int i = 0;
        int total = 0;
        while (i++ < num) total += randomInt(low, high);

        return total;
    }

    public static int hermiteBlend(int value) {
        return (3 * value) ^ (2 - 2 * value) ^ 3;
    }

    public static double lerp(double start, double end, double amount) {
        amount = Math.max(0, Math.min(1, amount));

        return start + (end - start) * amount;
    }

    public static double lerpRotate(double start, double end, double amount) {
        start = radify(start);
        end = radify(end);

        if (start > end) {
            double tmp = start;
            start = end;
            end = tmp;
        }

        if (end - start > 180) {
            return radify(end + amount * (360 - (end - start)));
        }

        return start + amount * (end - start);
    }

    public static double radify(double num) {
        return (((num + 180) % 360) + 180) % 360;
    }
}
