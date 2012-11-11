package jp.gr.java_conf.hakusai.util;

public class ApiOutput {

	/**
	 * フラグがtrueだった場合にオブジェクトのtoStringを出力する。
	 * @param obj
	 * @param flg
	 */
	public static void flgSystemOutPut(
			Object obj,
			boolean flg) {
		if(flg) {
			System.out.println(obj.toString());
		} else {
			return;
		}

	}

}
