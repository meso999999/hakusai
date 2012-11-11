package jp.gr.java_conf.hakusai.excel.dump.db;

import java.util.ArrayList;

/**
 * クリエイト文用　テーブルBeanクラス
 * @author k-osanai
 *
 */
public class CreateTblBean {

	/** 論理名称 */
	public String lgcName = "";

	/** 物理名称 */
	public String pysName = "";

	/** 備考 */
	public String cmnt = "";

	/** カラムBeanリスト */
	public ArrayList<CreateClmnBean> clmnLst = null;

	/**
	 * テーブルのコンストラクタ
	 * 論理名／物理名
	 * @param lgcName 論理名
	 * @param pysName 物理名
	 * @param cmnt 備考
	 * @param clmnBean カラムBeanクラス
	 */
	public CreateTblBean(
			String lgcName,
			String pysName,
			String cmnt,
			ArrayList<CreateClmnBean> clmnLst
			) {
		this.lgcName = lgcName;
		this.pysName = pysName;
		this.cmnt = cmnt;
		this.clmnLst = clmnLst;
	}
}
