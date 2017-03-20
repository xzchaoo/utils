package com.xzchaoo.utils.commandline;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Administrator on 2017/3/20.
 */
public class ArgsParserTest {
	@Test
	public void test_parse() {
		ArgsParser ap = new ArgsParser();
		String[] args0 = new String[]{"-a", "-b=2", "--cc", "--dd=4", "--e=1,2,3"};
		Args args = ap.parse(args0);
		assertTrue(args.contains("a"));
		assertEquals(2, args.getInt("b"));
		assertTrue(args.getBoolean("cc"));
		assertEquals(4, args.getInt("dd"));
		List<Integer> intList = args.getIntList("e");
		assertEquals(Arrays.asList(1, 2, 3), intList);
	}
}
