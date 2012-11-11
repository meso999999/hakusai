package jp.gr.java_conf.hakusai.wiki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

public class WikiEdit {

	/**
	 * JSON形式の記事からメイン記事タイトルを取得
	 * @param wikiJsonTitle
	 */
	public String getMainReportTitle(String wikiJsonTitleLst) {
		int startIdx = wikiJsonTitleLst.indexOf("\"");
		int endIdx = wikiJsonTitleLst.indexOf("\"", ++startIdx);

		return wikiJsonTitleLst.substring(startIdx,endIdx);

	}

	/**
	 * JSON形式の記事からサブ記事タイトルを取得
	 * @param wikiJsonTitle
	 */
	public ArrayList<String> getSubReportTitle(String wikiJsonTitleLst) {
		ArrayList<String> rLst = new ArrayList<String>();

		System.out.println(wikiJsonTitleLst);
		int startSubIdx = wikiJsonTitleLst.indexOf("[",1);
		int endIdx = wikiJsonTitleLst.indexOf("]", ++startSubIdx);

		String[] wikiAry = wikiJsonTitleLst.substring(startSubIdx,endIdx).split(",");
		for(int i = 0; i < wikiAry.length ; i++) {
			rLst.add(wikiAry[i].replaceAll("\"", ""));
		}
		return rLst;

	}

	public List<HashMap<String, String>> getContentsLst(Element root) {
		List<HashMap<String, String>> lst = new ArrayList<HashMap<String,String>>();

		List<Element> resultLst = root.getChildren("result");

		Iterator<Element> rIte = resultLst.iterator();

		while(rIte.hasNext()){
			Element result = (Element)rIte.next();

			HashMap<String,String> hMap = new HashMap<String, String>();

			String id = result.getChild("id").getValue();
			String url = result.getChild("url").getValue();
			String title = result.getChild("title").getValue();
			String body = result.getChild("body").getValue();
			String length = result.getChild("length").getValue();
			String redirect = result.getChild("redirect").getValue();
			String strict = result.getChild("strict").getValue();
			String datetime = result.getChild("datetime").getValue();

			hMap.put("id", id);
			hMap.put("url", url);
			hMap.put("title", title);
			hMap.put("body", body);
			hMap.put("length", length);
			hMap.put("redirect", redirect);
			hMap.put("strict", strict);
			hMap.put("datetime", datetime);

			lst.add(hMap);

		}

		return lst;
	}
}
