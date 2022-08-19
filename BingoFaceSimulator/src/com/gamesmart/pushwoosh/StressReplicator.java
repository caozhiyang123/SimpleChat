package com.gamesmart.pushwoosh;

import java.io.FileInputStream;
import java.util.Properties;

import com.gamesmart.pushwoosh.client.BaseClient;
import com.gamesmart.pushwoosh.client.BingoFaceClient;
import com.gamesmart.pushwoosh.vo.MachineVO;

public class StressReplicator {
	
	public StressReplicator() {
		Properties props = new Properties();
        try {
			props.load(new FileInputStream("config/stressTest.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        MachineVO.ballCount = Integer.valueOf(props.getProperty("ballCount", "75"));
        MachineVO.cardWith = Integer.valueOf(props.getProperty("cardWith","5"));
        MachineVO.cardHeight = Integer.valueOf(props.getProperty("cardHeight","5"));
        MachineVO.drawBallCount = Integer.valueOf(props.getProperty("drawBallCount","5"));
        MachineVO.delta = Integer.valueOf(props.getProperty("delta","5"));
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			BaseClient client = new BingoFaceClient();
			new Thread(()->client.spin()).start();
		}
	}
}
