package Exchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;

public class BinanceData {
	
	private BinanceBTC BTC;
	private BinanceETH ETH;
	private BinanceBNB BNB;
	private BinanceIOTA IOTA;
	
	/*
	 * ノードの数
	 */
	final private int nodeNum = 4;
	
	/*
	 * 各ノードへの重みとノード名を格納
	 */
	private Map<String, Double> BTCMap = new HashMap<>();
	private Map<String, Double> ETHMap = new HashMap<>();
	private Map<String, Double> BNBMap = new HashMap<>();
	private Map<String, Double> IOTAMap = new HashMap<>();
	
	/*
	 * 変換の際の状態を示す
	 */
	private BinanceEx binanceExchange = null;
	
	/*
	 * ノードの経路の重さ表すArrayList
	 */
	private Double gainWeight = 1.0;
	
	/*
	 * 決定経路
	 */
	private ArrayList<String> route = new ArrayList<>();
	
	/*
	 * クライアントフィールド
	 */
	private BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("API-KEY", "SECRET");
	private BinanceApiRestClient client = factory.newRestClient();
	
	/*
	 * コンストラクタ
	 */
	public BinanceData(){
		BTC = new BinanceBTC(this);
		ETH = new BinanceETH(this);
		BNB = new BinanceBNB(this);
		IOTA = new BinanceIOTA(this);
	}
	
	public BinanceApiRestClient getClient(){
		return this.client;
	}
	
	//ゲッター(BTC,ETH,BNB,IOTA)
	public BinanceBTC getBTC(){
		return this.BTC;
	}	
	public BinanceETH getETH(){
		return this.ETH;
	}	
	public BinanceBNB getBNB(){
		return this.BNB;
	}
	public BinanceIOTA getIOTA(){
		return this.IOTA;
	}
	
	//ゲッター(BTCMap,ETHMap,BNBMap,IOTAMap)
	public Map<String, Double> getBTCMap(){
		return BTCMap;		
	}	
	public Map<String, Double> getETHMap(){
		return ETHMap;		
	}
	public Map<String, Double> getBNBMap(){
		return BNBMap;		
	}
	public Map<String, Double> getIOTAMap(){
		return IOTAMap;		
	}
	
	//ゲッターnodeNum
	public int getNodeNum(){
		return nodeNum;		
	}
	
	
	//ゲッター(扱う仮想通貨)
	public BinanceEx getBinanceExchange(){
		return binanceExchange;
	}
	//セッター(扱う仮想通貨)
	public void setBinanceExchange(BinanceEx binanceExchange){
		this.binanceExchange = binanceExchange;
	}	

	
	//ゲッター(結果の重み)
	public Double getGainWeight(){
		return gainWeight;
	}
	//セッター(結果の重み)
	public void setGainWeight(Double gainWeight){
		this.gainWeight *= gainWeight;
	}
	
	
	//ゲッター(経路)
	public ArrayList<String> getRoute(){
		return route;
	}
	//セッター(経路)
	public void setRoute(String nextNode){
		this.route.add(nextNode);
	}

}
