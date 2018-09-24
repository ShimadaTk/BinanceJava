package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Exchange.BinanceData;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.BookTicker;

public class ETH_BoardTest {

	public static void main(String[] args){
		/*
		 * クライアント
		 */
		BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("API-KEY", "SECRET");
		BinanceApiRestClient client = factory.newRestClient();
		
		List<BookTicker> a = client.getBookTickers();
	
		Map<String, Double> ETHsumple = new HashMap<>();
		for(int i = 0; i < a.size(); i++){
			if((a.get(i).getSymbol()).equals("ETHBTC")){
				Double ETHBTC = Double.parseDouble(a.get(i).getBidPrice());
				ETHsumple.put(a.get(i).getSymbol(), ETHBTC*0.999);
			}
			else if((a.get(i).getSymbol()).equals("BNBETH")){
				Double BNBETH = Double.parseDouble(a.get(i).getAskPrice());
				ETHsumple.put(a.get(i).getSymbol(), (1/BNBETH)*0.999);
			}
			else if((a.get(i).getSymbol()).equals("IOTAETH")){
				Double IOTAETH = Double.parseDouble(a.get(i).getAskPrice());
				ETHsumple.put(a.get(i).getSymbol(), (1/IOTAETH)*0.999);
			}
		}
		BinanceData data = new BinanceData();
		data.getETH().exchange();
		
		for (java.util.Map.Entry<String, Double> entry : ETHsumple.entrySet()) {
		    System.out.println(entry.getKey() + "：" + entry.getValue());
		}
		 System.out.println("以下BinanceETH");
		for (java.util.Map.Entry<String, Double> entry2 : data.getETHMap().entrySet()) {
		    System.out.println(entry2.getKey() + "：" + entry2.getValue());
		}
	}
	
	
}
