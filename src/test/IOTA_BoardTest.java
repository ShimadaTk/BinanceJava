package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Exchange.BinanceData;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.BookTicker;

public class IOTA_BoardTest {

	public static void main(String[] args){
		/*
		 * クライアント
		 */
		BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("API-KEY", "SECRET");
		BinanceApiRestClient client = factory.newRestClient();
		
		List<BookTicker> a = client.getBookTickers();
	
		Map<String, Double> IOTAsumple = new HashMap<>();
		for(int i = 0; i < a.size(); i++){
			if((a.get(i).getSymbol()).equals("IOTAETH")){
				Double IOTAETH = Double.parseDouble(a.get(i).getBidPrice());
				IOTAsumple.put(a.get(i).getSymbol(), IOTAETH*0.999);
			}
			else if((a.get(i).getSymbol()).equals("IOTABNB")){
				Double IOTABNB = Double.parseDouble(a.get(i).getBidPrice());
				IOTAsumple.put(a.get(i).getSymbol(), IOTABNB*0.999);
				System.out.println(Double.parseDouble(a.get(i).getBidPrice()));
			}
			else if((a.get(i).getSymbol()).equals("IOTABTC")){
				Double IOTABTC = Double.parseDouble(a.get(i).getBidPrice());
				IOTAsumple.put(a.get(i).getSymbol(), IOTABTC*0.999);
			}
		}
		BinanceData data = new BinanceData();
		data.getIOTA().exchange();
		
		for (java.util.Map.Entry<String, Double> entry : IOTAsumple.entrySet()) {
		    System.out.println(entry.getKey() + "：" + entry.getValue());
		}
		 System.out.println("以下BinanceIOTA");
		for (java.util.Map.Entry<String, Double> entry2 : data.getIOTAMap().entrySet()) {
		    System.out.println(entry2.getKey() + "：" + entry2.getValue());
		}
	}
	
	
}
