package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Exchange.BinanceData;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.BookTicker;

public class BTC_BoardTest {

	public static void main(String[] args){
		/*
		 * クライアント
		 */
		BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("API-KEY", "SECRET");
		BinanceApiRestClient client = factory.newRestClient();
		
		List<BookTicker> a = client.getBookTickers();
	
		Map<String, Double> BTCsumple = new HashMap<>();
		for(int i = 0; i < a.size(); i++){
			if((a.get(i).getSymbol()).equals("ETHBTC")){
				Double ETHBTC = Double.parseDouble(a.get(i).getAskPrice());
				BTCsumple.put(a.get(i).getSymbol(), (1/ETHBTC)*0.999);
				System.out.println(Double.parseDouble(a.get(i).getAskPrice()));
			}
			else if((a.get(i).getSymbol()).equals("BNBBTC")){
				Double BNBBTC = Double.parseDouble(a.get(i).getAskPrice());
				BTCsumple.put(a.get(i).getSymbol(), (1/BNBBTC)*0.999);
				System.out.println(Double.parseDouble(a.get(i).getAskPrice()));
			}
			else if((a.get(i).getSymbol()).equals("IOTABTC")){
				Double IOTABTC = Double.parseDouble(a.get(i).getAskPrice());
				BTCsumple.put(a.get(i).getSymbol(), (1/IOTABTC)*0.999);
				System.out.println(Double.parseDouble(a.get(i).getAskPrice()));
			}
		}
		BinanceData data = new BinanceData();
		data.getBTC().exchange();
		
		for (java.util.Map.Entry<String, Double> entry : BTCsumple.entrySet()) {
		    System.out.println(entry.getKey() + "：" + entry.getValue());
		}
		 System.out.println("以下BinanceBTC");
		for (java.util.Map.Entry<String, Double> entry2 : data.getBTCMap().entrySet()) {
		    System.out.println(entry2.getKey() + "：" + entry2.getValue());
		}
	}
	
	
}
