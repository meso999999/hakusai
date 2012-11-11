package jp.gr.java_conf.hakusai.excel.dump.db;

public class CreateClmnBean {

	/** 論理名称 */
	public String lgcName = "";

	/** 物理名称 */
	public String pysName = "";

	/** データ型 */
	public String dataType = "";

	/** 桁数 */
	public String dgtNum = "";

	/** 初期値 */
	public String initVal = "";

	/** プライマリーキー　フラグ */
	public boolean pkFlg = false;

	/** インデックスキー１　フラグ */
	public boolean idx1Flg = false;

	/** インデックスキー２　フラグ */
	public boolean idx2Flg = false;

	/** Not Null フラグ */
	public boolean notNllFlg = false;

	/** ユニーク フラグ */
	public boolean uniqueFlg = false;

}
