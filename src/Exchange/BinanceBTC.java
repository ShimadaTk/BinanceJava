package Exchange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binance.api.client.domain.market.BookTicker;

public class BinanceBTC implements BinanceEx{

	private BinanceData data;
	private Map<String, Integer> accessNum = new HashMap<>();
	
	/**
	 * コンストラクタ
	 * 更新を楽にするために、「accessNum」に該当する通貨ペアの板情報を格納する。
	 */
	public BinanceBTC(BinanceData data){
		this.data = data;
		List<BookTicker> allBook = data.getClient().getBookTickers();
		for(int i = 0; i < allBook.size(); i++){
			if((allBook.get(i).getSymbol()).equals("ETHBTC")){
				accessNum.put("ETH", i);
			}
			else if((allBook.get(i).getSymbol()).equals("BNBBTC")){
				accessNum.put("BNB", i);
			}
			else if((allBook.get(i).getSymbol()).equals("IOTABTC")){
				accessNum.put("IOTA", i);
			}
		}
	/*	for (String key:BTCMap.keySet()){
			if(key.equals("IOTABTC")){
				System.out.println(key);
			}
			else if(key.equals("EtHBTC")){
				System.out.println(key);
			}
		}
		System.out.println("Mapサイズ:"+BTCMap.size());
		*/
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
			Double value = Double.parseDouble(allBook.get(accessNum.get("ETH")).getAskPrice());
			data.getBTCMap().put("ETH", (1/value)*0.999);
			value = Double.parseDouble(allBook.get(accessNum.get("BNB")).getAskPrice());
			data.getBTCMap().put("BNB", (1/value)*0.999);
			value = Double.parseDouble(allBook.get(accessNum.get("IOTA")).getAskPrice());
			data.getBTCMap().put("IOTA", (1/value)*0.999);
			//エラー発生時
			}catch(Exception e){
				e.printStackTrace();
				data.getBTCMap().put("ETH", 0.0);
				data.getBTCMap().put("BNB", 0.0);
				data.getBTCMap().put("IOTA", 0.0);
			}		
			return;
	}

	@Override
	public void transition() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("ETHかIOTAのどちらかしか選択できません。");
	}

	@Override
	public void transition2() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("ETHかIOTAのどちらかしか選択できません。");
	}

	@Override
	public void transition3() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("ETHかIOTAのどちらかしか選択できません。");
	}

/*	@Override
	public void transition4() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("ETHかIOTAのどちらかしか選択できません。");
	}
*/
}
