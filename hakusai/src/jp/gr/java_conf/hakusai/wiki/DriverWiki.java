package jp.gr.java_conf.hakusai.wiki;

import org.jdom.Element;


public class DriverWiki {

	private final static String DEFAULT_KEY = "TCP/IP";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WikiAccess wAcs = new WikiAccess();
		Element root = null;
		try {
			root = wAcs.getWikiElement(DEFAULT_KEY);
			WikiEdit wEdit = new WikiEdit();
			wEdit.getContentsLst(root);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


	}

}
