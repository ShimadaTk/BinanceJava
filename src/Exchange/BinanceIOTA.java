package Exchange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binance.api.client.domain.market.BookTicker;

public class BinanceIOTA implements BinanceEx{

	
	private BinanceData data;
	private Map<String, Integer> accessNum = new HashMap<>();
	
	/**
	 * コンストラクタ
	 * 更新を楽にするために、「accessNum」に該当する通貨ペアの板情報を格納する。
	 */
	public BinanceIOTA(BinanceData data){
		this.data = data;
		List<BookTicker> allBook = data.getClient().getBookTickers();
		for(int i = 0; i < allBook.size(); i++){
			if((allBook.get(i).getSymbol()).equals("IOTABTC")){
				accessNum.put("BTC", i);
			}
			else if((allBook.get(i).getSymbol()).equals("IOTAETH")){
				accessNum.put("ETH", i);
			}
			else if((allBook.get(i).getSymbol()).equals("IOTABNB")){
				accessNum.put("BNB", i);
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
			data.getIOTAMap().put("BTC", value*0.999);
			value = Double.parseDouble(allBook.get(accessNum.get("ETH")).getBidPrice());
			data.getIOTAMap().put("ETH", value*0.999);
			value = Double.parseDouble(allBook.get(accessNum.get("BNB")).getBidPrice());
			data.getIOTAMap().put("BNB", value*0.999);
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
		
		
		//IOTA→BTC
		nextNode2 = "BTC";
		for(int i = 0; i < (data.getNodeNum()-2); i++){
			totalWeight2 = 1.0;//初期化
			nextWeight2 = 1.0;//初期化
			/*
			 * 計算開始
			 */
			totalWeight2 *= data.getIOTAMap().get("BTC");
			nextWeight2 *= data.getIOTAMap().get("BTC");
			
			//IOTA→BTC→BNB→ETH
			if(i==0){
				totalWeight2 *= data.getBTCMap().get("BNB");
				totalWeight2 *= data.getBNBMap().get("ETH");
				System.out.println(totalWeight2 + "IOTA;BTC;BNB;ETH");
				nextNode = nextNode2;
				nextWeight = nextWeight2;
				totalWeight = totalWeight2;
			}

			//IOTA→BTC→ETH
			else if(i==1){
				totalWeight2 *= data.getBTCMap().get("ETH");
				System.out.println(totalWeight2 + "IOTA;BTC;ETH");
			}
			if(i>=1 && totalWeight2 > totalWeight){
				totalWeight = totalWeight2;
			}		
		}
		
		//IOTA→BNB
		nextNode2 = "BNB";
		for(int i = 0; i < (data.getNodeNum()-2); i++){
			totalWeight2 = 1.0;//初期化
			nextWeight2 = 1.0;//初期化
			/*
			 * 計算開始
			 */
			totalWeight2 *= data.getIOTAMap().get("BNB");
			nextWeight2 *= data.getIOTAMap().get("BNB");
			
			//IOTA→BNB→BTC→ETH
			if(i==0){
				totalWeight2 *= data.getBNBMap().get("BTC");
				totalWeight2 *= data.getBTCMap().get("ETH");
				System.out.println(totalWeight2 + "IOTA;BNB;BTC;ETH;");
			}
			
			//IOTA→BNB→ETH
			else if(i==1){
				totalWeight2 *= data.getBNBMap().get("ETH");
				System.out.println(totalWeight2 + "IOTA;BNB;ETH");
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
		
		//IOTA→BTC
		if((data.getRoute().get(1)).equals("BTC")){
			for(int i = 0; i < (data.getNodeNum()-2); i++){
				totalWeight2 = 1.0;
				nextWeight2 = 1.0;
				if(i == 0){   ////IOTA→BTC→BNB→ETH
					nextNode2 = "BNB";
					nextWeight2 *= data.getBTCMap().get("BNB");
					totalWeight2 *= data.getBTCMap().get("BNB");
					totalWeight2 *= data.getBNBMap().get("ETH");
					nextNode = nextNode2;
					nextWeight = nextWeight2;
					totalWeight = totalWeight2;
				}
				else if(i == 1){   ////IOTA→BTC→ETH
					nextNode2 = "ETH";
					nextWeight2 *= data.getBTCMap().get("ETH");
					totalWeight2 *= data.getBTCMap().get("ETH");
				}
				
				if(i>=1 && totalWeight2 > totalWeight){
					nextNode = nextNode2;
					nextWeight = nextWeight2;
					totalWeight = totalWeight2;
				}	
			}
		}
		//IOTA→BNB
		else if((data.getRoute().get(1)).equals("BNB")){
			for(int i = 0; i < (data.getNodeNum()-2); i++){
				totalWeight2 = 1.0;
				nextWeight2 = 1.0;
				//IOTA→BNB→BTC→ETH
				if(i==0){
					nextNode2 = "BTC";
					nextWeight2 *= data.getBNBMap().get("BTC");
					totalWeight2 *= data.getBNBMap().get("BTC");
					totalWeight2 *= data.getBTCMap().get("ETH");
					nextNode = nextNode2;
					nextWeight = nextWeight2;
					totalWeight = totalWeight2;
				}
			
				//IOTA→BNB→ETH
				else if(i==1){
					nextNode2 = "ETH";
					nextWeight2 *= data.getBNBMap().get("ETH");
					totalWeight2 *= data.getBNBMap().get("ETH");
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
		
		//IOTA→BTC→BNB→ETH
		if((data.getRoute().get(2)).equals("BTC")){
			nextNode = "ETH";
			nextWeight = data.getBTCMap().get("ETH");
		}
		else if((data.getRoute().get(2)).equals("BNB")){
			nextNode = "ETH";
			nextWeight = data.getBNBMap().get("ETH");
		}
		data.setGainWeight(nextWeight);
		data.setRoute(nextNode);
	}

/*	@Override
	public void transition4() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
*/
}
