package com.gamesmart.simplechat.enghine.util;

public class ChatUtil {
	//tmp names
	private static final String[] names = {"翠花","小明","狗蛋","顺溜","小花","丹尼","麦克","欸特","罗伯特","古斯特","蛋蛋","陌陌","艾琳","玩具","刘弘畊","周杰伦","那英","张杰","费玉清","大老板","小老弟","来跟华子"};

	public static String getName() {
		int size = names.length;
		int index = (int)(Math.random()*size);
		return names[index];
	}
}
