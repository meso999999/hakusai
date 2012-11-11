package jp.gr.java_conf.hakusai.excel.dump.db;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Iterator;

import jp.gr.java_conf.hakusai.exeption.AppException;
import jp.gr.java_conf.hakusai.util.ApiOutput;
import jp.gr.java_conf.hakusai.util.ApiSystem;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class InsertLogic {

	/** Cell blank値 */
	private final String CELL_BLANK_VAL = "Blank";

	/** シートオブジェクト */
	private Sheet shtObj = null;

	/** テーブル備考の行 */
	private int tblCmntGyo = -1;

	/** テーブル備考の桁 */
	private int tblCmntKt = -1;

	/** テーブル論理名の行 */
	private int tblLgcNmGyo = -1;

	/** テーブル論理名の桁 */
	private int tblLgcNmKt = -1;

	/** テーブル物理名の行 */
	private int tblPysNmGyo = -1;

	/** テーブル物理名の桁 */
	private int tblPysNmKt = -1;

	/** 項目タイトル行番号 */
	private int kmkTtlGyo = -1;

	/** 項目タイトル開始列番号 */
	private int kmkTtlStrtKt = -1;

	/** 項目タイトル終了列番号 */
	private int kmkTtlEndKt = -1;

	/** 項目値開始行番号 */
	private int kmkValStrGyo = -1;

	/** 項目値開始列番号 */
	private int kmkValStrtKt = -1;

	/** 項目値終了行番号 */
	private int kmkValEndGyo = -1;

	/** 項目値終了列番号 */
	private int kmkValEndKt = -1;

	/**
	 * コンストラクタ
	 * テーブルの行、桁の番号の設定
	 * 項目の行、桁の番号の設定
	 *
	 * @param shtObj シートオブジェクト
	 * @param tblPysNmGyo テーブル物理名 行番号
	 * @param tblPysNmKt テーブル物理名 列番号
	 * @param tblLgcNmGyo テーブル論理名 行番号
	 * @param tblLgcNmKt テーブル論理名 列番号
	 * @param tblCmntGyo テーブルコメント 行番号
	 * @param tblCmntKt テーブルコメント 列番号
	 * @param kmkTtlStrGyo 項目タイトル開始行番号
	 * @param kmkTtlStrtKt 項目タイトル開始列番号
	 * @param kmkValStrGyo 項目値開始行番号
	 * @param kmkValStrtKt 項目値開始列番号
	 */
	public InsertLogic(
			Sheet shtObj,
			int tblPysNmGyo,
			int tblPysNmKt,
			int tblLgcNmGyo,
			int tblLgcNmKt,
			int tblCmntGyo,
			int tblCmntKt,
			int kmkTtlStrtGyo,
			int kmkTtlStrtKt,
			int kmkValStrtGyo,
			int kmkValStrtKt
			) throws AppException {

		// シート設定
		this.shtObj = shtObj;
		// テーブル設定
		this.tblPysNmGyo = tblPysNmGyo;
		this.tblPysNmKt = tblPysNmKt;
		this.tblLgcNmGyo = tblLgcNmGyo;
		this.tblLgcNmKt = tblLgcNmKt;
		this.tblCmntGyo = tblCmntGyo;
		this.tblCmntKt = tblCmntKt;
		// 項目タイトル行設定
		this.kmkTtlGyo = kmkTtlStrtGyo;
		// 項目タイトル開始列設定
		this.kmkTtlStrtKt = kmkTtlStrtKt;
		// 項目タイトル終了列設定
		int count = kmkTtlStrtKt;
		int mx = 100000;
		while(count <= mx) {
			// 対象の項目が存在するかどうか判定する。
			Row row = shtObj.getRow(kmkTtlStrtGyo);
	        Cell kmkTtlCell = row.getCell(count);
	        if (kmkTtlCell == null){
	        	kmkTtlCell = row.createCell(count);
	        }
	        boolean sFlg = getCellFlg(getCellVal(kmkTtlCell));

	        if(!sFlg) {
	        	if(count == kmkTtlStrtKt) {
	        		// 最初の行から項目が設定されていない場合はExceptionにする。
		        	throw new AppException("シートに項目が設定されていません。");
	        	}
	        	break;
	        }

        	count++;

	        if(count == mx) {
	        	throw new AppException("最終列[" + mx + "]まで到達してしまいました。");
	        }
		}
		// 読み込んでいる列より一つ下げる
		this.kmkTtlEndKt  = count - 1;

		// 項目値開始行番号
		this.kmkValStrGyo = kmkValStrtGyo;
		// 項目値終了行番号
		int valCount = kmkValStrtGyo;
		int valMx = 100000;
		while(valCount <= valMx) {
			// 対象の項目が存在するかどうか判定する。
			Row row = shtObj.getRow(valCount);
	        Cell kmkValCell = row.getCell(kmkValStrtKt);
	        if (kmkValCell == null){
	        	kmkValCell = row.createCell(kmkValStrtKt);
	        }
	        boolean sFlg = getCellFlg(getCellVal(kmkValCell));

	        if(!sFlg) {
	        	if(valCount == kmkValStrtGyo) {
	        		// 最初の行から項目が設定されていない場合はExceptionにする。
		        	throw new AppException("シートに項目が設定されていません。");
	        	}
	        	break;
	        }

	        valCount++;

	        if(valCount == valMx) {
	        	throw new AppException("最終行[" + mx + "]まで到達してしまいました。");
	        }
		}
		// 項目値終了行番号
		this.kmkValEndGyo = valCount - 1;
	}

	/**
	 * セルの値がTRUE／FALSEか返す
	 * @param val 値
	 * @return <br />
	 *    false 値がFALSE <br />
	 *    true 値がTRUE <br />
	 */
	private boolean getCellFlg(String val) {
		if(StringUtils.equals(val, CELL_BLANK_VAL)
				|| StringUtils.isEmpty(val)) {
			return false;
		}
		return true;
	}

	/**
	 * セル オブジェクトでString値で返す
	 * @param cell セル オブジェクト
	 * @return セル String値
	 */
	private String getCellVal(Cell cell) {

		// セル文字列
		String cellVal = "";

        switch(cell.getCellType()) {
	        case Cell.CELL_TYPE_STRING:
	        	cellVal = cell.getStringCellValue();
	        	break;
	        case Cell.CELL_TYPE_NUMERIC:
	          if(DateUtil.isCellDateFormatted(cell)) {
	        	  cellVal = cell.getDateCellValue().toString();
	          } else {
	        	  cellVal = Double.toString(cell.getNumericCellValue());
	        	  int dotIdx = cellVal.lastIndexOf(".");
	        	  if(cellVal.length() == (dotIdx + 2) &&
	        			  cellVal.charAt(dotIdx + 1) == '0') {
	        		cellVal = cellVal.replaceAll("\\.0", "");
	        	  }
	          }
	        	break;
	        case Cell.CELL_TYPE_BOOLEAN:
	        	cellVal = Boolean.toString(cell.getBooleanCellValue());
	        	break;
	        case Cell.CELL_TYPE_FORMULA:
	        	cellVal = cell.getCellFormula();
	        	break;
	        case Cell.CELL_TYPE_ERROR :
	        	cellVal = Byte.toString(cell.getErrorCellValue());
	        	break;
	        case Cell.CELL_TYPE_BLANK :
	        	cellVal = "";
	        	break;
        }

        return cellVal;
	}

	/**
	 * インサート文用 テーブルBeanクラスを取得
	 * @return インサート文用 テーブルBeanクラス
	 */
	private InsertTblBean getInsertTblBean() {

		// テーブル論理名を取得
		String lgcName = "";
		Row row = this.shtObj.getRow(this.tblLgcNmGyo);
		Cell lgcCell = row.getCell(tblLgcNmKt);
		if (lgcCell == null) {
			lgcCell = row.createCell(tblLgcNmKt);
		}
		lgcName = getCellVal(lgcCell);

		// テーブル物理名を取得
		String pysName = "";
		row = this.shtObj.getRow(this.tblPysNmGyo);
		Cell pysCell = row.getCell(tblPysNmKt);
		if (pysCell == null) {
			pysCell = row.createCell(tblPysNmKt);
		}
		pysName = getCellVal(pysCell);

		// テーブルコメント
		String cmnt = "";
		row = this.shtObj.getRow(this.tblCmntGyo);
		Cell cmntCell = row.getCell(tblCmntKt);
		if (cmntCell == null) {
			cmntCell = row.createCell(tblCmntKt);
		}
		cmnt = getCellVal(cmntCell);

		// カラムタイトルリスト
		ArrayList<String> ttlLst = getClmnTtlLst();

		// カラムタイトルリスト
		ArrayList<ArrayList<String>> clmTbl = getClmnTbl();

		InsertTblBean bean = new InsertTblBean(
				lgcName,
				pysName,
				cmnt,
				ttlLst,
				clmTbl);

		return bean;
	}

	/**
	 * インサート文 行単位 カラムタイトルを取得
	 * @param tmpKmkGyo 取得する行
	 * @return インサート文 カラムタイトル
	 */
	private ArrayList<String> getClmnTtlLst() {

		// カラムリスト
		ArrayList<String> lst = new ArrayList<String>();

		for(int i = kmkTtlStrtKt; i <= kmkTtlEndKt; i++) {
			Row row = this.shtObj.getRow(this.kmkTtlGyo);

	        Cell kmkTtlCell = row.getCell(i);
	        if (kmkTtlCell == null){
	        	kmkTtlCell = row.createCell(i);
	        }
	        lst.add(getCellVal(kmkTtlCell));
		}

        return lst;
	}

	/**
	 * インサート文 テーブル カラムを取得
	 * @return インサート文 カラム値テーブル
	 */
	private ArrayList<ArrayList<String>> getClmnTbl() {

		// カラムリスト
		ArrayList<ArrayList<String>> tbl = new ArrayList<ArrayList<String>>();

		for(int i = this.kmkValStrGyo; i <= this.kmkValEndGyo; i++) {
			ArrayList<String> lst = getClmnLst(i);
			tbl.add(lst);
		}

        return tbl;
	}

	/**
	 * インサート文 行単位 カラムを取得
	 * @param tmpKmkGyo 取得する行
	 * @return インサート文 カラム
	 */
	private ArrayList<String> getClmnLst(int tmpKmkGyo) {

		// カラムリスト
		ArrayList<String> lst = new ArrayList<String>();

		for(int i = kmkTtlStrtKt; i <= this.kmkTtlEndKt; i++) {
			Row row = this.shtObj.getRow(tmpKmkGyo);

	        Cell kmkTtlCell = row.getCell(i);
	        if (kmkTtlCell == null){
	        	kmkTtlCell = row.createCell(i);
	        }
	        lst.add(getCellVal(kmkTtlCell));
		}

        return lst;
	}


	/**
	 * INSERT文 取得
	 * INSERT INTO table名 (
	 *     colum_1名,
	 *     colum_2名,
	 *     ・
	 *     colum_N名
	 *  ) VALUES (
	 *     value_1名,
	 *     value_2名,
	 *     ・
	 *     value_N名
	 * );
	 * @return
	 */
	public String getInsertSntnc() {
		StringBuffer insBuf = new StringBuffer();

		// テーブルBeanを取得
		InsertTblBean insBean = getInsertTblBean();

		// インサート文タイトルリスト
		ArrayList<String> ttlLst = insBean.clmnTtlLst;
		// インサート文カラムリストテーブル
		ArrayList<ArrayList<String>> valLst = insBean.clmnValLst;
		Iterator<ArrayList<String>> iteVal = valLst.iterator();

		// コメントにテーブル名
		insBuf.append("-- ");
		insBuf.append(insBean.lgcName);
		insBuf.append(System.getProperty("line.separator"));
		// 備考
		if (!StringUtils.isEmpty(insBean.cmnt)) {
			insBuf.append("-- ");
			insBuf.append(insBean.cmnt);
			insBuf.append(System.getProperty("line.separator"));
		}
		// ドロップテーブル
		insBuf.append("DELETE TABLE ");
		insBuf.append(insBean.pysName);
		insBuf.append(";");
		insBuf.append(System.getProperty("line.separator"));

		// カラム値
		while(iteVal.hasNext()) {

			// テーブル
			insBuf.append("INSERT INTO ");
			insBuf.append(insBean.pysName);
			insBuf.append("( ");

			// カラムタイトル
			Iterator<String> iteTtl = ttlLst.iterator();
			int ttlCount = 1;
			while(iteTtl.hasNext()) {
				// カラムタイトル
				String ttl = iteTtl.next();

				insBuf.append(ttl);

				if(ttlCount != ttlLst.size()) {
					ttlCount++;
					insBuf.append(",");
				}
			}

			insBuf.append(") VALUES (");

			// カラムリスト
			ArrayList<String> clmnLst = iteVal.next();
			Iterator<String> iteClmn = clmnLst.iterator();
			// 行数
			int clmnCount = 1;
			while(iteClmn.hasNext()) {
				// 項目
				String clmn = iteClmn.next();

				insBuf.append("'");
				insBuf.append(clmn);
				insBuf.append("'");

				if(clmnCount != clmnLst.size()) {
					clmnCount++;
					insBuf.append(",");
				}
			}
			insBuf.append(");");
			insBuf.append(System.getProperty("line.separator"));

		}

		return insBuf.toString();

	}

}
