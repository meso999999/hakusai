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

public class CreateLogic {

	/** Cell blank値 */
	private final String CELL_BLANK_VAL = "Blank";

	/** Cell TRUE値 */
	private final String CELL_TRUE_VAL = "●";

	/** 項目の終了行 */
	private int kmkEndGyo = -1;

	/** 項目の桁数の桁 */
	private int kmkKtKt	= -1;

	/** 項目のNOT NULLの桁 */
	private int kmkNtNllKt = -1;

	/** 項目の主キーの桁 */
	private int kmkPrmryKeyKt = -1;

	/** 項目物理名の桁 */
	private int kmkPysNmKt = -1;

	/** 項目論理名の桁 */
	private int kmkLgcNmKt = -1;

	/** 項目の開始行 */
	private int kmkStrtGyo = -1;

	/** 項目の型の桁 */
	private int kmkTypKt	= -1;

	/** 項目の初期値の桁 */
	private int kmkInitKt	= -1;

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
	 * @param kmkStrtGyo 行開始番号
	 * @param kmkPysNmKt 物理名
	 * @param kmkLgcNmKt 論理名
	 * @param kmkTypKt 型名
	 * @param kmkKtKt 桁数
	 * @param kmkInitKt 初期値
	 * @param kmkPrmryKeyKt プライマリキー
	 * @param kmkNtNllKt NOT NULL
	 */
	public CreateLogic(
			Sheet shtObj,
			int tblPysNmGyo,
			int tblPysNmKt,
			int tblLgcNmGyo,
			int tblLgcNmKt,
			int tblCmntGyo,
			int tblCmntKt,
			int kmkStrtGyo,
			int kmkPysNmKt,
			int kmkLgcNmKt,
			int kmkTypKt,
			int kmkKtKt,
			int kmkInitKt,
			int kmkPrmryKeyKt,
			int kmkNtNllKt
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
		// 項目設定
		this.kmkStrtGyo = kmkStrtGyo;
		// 最終行を設定する。
		int count = kmkStrtGyo;
		int mx = 100000;
		while(count <= mx) {
			// 対象の項目が存在するかどうか判定する。
			Row row = shtObj.getRow(count);
	        Cell kmkPysNmCell = row.getCell(kmkPysNmKt);
	        if (kmkPysNmCell == null){
	        	kmkPysNmCell = row.createCell(kmkPysNmKt);
	        }
	        boolean sFlg = getCellFlg(getCellVal(kmkPysNmCell));

	        if(!sFlg) {
	        	if(count == kmkStrtGyo) {
	        		// 最初の行から項目が設定されていない場合はExceptionにする。
		        	throw new AppException("シートに項目が設定されていません。");
	        	}
	        	break;
	        }

        	count++;

	        if(count == mx) {
	        	throw new AppException("最終行[" + mx + "]まで到達してしまいました。");
	        }
		}
		// 読み込んでいる行より一つ下げる
		this.kmkEndGyo = count - 1;

		this.kmkPysNmKt = kmkPysNmKt;
		this.kmkLgcNmKt = kmkLgcNmKt;
		this.kmkTypKt = kmkTypKt;
		this.kmkKtKt = kmkKtKt;
		this.kmkInitKt = kmkInitKt;
		this.kmkPrmryKeyKt = kmkPrmryKeyKt;
		this.kmkNtNllKt = kmkNtNllKt;
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
	 * クリエイト文 行単位 カラムを取得
	 * @param tmpKmkGyo 取得する行
	 * @return クリエイト文 カラム
	 */
	private CreateClmnBean getCreateClmnBean(int tmpKmkGyo) {

		CreateClmnBean bean = new CreateClmnBean();

		Row row = this.shtObj.getRow(tmpKmkGyo);

		// 物理名称
        Cell kmkPysNmCell = row.getCell(kmkPysNmKt);
        if (kmkPysNmCell == null){
        	kmkPysNmCell = row.createCell(kmkPysNmKt);
        }
        bean.pysName = getCellVal(kmkPysNmCell);

		// 論理名称
        Cell kmkLgcNmCell = row.getCell(kmkLgcNmKt);
        if (kmkLgcNmCell == null){
        	kmkLgcNmCell = row.createCell(kmkLgcNmKt);
        }
        bean.lgcName = getCellVal(kmkLgcNmCell);

        // 型
        Cell kmkTypCell = row.getCell(kmkTypKt);
        if (kmkTypCell == null){
        	kmkTypCell = row.createCell(kmkTypKt);
        }
        bean.dataType = getCellVal(kmkTypCell);

        // 桁
        Cell kmkKtCell = row.getCell(kmkKtKt);
        if (kmkKtCell == null){
        	kmkKtCell = row.createCell(kmkKtKt);
        }
        bean.dgtNum = getCellVal(kmkKtCell);

        // 初期値
        Cell kmkInitCell = row.getCell(kmkInitKt);
        if (kmkInitCell == null){
        	kmkInitCell = row.createCell(kmkInitKt);
        }
        bean.initVal = getCellVal(kmkInitCell);

        // プライマリーキー
        Cell kmkPrmryKeyCell = row.getCell(kmkPrmryKeyKt);
        if (kmkPrmryKeyCell == null){
        	kmkPrmryKeyCell = row.createCell(kmkPrmryKeyKt);
        }
        bean.pkFlg = getCellFlg(getCellVal(kmkPrmryKeyCell));

        // NOT NULL
        Cell kmkNtNllCell = row.getCell(kmkNtNllKt);
        if (kmkNtNllCell == null){
        	kmkNtNllCell = row.createCell(kmkNtNllKt);
        }
        bean.notNllFlg = getCellFlg(getCellVal(kmkNtNllCell));

        return bean;
	}

	/**
	 * クリエイト文 取得
	 * CREATE TABLE table名 (
	 *     colum名 型名 (桁数) (DEFAULT '初期値') (PRIMARY KEY) (NOT NULL)
	 *     ・
	 *     ・
	 *     ・
	 *  );
	 * @return
	 */
	public String getCreateSntnc() {
		StringBuffer craBuf = new StringBuffer();

		// クリエイト文テーブルBean
		CreateTblBean tblBean = getCreateTblBean();
		// クリエイト文カラムリスト
		ArrayList<CreateClmnBean> lst = tblBean.clmnLst;
		Iterator<CreateClmnBean> ite = lst.iterator();
		// 行数取得
		int gyoCount = 1;
		int gyoEnd = lst.size();

		// コメントにテーブル名
		craBuf.append("-- ");
		craBuf.append(tblBean.lgcName);
		craBuf.append(System.getProperty("line.separator"));
		// 備考
		if (!StringUtils.isEmpty(tblBean.cmnt)) {
			craBuf.append("-- ");
			craBuf.append(tblBean.cmnt);
			craBuf.append(System.getProperty("line.separator"));
		}
		// ドロップテーブル
		craBuf.append("DROP TABLE ");
		craBuf.append(tblBean.pysName);
		craBuf.append(";");
		craBuf.append(System.getProperty("line.separator"));

		// テーブル
		craBuf.append("CREATE TABLE ");
		craBuf.append(tblBean.pysName);
		craBuf.append("( ");
		craBuf.append(System.getProperty("line.separator"));
		while(ite.hasNext()) {
			// インデント
			craBuf.append(ApiSystem.INDENT_TAB);
			// カラム
			CreateClmnBean clmnBean = ite.next();
			// 物理名
			craBuf.append(clmnBean.pysName);
			craBuf.append(" ");
			// データ型
			craBuf.append(clmnBean.dataType);
			// 桁数
			if(!StringUtils.isEmpty(clmnBean.dgtNum)){
				craBuf.append("(" + clmnBean.dgtNum + ")");
			}
			craBuf.append(" ");
			// 初期値
			if(!StringUtils.isEmpty(clmnBean.initVal)){
				craBuf.append("DEFAULT '");
				craBuf.append(clmnBean.initVal);
				craBuf.append("'");
				craBuf.append(" ");
			}
			// プライマリーキー
			if(clmnBean.pkFlg) {
				craBuf.append("PRIMARY KEY");
				craBuf.append(" ");
			}
			// Not Null
			if(clmnBean.notNllFlg) {
				craBuf.append("NOT NULL");
				craBuf.append(" ");
			}
			// コメント
			craBuf.append("COMMENT");
			craBuf.append(" ");
			craBuf.append("'");
			craBuf.append(clmnBean.lgcName);
			craBuf.append("'");
			// ,
			if(gyoCount != gyoEnd) {
				gyoCount++;
				craBuf.append(",");
			}

			// 改行
			craBuf.append(System.getProperty("line.separator"));
		}
		craBuf.append(");");
		// 改行
		craBuf.append(System.getProperty("line.separator"));
		craBuf.append(System.getProperty("line.separator"));

		ApiOutput.flgSystemOutPut(craBuf, ApiSystem.DEBUG_FLG);

		return craBuf.toString();

	}

	/**
	 * Create文Beanを取得
	 * @return Create文Bean
	 */
	private CreateTblBean getCreateTblBean() {

		// テーブル論理名称
		Row row = this.shtObj.getRow(this.tblLgcNmGyo);
        Cell tblLgcNmCell = row.getCell(tblLgcNmKt);
        if (tblLgcNmCell == null){
        	tblLgcNmCell = row.createCell(kmkLgcNmKt);
        }

        // テーブル物理名称
		row = this.shtObj.getRow(this.tblPysNmGyo);
        Cell tblPysNmCell = row.getCell(tblPysNmKt);
        if (tblPysNmCell == null){
        	tblPysNmCell = row.createCell(kmkPysNmKt);
        }

        // テーブルコメント
		row = this.shtObj.getRow(this.tblCmntGyo);
        Cell tblCmntCell = row.getCell(tblCmntKt);
        if (tblCmntCell == null){
        	tblCmntCell = row.createCell(tblCmntKt);
        }

        // カラムリスト
    	ArrayList<CreateClmnBean> lst = new ArrayList<CreateClmnBean>();
        for(int i = this.kmkStrtGyo ; i<=this.kmkEndGyo ;i++) {
        	lst.add(getCreateClmnBean(i));
        }

        return new CreateTblBean(
        		getCellVal(tblLgcNmCell),
        		getCellVal(tblPysNmCell),
        		getCellVal(tblCmntCell),
        		lst);

	}

}
