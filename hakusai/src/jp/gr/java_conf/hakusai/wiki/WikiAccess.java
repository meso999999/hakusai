package jp.gr.java_conf.hakusai.wiki;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import jp.gr.java_conf.hakusai.util.ApiSystem;
import jp.gr.java_conf.hakusai.util.ApiUtils;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.JDOMParseException;
import org.jdom.input.SAXBuilder;

public class WikiAccess {

	private final String wikiTopUrl = "http://wikipedia.simpleapi.net/api?keyword=";
	private final String wikiTopFoot = "&output=xml";
	private final String wikiIncHead = "http://ja.wikipedia.org/w/api.php?format=json&action=opensearch&search=";
	private final String wikiIncFoot = "&namespace=0&suggest=";

	public String srchInc(String param) {
		StringBuffer bufStr = new StringBuffer();
		URL url;
		try {
			url = new URL(wikiIncHead + param + wikiIncFoot);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("GET");
			http.connect();
			BufferedReader buf = new BufferedReader(new InputStreamReader(
					http.getInputStream(), "UTF-8"));
			String line = "";
			while ((line = buf.readLine()) != null) {
				bufStr.append(line);
			}
			buf.close();
			System.out.println(bufStr.toString());

		} catch (MalformedURLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return ApiUtils.chgUnicode(bufStr);
	}

	public String srchParam(String param) {
		StringBuffer bufStr = new StringBuffer();
		URL url;
		try {
			System.out.println(wikiTopUrl + param + wikiTopFoot);
			url = new URL(wikiTopUrl + param + wikiTopFoot);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("GET");
			http.connect();
			BufferedReader buf = new BufferedReader(new InputStreamReader(
					http.getInputStream(), "UTF-8"));
			String line = "";
			while ((line = buf.readLine()) != null) {
				bufStr.append(line);
				bufStr.append(ApiSystem.DEFAULT_LINECODE);
			}
			buf.close();

		} catch (MalformedURLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return bufStr.toString();
	}

	public Element getWikiElement(String param) throws InterruptedException {
		Element root = null;
		try {
			SAXBuilder builder = new SAXBuilder();

			Document doc = null;
			// アクセス回数
			int accessMax = 40;
			int count = 0;
			while (accessMax!=count) {
				count++;
				try {
					doc = builder.build(wikiTopUrl + param + wikiTopFoot);
					System.out.println(wikiTopUrl + param + wikiTopFoot);
					break;
				} catch (JDOMParseException je) {
					Thread.sleep(100);
					System.out.println("ネットワークのアクセスに失敗しました：" + wikiTopUrl + param + wikiTopFoot);
				}
			}
			System.out.println(doc.toString());
			root = doc.getRootElement();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return root;

	}

}
