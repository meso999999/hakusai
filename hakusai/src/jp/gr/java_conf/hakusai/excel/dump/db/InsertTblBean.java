package jp.gr.java_conf.hakusai.excel.dump.db;

import java.util.ArrayList;

/**
 * インサート文用 テーブルBeanクラス
 * @author k-osanai
 *
 */
public class InsertTblBean {

	/** 論理名称 */
	public String lgcName = "";

	/** 物理名称 */
	public String pysName = "";

	/** 備考 */
	public String cmnt = "";

	/** カラムタイトルリスト */
	public ArrayList<String> clmnTtlLst = null;

	/** カラム値リスト */
	public ArrayList<ArrayList<String>> clmnValLst = null;

	/**
	 * テーブルのコンストラクタ
	 * 論理名／物理名
	 * @param lgcName 論理名
	 * @param pysName 物理名
	 * @param cmnt 備考
	 * @param clmnBean カラムBeanクラス
	 */
	public InsertTblBean(
			String lgcName,
			String pysName,
			String cmnt,
			ArrayList<String> clmnTtlLst,
			ArrayList<ArrayList<String>> clmnValLst
			) {
		this.lgcName = lgcName;
		this.pysName = pysName;
		this.cmnt = cmnt;
		this.clmnTtlLst = clmnTtlLst;
		this.clmnValLst = clmnValLst;
	}
}
