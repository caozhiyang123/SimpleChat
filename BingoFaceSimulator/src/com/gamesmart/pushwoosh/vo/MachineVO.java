package com.gamesmart.pushwoosh.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineVO {
	public static int ballCount = 75;
	public static int cardWith = 5;
	public static int cardHeight = 5;
	public static int drawBallCount = 40;
	public static int delta = 10000;
	
	public static Map<String,List<Integer>> pattern;
	public static Map<String,List<Integer>> getPattern(){return pattern;}
	static {
		pattern = new HashMap<>();
		List<Integer> rowPattern = new ArrayList<>();
		rowPattern.add(Integer.parseInt("1111100000000000000000000",2));
		rowPattern.add(Integer.parseInt("0000011111000000000000000",2));
		rowPattern.add(Integer.parseInt("0000000000111110000000000",2));
		rowPattern.add(Integer.parseInt("0000000000000001111100000",2));
		rowPattern.add(Integer.parseInt("0000000000000000000011111",2));
		pattern.put("any_row_pattern", rowPattern);
		
		List<Integer> columnPattern = new ArrayList<>();
		columnPattern.add(Integer.parseInt("1000010000100001000010000",2));
		columnPattern.add(Integer.parseInt("0100001000010000100001000",2));
		columnPattern.add(Integer.parseInt("0010000100001000010000100",2));
		columnPattern.add(Integer.parseInt("0001000010000100001000010",2));
		columnPattern.add(Integer.parseInt("0000100001000010000100001",2));
		pattern.put("any_column_pattern", columnPattern);
		
		List<Integer> cornersPattern = new ArrayList<>();
		cornersPattern.add(Integer.parseInt("1000100000000000000010001",2));
		pattern.put("corners", cornersPattern);

		List<Integer> slashPattern = new ArrayList<>();
		slashPattern.add(Integer.parseInt("1000001000001000001000001",2));
		slashPattern.add(Integer.parseInt("0000100010001000100010000",2));
		pattern.put("slash_pattern", slashPattern);
		
		
	}
}
