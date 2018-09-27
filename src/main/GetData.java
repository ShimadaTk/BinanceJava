package main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import Exchange.BinanceData;


/**
 *
 * 
 * 
 * 
 * Zaifで、それぞれの重さをエクセルに出力するクラス
 */
public class GetData {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

    	
    	// 現在の日付を取得
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");

        String outputFilePath = "/Users/shimadatakaya/Desktop/Binanceweight/" + dateFormat.format(date).toString() + "BinanceEx.xlsx";
        Workbook book = null;
        FileOutputStream fout = null;

        try {
            book = new SXSSFWorkbook();

            Font font = book.createFont();
            font.setFontName("ＭＳ ゴシック");
            font.setFontHeightInPoints((short) 9);

            DataFormat format = book.createDataFormat();

            //ヘッダ文字列用のスタイル
            CellStyle style_header = book.createCellStyle();
            style_header.setBorderBottom(BorderStyle.THIN);
            GetData.setBorder(style_header, BorderStyle.THIN);
            style_header.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_CORNFLOWER_BLUE.getIndex());
            style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style_header.setVerticalAlignment(VerticalAlignment.TOP);
            style_header.setFont(font);

            //文字列用のスタイル
            CellStyle style_string = book.createCellStyle();
            GetData.setBorder(style_string, BorderStyle.THIN);
            style_string.setVerticalAlignment(VerticalAlignment.TOP);
            style_string.setFont(font);

            //整数用のスタイル(BTC→JPY)
            CellStyle style_int = book.createCellStyle();
            GetData.setBorder(style_int, BorderStyle.THIN);
            style_int.setDataFormat(format.getFormat("0"));
            style_int.setVerticalAlignment(VerticalAlignment.TOP);
            style_int.setFont(font);
            
            //小数用のスタイル
            CellStyle style_Coin = book.createCellStyle();
            GetData.setBorder(style_Coin, BorderStyle.THIN);
            style_Coin.setDataFormat(format.getFormat("0.000000000000"));
            style_Coin.setVerticalAlignment(VerticalAlignment.TOP);
            style_Coin.setFont(font);

    /*        //小数用のスタイル(BTC→XEM)
            CellStyle style_BTCXEM = book.createCellStyle();
            GetData.setBorder(style_BTCXEM, BorderStyle.THIN);
            style_BTCXEM.setDataFormat(format.getFormat("0.00000000000"));
            style_BTCXEM.setVerticalAlignment(VerticalAlignment.TOP);
            style_BTCXEM.setFont(font);
            
            //小数用のスタイル(JPY→%)
            CellStyle style_JPY = book.createCellStyle();
            GetData.setBorder(style_JPY, BorderStyle.THIN);
            style_JPY.setDataFormat(format.getFormat("0.00000000000000000"));
            style_JPY.setVerticalAlignment(VerticalAlignment.TOP);
            style_JPY.setFont(font);

            //小数用のスタイル(MONA→BTC)
            CellStyle style_MONABTC = book.createCellStyle();
            GetData.setBorder(style_MONABTC, BorderStyle.THIN);
            style_MONABTC.setDataFormat(format.getFormat("0.000000000"));
            style_MONABTC.setVerticalAlignment(VerticalAlignment.TOP);
            style_MONABTC.setFont(font);
            
          //小数用のスタイル(MONA→JPY)
            CellStyle style_MONAJPY = book.createCellStyle();
            GetData.setBorder(style_MONAJPY, BorderStyle.THIN);
            style_MONAJPY.setDataFormat(format.getFormat("0.00"));
            style_MONAJPY.setVerticalAlignment(VerticalAlignment.TOP);
            style_MONAJPY.setFont(font);
            
          //小数用のスタイル(XEM→BTC)
            CellStyle style_XEMBTC = book.createCellStyle();
            GetData.setBorder(style_XEMBTC, BorderStyle.THIN);
            style_XEMBTC.setDataFormat(format.getFormat("0.000000000"));
            style_XEMBTC.setVerticalAlignment(VerticalAlignment.TOP);
            style_XEMBTC.setFont(font);
            
          //小数用のスタイル(XEM→JPY)
            CellStyle style_XEMJPY = book.createCellStyle();
            GetData.setBorder(style_XEMJPY, BorderStyle.THIN);
            style_XEMJPY.setDataFormat(format.getFormat("0.00000"));
            style_XEMJPY.setVerticalAlignment(VerticalAlignment.TOP);
            style_XEMJPY.setFont(font);
*/
            //日時表示用のスタイル
            CellStyle style_datetime = book.createCellStyle();
            GetData.setBorder(style_datetime, BorderStyle.THIN);
            style_datetime.setDataFormat(format.getFormat("yyyy/mm/dd hh:mm:ss"));
            style_datetime.setVerticalAlignment(VerticalAlignment.TOP);
            style_datetime.setFont(font);
            
          //日時表示用のスタイル(秒)
            CellStyle style_datetime_ss = book.createCellStyle();
            GetData.setBorder(style_datetime_ss, BorderStyle.THIN);
            style_datetime_ss.setDataFormat(format.getFormat("mm:ss"));
            style_datetime_ss.setVerticalAlignment(VerticalAlignment.TOP);
            style_datetime_ss.setFont(font);

            Row row;
            int rowNumber;
            Cell cell;
            int colNumber;

            //シートの作成(3シート作ってみる)
            Sheet sheet;

            for (int i = 0; i < 1; i++) {
                sheet = book.createSheet();
                if (sheet instanceof SXSSFSheet) {
                    ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
                }

                //シート名称の設定
                book.setSheetName(i, "シート" + (i + 1));

                //ヘッダ行の作成
                rowNumber = 0;
                colNumber = 0;
                row = sheet.createRow(rowNumber);
                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("更新時間");
                
                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("更新時間(秒)");
                
                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("更新回数"); 
                
                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("BTC→ETH");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("BTC→BNB");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("BTC→IOTA");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("ETH→BTC");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("ETH→BNB");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("ETH→IOTA");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("BNB→BTC");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("BNB→ETH");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("BNB→IOTA");
                
                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("IOTA→BTC");
                
                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("IOTA→ETH");
                
                cell = row.createCell(colNumber);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("IOTA→BNB");

                //ウィンドウ枠の固定
                sheet.createFreezePane(1, 1);

                //ヘッダ行にオートフィルタの設定
                sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, colNumber));

                //列幅の自動調整
                for (int j = 0; j <= colNumber; j++) {
                    sheet.autoSizeColumn(j, true);
                }

                BinanceData data = new BinanceData();
                //データ行の生成(一日分のデータを作ってみる)
                for (int j = 0; j < 1440; j++) {
                	data.getBTC().exchange();
                	data.getETH().exchange();
                	data.getBNB().exchange();
                	data.getIOTA().exchange();
                    rowNumber++;
                    colNumber = 0;
                    row = sheet.createRow(rowNumber);
                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_datetime);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(new Date());
                    
                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_datetime_ss);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(new Date());

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_int);
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue(j + 1);

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_Coin);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getBTCMap().get("ETH"));

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_Coin);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getBTCMap().get("BNB"));

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_Coin);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getBTCMap().get("IOTA"));

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_Coin);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getETHMap().get("BTC"));

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_Coin);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getETHMap().get("BNB"));

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_Coin);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getETHMap().get("IOTA"));

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_Coin);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getBNBMap().get("BTC"));
                    
                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_Coin);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getBNBMap().get("ETH"));
                    
                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_Coin);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getBNBMap().get("IOTA"));
                    
                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_Coin);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getIOTAMap().get("BTC"));
                    
                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_Coin);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getIOTAMap().get("ETH"));
                    
                    cell = row.createCell(colNumber);
                    cell.setCellStyle(style_Coin);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getIOTAMap().get("BNB"));
                    
                  //ここで待ち時間を決める
                   Thread.sleep(60000);
                    
                    //列幅の自動調整
                    for (int k = 0; k <= colNumber; k++) {
                        sheet.autoSizeColumn(k, true);
                    }                    
                }
            }

            

            //ファイル出力
            fout = new FileOutputStream(outputFilePath);
            book.write(fout);
        }
        finally {
            if (fout != null) {
                try {
                    fout.close();
                }
                catch (IOException e) {
                }
            }
            if (book != null) {
                try {
                    /*
                        SXSSFWorkbookはメモリ空間を節約する代わりにテンポラリファイルを大量に生成するため、
                        不要になった段階でdisposeしてテンポラリファイルを削除する必要がある
                     */
                    ((SXSSFWorkbook) book).dispose();
                }
                catch (Exception e) {
                }
            }
        }
    }

    private static void setBorder(CellStyle style, BorderStyle border) {
        style.setBorderBottom(border);
        style.setBorderTop(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
    }

    private final static String[] LIST_ALPHA = {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    private static String getExcelColumnString(int column) {
        String result = "";

        if (column >= 0) {
            if (column / GetData.LIST_ALPHA.length > 0) {
                result += getExcelColumnString(column / GetData.LIST_ALPHA.length - 1);
            }
            result += GetData.LIST_ALPHA[column % GetData.LIST_ALPHA.length];
        }

        return result;
    }
}