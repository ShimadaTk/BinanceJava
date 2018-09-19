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
		}
	}
	/**
	 * 各ノード間の重みを計算、格納
	 */
	@Override
	public void exchange() {
		// TODO 自動生成されたメソッド・スタブ
		try{
			List<BookTicker> allBook = data.getClient().getBookTickers();
			Double value = Double.parseDouble(allBook.get(accessNum.get("BTC")).getAskPrice());
			data.getIOTAMap().put("BTC", (1/value)*0.999);
			value = Double.parseDouble(allBook.get(accessNum.get("ETH")).getAskPrice());
			data.getIOTAMap().put("ETH", (1/value)*0.999);
			//エラー発生時
			}catch(Exception e){
				e.printStackTrace();
			}		
			return;
	}

	
	@Override
	public void transition() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void transition2() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void transition3() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void transition4() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}
