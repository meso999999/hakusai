package jp.gr.java_conf.hakusai.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class ApiUtils {

	/**
	 * 文字列をlenの長さで切る。<br />
	 * lenの長さに足りない場合はそのまま出力する。<br />
	 * null だった場合は "" で返す。
	 * @param s 対象文字列
	 * @param len 切る長さ
	 * @return 切られた後の文字列
	 */
	public static String apiSubstring(String s,int len) {
		if (s == null) {
			return "";
		} else if(StringUtils.length(s) >= 0 && StringUtils.length(s) < len) {
			return s;
		} else {
			return s.substring(0,len);
		}
	}

	/**
	 * A-Zの英字を指定された文字数でランダムで出力
	 * @param len
	 * @return
	 */
    public static String getRndAZStr(int len) {

        //Randomクラスのインスタンス化
        Random rnd = new Random();
        // 文字バッファ
        StringBuffer sBuf = new StringBuffer();

       //変数の宣言
        int ran;
        int a;
        char c;

        //10回繰り返す
        for(int b=0;b<len;b++){
           //０～２５の乱数を作成
            ran = rnd.nextInt(26);

            //６５を足して６５～９０にする
            a = 65 + ran;

            //charに型変換
            c = (char)a;
            //表示
            sBuf.append(c);
        }

        return sBuf.toString();
    }


	/**
	 * ファイル名から拡張子を取得
	 * @param fNm ファイル名
	 * @return
	 */
	public static String getExtention(String fNm) {

		int fLength = fNm.length();
		int count = fLength - 1;
		while(true) {
			char s = fNm.charAt(count);
			if(s == '.') {
				break;
			}
			if(count <= 0) {
				break;
			}

			count--;
		}

		fNm = fNm.substring(count);

		return fNm;

	}

	/**
	 * ファイル名から拡張子を除いたファイル名を取得
	 * @param fNm ファイル名
	 * @return
	 */
	public static String getDelExtention(String fNm) {

		int fLength = fNm.length();
		int count = fLength - 1;
		while(true) {
			char s = fNm.charAt(count);
			if(s == '.') {
				break;
			}
			if(count <= 0) {
				break;
			}

			count--;
		}

		fNm = fNm.substring(0,count);

		return fNm;

	}

	/**
	 * Unicode（例：\u3042）の文字列を符号化して日本語文字列にする。
	 * @param bufParam 符号化前文字列
	 * @return 符号化文字列
	 */
	public static String chgUnicode(StringBuffer bufParam) {

		// 符号化後文字列を挿入
		StringBuffer bufCode = new StringBuffer();

		// 符号化
		for(int i=0; i < bufParam.length(); i++) {
			String tmp = "";
			if( bufParam.length() >= i+2) {
				tmp = bufParam.substring(i, i+2);
			}

			if(tmp.equals("\\u")) {

				String[] strAry = bufParam.substring(i+2, i+6).split(" ");

				List<Character> lst = new ArrayList<Character>();
				for(int k=0;k<strAry.length;k++) {
					String itm = strAry[k];
					try {
						lst.add(new Character((char)Integer.parseInt(itm,16)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				char[] buf = new char[lst.size()];
				for (int j=0; j<lst.size(); j++) {
					buf[j] = ((Character)lst.get(j)).charValue();
				}
				bufCode.append(buf);
				i = i+5;

			} else {
				if( bufParam.length() >= i+1) {
					bufCode.append(bufParam.substring(i, i+1));
				}
			}
		}

		return bufCode.toString();

	}
}
