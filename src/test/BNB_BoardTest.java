package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Exchange.BinanceData;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.BookTicker;

public class BNB_BoardTest {

	public static void main(String[] args){
		/*
		 * クライアント
		 */
		BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("API-KEY", "SECRET");
		BinanceApiRestClient client = factory.newRestClient();
		
		List<BookTicker> a = client.getBookTickers();
	
		Map<String, Double> BNBsumple = new HashMap<>();
		for(int i = 0; i < a.size(); i++){
			if((a.get(i).getSymbol()).equals("BNBBTC")){
				Double BNBBTC = Double.parseDouble(a.get(i).getBidPrice());
				BNBsumple.put(a.get(i).getSymbol(), BNBBTC*0.999);
				System.out.println(Double.parseDouble(a.get(i).getBidPrice()));
			}
			else if((a.get(i).getSymbol()).equals("BNBETH")){
				Double BNBETH = Double.parseDouble(a.get(i).getBidPrice());
				BNBsumple.put(a.get(i).getSymbol(), BNBETH*0.999);
				System.out.println(Double.parseDouble(a.get(i).getBidPrice()));
			}
			else if((a.get(i).getSymbol()).equals("IOTABNB")){
				Double IOTABNB = Double.parseDouble(a.get(i).getAskPrice());
				BNBsumple.put(a.get(i).getSymbol(), (1/IOTABNB)*0.999);
				System.out.println(Double.parseDouble(a.get(i).getAskPrice()));
			}
		}
		BinanceData data = new BinanceData();
		data.getBNB().exchange();
		
		for (java.util.Map.Entry<String, Double> entry : BNBsumple.entrySet()) {
		    System.out.println(entry.getKey() + "：" + entry.getValue());
		}
		 System.out.println("以下BinanceBNB");
		for (java.util.Map.Entry<String, Double> entry2 : data.getBNBMap().entrySet()) {
		    System.out.println(entry2.getKey() + "：" + entry2.getValue());
		}
	}
	
	
}
