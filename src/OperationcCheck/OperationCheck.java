package OperationcCheck;

import java.util.List;
import java.util.Scanner;

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
		System.out.println(a.size());
		/**
		 * 板のListの４番目の通貨ペアの売り注文と買い注文
		 **/
		//売り板
		System.out.println("売り板"+a.get(3).getAskPrice()+"Ask");
		//買い板
		System.out.println("買い板"+a.get(3).getBidPrice()+"Bid");
		//通貨のペア
		System.out.println(a.get(0).getSymbol());
		
		
		System.out.println("実行しました。");
	}

}
