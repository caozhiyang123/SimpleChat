package com.gamesmart.pushwoosh.client;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.gamesmart.pushwoosh.vo.GameCard;
import com.gamesmart.pushwoosh.vo.MachineVO;

public class BingoFaceClient extends BaseClient{
	private static DecimalFormat format = new DecimalFormat("0.0000");
	
	private BitSet start() {
		List<Integer> ballNumbers = initBall();
		List<Integer> balls = getBalls(ballNumbers);
		GameCard gameCard = new GameCard();
		
		spins.addAndGet(1);
		BitSet cardFlag = gameCard.getCardFlag();
		Integer[] numbers = gameCard.getNumbers();
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numbers.length; i++) {
			if(balls.contains(numbers[i])) {
				cardFlag.set(i);
				sb.append(1);
			}else {
				sb.append(0);
			}
		}
		
		return cardFlag;
	}

	private List<Integer> initBall() {
		List<Integer> ballNumbers =  new ArrayList<>();
		for (int i = 0; i < MachineVO.ballCount; i++)
		{
			ballNumbers.add(i + 1);
		}
		return ballNumbers;
	}

	private List<Integer>  getBalls(List<Integer> ballNumbers) {
		Collections.shuffle(ballNumbers);
		int drawBallCount = MachineVO.drawBallCount;
		List<Integer> balls = new ArrayList<Integer>(drawBallCount);
		for (int i = 0; i < drawBallCount; i++) {
			balls.add(ballNumbers.get(i));
		}
		return balls;
	}

	@Override
	public void spin() {
		for (int i = 0; i < 10000; i++) {
			BitSet cardFlag = start();
			int length = MachineVO.cardHeight * MachineVO.cardWith;
			int res = 0;
			for (int j = 0; j < length; j++) {
				res |= ((cardFlag.get(j)?1:0) << (length-j-1));
			}
			setWinPattern(res);
		}
	}

	private static AtomicInteger spins = new AtomicInteger();
	private static Map<String,AtomicInteger> hit = new ConcurrentHashMap<String, AtomicInteger>();
	
	private static void setWinPattern(int src) {
		Map<String, List<Integer>> pattern = MachineVO.getPattern();
		for (Map.Entry<String, List<Integer>> map: pattern.entrySet()) {
			String key = map.getKey();
			List<Integer> value = map.getValue();
			for (Integer dst : value) {
				if((src & dst) == dst) {
					AtomicInteger count = hit.getOrDefault(key,new AtomicInteger());
					hit.put(key, count);
					count.addAndGet(1);
					break;
				}
			}
		}
		if(spins.intValue()%MachineVO.delta == 0) {
			StringBuffer sb = new StringBuffer();
			for (Map.Entry<String,AtomicInteger> map: hit.entrySet()) {
				double hitRate = map.getValue().intValue()*1.0/spins.intValue();
				sb.append(map.getKey()+":"+format.format(hitRate));
				sb.append(",");
			}
			System.out.println(sb.toString());
		}
	}
}
