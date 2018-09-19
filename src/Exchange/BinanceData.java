package Exchange;

import java.util.HashMap;
import java.util.Map;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;

public class BinanceData {
	
	private BinanceBTC BTC;
	private BinanceETH ETH;
	private BinanceIOTA IOTA;
	
	private Map<String, Double> BTCMap = new HashMap<>();
	private Map<String, Double> ETHMap = new HashMap<>();
	private Map<String, Double> IOTAMap = new HashMap<>();
	
	/*
	 * クライアント
	 */
	private BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("API-KEY", "SECRET");
	private BinanceApiRestClient client = factory.newRestClient();
	
	public BinanceData(){
		BTC = new BinanceBTC(this);
		ETH = new BinanceETH(this);
		IOTA = new BinanceIOTA(this);
	}
	
	public BinanceApiRestClient getClient(){
		return this.client;
	}
	
	public BinanceBTC getBTC(){
		return this.BTC;
	}
	
	public BinanceETH getETH(){
		return this.ETH;
	}
	
	public BinanceIOTA getIOTA(){
		return this.IOTA;
	}
	
	////ゲッター(BTCMap)
	public Map<String, Double> getBTCMap(){
		return BTCMap;		
	}
	
	//ゲッター(ETHMap)
	public Map<String, Double> getETHMap(){
		return ETHMap;		
	}
	 //ゲッター(IOTAMap)
	public Map<String, Double> getIOTAMap(){
		return IOTAMap;		
	}

}
