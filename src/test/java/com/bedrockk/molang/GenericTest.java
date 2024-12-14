package com.bedrockk.molang;

import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoParams;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.StringValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("Generic Tests")
public class GenericTest {

	@Test
	@DisplayName("MoParams Test")
	public void testMoParams() {
		var value = new StringValue("AAAAAAAAA");
		var params = new MoParams(new MoLangEnvironment(), List.of(value));
		Assertions.assertEquals(value.asString(), params.getString(0));
	}

	@Test
	public void testDoubleValueAsString() {
		var value = new DoubleValue(1.0);
		var result = value.asString();
		Assertions.assertEquals("1", result);
	}

	@Test
	public void testDoubleValueAsString2() {
		var value = new DoubleValue(1.1120);
		var result = value.asString();
		Assertions.assertEquals("1.112", result);
	}
}
