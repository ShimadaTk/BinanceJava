package Exchange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binance.api.client.domain.market.BookTicker;
import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

public class BinanceETH implements BinanceEx{

	
	private BinanceData data;
	private Map<String, Integer> accessNum = new HashMap<>();
	
	/**
	 * コンストラクタ
	 * 更新を楽にするために、「accessNum」に該当する通貨ペアの板情報を格納する。
	 */
	public BinanceETH(BinanceData data){
		this.data = data;
		List<BookTicker> allBook = data.getClient().getBookTickers();
		for(int i = 0; i < allBook.size(); i++){
			if((allBook.get(i).getSymbol()).equals("ETHBTC")){
				accessNum.put("BTC", i);
			}
			else if((allBook.get(i).getSymbol()).equals("BNBETH")){
				accessNum.put("BNB", i);
			}
			else if((allBook.get(i).getSymbol()).equals("IOTAETH")){
				accessNum.put("IOTA", i);
			}
		}
	}
	
	/**
	 * 各ノード間の重みを計算、格納
	 */
	@Override
	public void exchange() {
		// TODO 自動生成されたメソッド・スタブ
		/*
		 * 板情報はStringとしてリストに格納されているため、Double型にキャストする
		 */
		try{
			List<BookTicker> allBook = data.getClient().getBookTickers();
			Double value = Double.parseDouble(allBook.get(accessNum.get("BTC")).getBidPrice());
			data.getETHMap().put("BTC",  value*0.999);
			value = Double.parseDouble(allBook.get(accessNum.get("BNB")).getAskPrice());
			data.getETHMap().put("BNB", (1/value)*0.999);
			value = Double.parseDouble(allBook.get(accessNum.get("IOTA")).getAskPrice());
			data.getETHMap().put("IOTA", (1/value)*0.999);
		
			
			//エラー発生時
			}catch(Exception e){
				e.printStackTrace();
			}		
			return;
	}
	

	
	@Override
	public void transition() {
		String nextNode = null;//決定経路
		Double nextWeight = 1.0;
		Double totalWeight = 1.0;
		String nextNode2 = null;//仮経路
		Double totalWeight2 = 1.0;
		Double nextWeight2 = 1.0;
		
		
		//ETH→BTC
		nextNode2 = "BTC";
		for(int i = 0; i < (data.getNodeNum()-2); i++){
			totalWeight2 = 1.0;//初期化
			nextWeight2 = 1.0;//初期化
			/*
			 * 計算開始
			 */
			totalWeight2 *= data.getETHMap().get("BTC");
			nextWeight2 *= data.getETHMap().get("BTC");
			
			//ETH→BTC→BNB→IOTA
			if(i==0){
				totalWeight2 *= data.getBTCMap().get("BNB");
				totalWeight2 *= data.getBNBMap().get("IOTA");
				System.out.println(totalWeight2 + "ETH;BTC;BNB;IOTA");
				nextNode = nextNode2;
				nextWeight = nextWeight2;
				totalWeight = totalWeight2;
			}

			//ETH→BTC→IOTA
			else if(i==1){
				totalWeight2 *= data.getBTCMap().get("IOTA");
				System.out.println(totalWeight2 + "ETH;BTC;IOTA");
			}
			if(i>=1 && totalWeight2 > totalWeight){
				totalWeight = totalWeight2;
			}		
		}
		
		//ETH→BNB
		nextNode2 = "BNB";
		for(int i = 0; i < (data.getNodeNum()-2); i++){
			totalWeight2 = 1.0;//初期化
			nextWeight2 = 1.0;//初期化
			/*
			 * 計算開始
			 */
			totalWeight2 *= data.getETHMap().get("BNB");
			nextWeight2 *= data.getETHMap().get("BNB");
			
			//ETH→BNB→BTC→IOTA
			if(i==0){
				totalWeight2 *= data.getBNBMap().get("BTC");
				totalWeight2 *= data.getBTCMap().get("IOTA");
				System.out.println(totalWeight2 + "ETH;BNB;BTC;IOTA;");
			}
			
			//ETH→BNB→IOTA
			else if(i==1){
				totalWeight2 *= data.getBNBMap().get("IOTA");
				System.out.println(totalWeight2 + "ETH;BNB;IOTA");
			}
			
			if(totalWeight2 > totalWeight){
				nextNode = nextNode2;
				nextWeight = nextWeight2;
				totalWeight = totalWeight2;
			}	
		}
		data.setGainWeight(nextWeight);
		data.setRoute(nextNode);
	}

	
	
	@Override
	public void transition2() {
		String nextNode = null;//決定経路
		Double nextWeight = 1.0;
		Double totalWeight = 1.0;
		String nextNode2 = null;//仮経路
		Double totalWeight2 = 1.0;
		Double nextWeight2 = 1.0;
		
		//ETH→BTC
		if((data.getRoute().get(1)).equals("BTC")){
			for(int i = 0; i < (data.getNodeNum()-2); i++){
				totalWeight2 = 1.0;
				nextWeight2 = 1.0;
				if(i == 0){   ////ETH→BTC→BNB→IOTA
					nextNode2 = "BNB";
					nextWeight2 *= data.getBTCMap().get("BNB");
					totalWeight2 *= data.getBTCMap().get("BNB");
					totalWeight2 *= data.getBNBMap().get("IOTA");
					nextNode = nextNode2;
					nextWeight = nextWeight2;
					totalWeight = totalWeight2;
				}
				else if(i == 1){   ////ETH→BTC→IOTA
					nextNode2 = "IOTA";
					nextWeight2 *= data.getBTCMap().get("IOTA");
					totalWeight2 *= data.getBTCMap().get("IOTA");
				}
				
				if(i>=1 && totalWeight2 > totalWeight){
					nextNode = nextNode2;
					nextWeight = nextWeight2;
					totalWeight = totalWeight2;
				}	
			}
		}
		//ETH→BNB
		else if((data.getRoute().get(1)).equals("BNB")){
			for(int i = 0; i < (data.getNodeNum()-2); i++){
				totalWeight2 = 1.0;
				nextWeight2 = 1.0;
				//ETH→BNB→BTC→IOTA
				if(i==0){
					nextNode2 = "BTC";
					nextWeight2 *= data.getBNBMap().get("BTC");
					totalWeight2 *= data.getBNBMap().get("BTC");
					totalWeight2 *= data.getBTCMap().get("IOTA");
					nextNode = nextNode2;
					nextWeight = nextWeight2;
					totalWeight = totalWeight2;
				}
			
				//ETH→BNB→IOTA
				else if(i==1){
					nextNode2 = "IOTA";
					nextWeight2 *= data.getBNBMap().get("IOTA");
					totalWeight2 *= data.getBNBMap().get("IOTA");
				}
			
				if(i>=1 && totalWeight2 > totalWeight){
					nextNode = nextNode2;
					nextWeight = nextWeight2;
					totalWeight = totalWeight2;
				}
			}
		}
		data.setGainWeight(nextWeight);
		data.setRoute(nextNode);
	}

	@Override
	public void transition3() {
		String nextNode = null;//決定経路
		Double nextWeight = 1.0;
		
		//ETH→BTC→BNB→IOTA
		if((data.getRoute().get(2)).equals("BTC")){
			nextNode = "IOTA";
			nextWeight = data.getBTCMap().get("IOTA");
		}
		else if((data.getRoute().get(2)).equals("BNB")){
			nextNode = "IOTA";
			nextWeight = data.getBNBMap().get("IOTA");
		}
		data.setGainWeight(nextWeight);
		data.setRoute(nextNode);
	}
	
/*	@Override
	public void transition4() {
		Double nextWeight = 1.0;
		if((data.getRoute().get(3)).equals("BTC")){
			nextWeight = data.getBTCMap().get("ETH");;
		}
		else if((data.getRoute().get(3)).equals("BNB")){
			nextWeight = data.getBNBMap().get("ETH");;
		}
		else if((data.getRoute().get(3)).equals("IOTA")){
			nextWeight = data.getIOTAMap().get("ETH");;
		}
		data.setGainWeight(nextWeight);
		data.setRoute("ETH");
	}
	*/

}
