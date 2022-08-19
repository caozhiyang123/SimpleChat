package com.gamesmart.pushwoosh.vo;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

public class GameCard {
	private int cardWith;
	private int cardHeight;
	private Integer[] numbers;
	private BitSet cardFlag;
	
	public BitSet getCardFlag() {
		return cardFlag;
	}
	
	public Integer[] getNumbers() {
		return numbers;
	}
	
	public GameCard() {
		cardWith = MachineVO.cardWith;
		cardHeight = MachineVO.cardHeight;
		numbers = new Integer[cardWith * cardHeight];
	
		List<List<Integer>> cardNumbers= getCardNums();
	    for(List<Integer> colum: cardNumbers)
	    {
	        Collections.shuffle(colum);
	    }
	    numbers = new Integer[cardWith * cardHeight];

	    for (int col = 0; col < cardWith; col++)
	    {
	        List<Integer> columNubers = cardNumbers.get(col);
	        for (int row = 0; row < cardHeight; row++)
	        {
	            int number = columNubers.get(row);
	            numbers[col+ row * cardWith ] = number;
	        }
	    }
	    cardFlag = new BitSet(cardWith * cardHeight);
	    cardFlag.set(cardWith * cardHeight /2);
	    
	}
	
	private List<List<Integer>> getCardNums() {
        List<List<Integer>> cardNumbers = new ArrayList<>();
        for (int i = 0; i < 75; i++)
        {
            int colum = i / 15;
            List<Integer> row = null;
            if(cardNumbers.size() <= colum)
            {
                row = new ArrayList<>();
                cardNumbers.add(row);
            }
            else
            {
                row = cardNumbers.get(colum);
            }
            row.add(i + 1);
        }
        return cardNumbers;
    }
	
	
}
