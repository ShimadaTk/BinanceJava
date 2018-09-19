package OperationcCheck;

import java.util.List;

import Exchange.BinanceBTC;
import Exchange.BinanceData;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.BookTicker;
import com.binance.api.client.domain.market.TickerStatistics;

public class OperationCheck {

	public static void main(String[] args) throws InterruptedException{
		
		/*
		 * クライアント
		 */
		BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("API-KEY", "SECRET");
		BinanceApiRestClient client = factory.newRestClient();
		/*
		 * 接続確認
		 */
		client.ping();
		long serverTime = client.getServerTime();
		System.out.println(serverTime);
		/**
		 * 終値
		 **/
		TickerStatistics tickerStatistics = client.get24HrPriceStatistics("NEOETH");
		System.out.println(tickerStatistics.getLastPrice());
		/**
		 * 全ての板情報
		 **/
		List<BookTicker> a = client.getBookTickers();
		System.out.println(a);
		/**
		 * 板のListの４番目の通貨ペアの売り注文と買い注文
		 **/
		//売り板
		System.out.println("売り板"+a.get(3).getAskPrice()+"Ask");
		//買い板
		System.out.println("買い板"+a.get(3).getBidPrice()+"Bid");
		//通貨のペア
		System.out.println(a.get(0).getSymbol());
		
		System.out.println("\n以降、自作クラステスト");
		BinanceData Data = new BinanceData();
		Data.getBTC().exchange();
		System.out.println(Data.getBTCMap().get("IOTA")+"  BTC→IOTA");
		System.out.println(Data.getBTCMap().get("ETH")+"  BTC→ETH");
		Thread.sleep(5000);
		Data.getBTC().exchange();
		System.out.println(Data.getBTCMap().get("IOTA")+"  BTC→IOTA");
		System.out.println(Data.getBTCMap().get("ETH")+"  BTC→ETH");
		Thread.sleep(5000);
		Data.getETH().exchange();
		System.out.println(Data.getETHMap().get("BTC")+"  ETH→BTC");
		System.out.println(Data.getETHMap().get("IOTA")+"  ETH→IOTA");
		Thread.sleep(5000);
		Data.getETH().exchange();
		System.out.println(Data.getETHMap().get("BTC")+"  ETH→BTC");
		System.out.println(Data.getETHMap().get("IOTA")+"  ETH→IOTA");
		Thread.sleep(5000);
		
		Data.getIOTA().exchange();
		System.out.println(Data.getIOTAMap().get("BTC")+"  IOTA→BTC");
		System.out.println(Data.getIOTAMap().get("ETH")+"  IOTA→ETH");
		Thread.sleep(5000);
		Data.getIOTA().exchange();
		System.out.println(Data.getIOTAMap().get("BTC")+"  IOTA→BTC");
		System.out.println(Data.getIOTAMap().get("ETH")+"  IOTA→ETH");
		
		
		System.out.println("実行しました。");
	}

}
