package test;

import java.util.Scanner;

import Exchange.BinanceData;

public class GetRoute {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		BinanceData data = new BinanceData();
		data.getBTC().exchange();
		data.getETH().exchange();
		data.getBNB().exchange();
		data.getIOTA().exchange();
		System.out.println("変換する仮想通貨(ETH，または，IOTAの二択)を選んでください．:");
		System.out.println("ETHならIOTAに，IOTAならETHに変換されます．");
		Scanner scan = new Scanner(System.in);
		String str = scan.next();	
		if(str.equals("ETH")){
			data.setBinanceExchange(data.getETH());
			data.setRoute("ETH");
		}
		else if(str.equals("IOTA")){
			data.setBinanceExchange(data.getIOTA());
			data.setRoute("IOTA");
		}
		else{
			System.out.print("入力した通貨は存在しないか、変換できません．");
			return;
		}
		for(int i = 1; i <= 5; i++){
			   data.getBTC().exchange();
			   data.getETH().exchange();
			   data.getBNB().exchange();
			   data.getIOTA().exchange();
			   if(i==1){
				   data.getBinanceExchange().transition();
			   }
			   else if(i==2){
				   data.getBinanceExchange().transition2();
			   }
			   else if(i==3){
				   data.getBinanceExchange().transition3();
			   }
	/*		   else if(i==4){
				   data.getBinanceExchange().transition4();
			   }
	*/
			   if(str.equals("ETH") && (data.getRoute().get(i)).equals("IOTA")){
				   break;
			   }
			   else if(str.equals("IOTA") && (data.getRoute().get(i)).equals("ETH")){
				   break;
			   }
			//   Thread.sleep(10000);
		   }
		System.out.println("");//改行目的
		for (int j = 0; j < data.getRoute().size();j++){
			if(j<data.getRoute().size()-1){
				System.out.print(data.getRoute().get(j)+"→");
				}
			else{
				System.out.println(data.getRoute().get(j)+"が最も効率の良い変換です．");
				System.out.println("変換結果:"+data.getGainWeight());
				if(str.equals("ETH")){
					System.out.println("(１ETHが "+data.getGainWeight()+ "IOTA になるということ．)");
					}
				else if(str.equals("IOTA")){
					System.out.println("(１IOTAが "+data.getGainWeight()+ "ETH になるということ．)");
					}
				}
		}
		/*
		 * 変換が得であったから確認するための変数
		 */
		Double difference = 1.0;
		if(str.equals("ETH")){
			difference = data.getGainWeight() - data.getETHMap().get("IOTA");
			System.out.println("\nETH→IOTA:"+data.getETHMap().get("IOTA"));
			System.out.println("普通に交換する(ETH→IOTA)より "+difference+ "IOTA 分お得です．)");
			}
		else if(str.equals("IOTA")){
			difference = data.getGainWeight() - data.getIOTAMap().get("ETH");
			System.out.println("\nIOTA→ETH:"+data.getIOTAMap().get("ETH"));
			System.out.println("普通に交換する(IOTA→ETH)より "+difference+ "ETH 分お得です．)");
			}
		
		/*
		 * 元の通貨に戻った時の重さを表す変数
		 */
		Double reRoute = 1.0;
		if(str.equals("ETH")){
			reRoute = (data.getGainWeight()) * data.getIOTAMap().get("ETH");
			}
		else if(str.equals("IOTA")){
			reRoute = (data.getGainWeight()) * data.getETHMap().get("IOTA");
			}
		System.out.println("\nこのルートをたどった後、元の通貨"+str+"に戻ると，重さは "
			+reRoute+" になります．");
		   
	}

}
