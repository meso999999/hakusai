package test.jp.gr.java_conf.hakusai.wiki;

import java.util.HashMap;
import java.util.List;

import jp.gr.java_conf.hakusai.wiki.WikiAccess;
import jp.gr.java_conf.hakusai.wiki.WikiEdit;

import org.jdom.Element;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestWiki {

	private final static String DEFAULT_KEY = "ガラス";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWiki() {
		WikiAccess wAcs = new WikiAccess();
		Element root;
		try {
			root = wAcs.getWikiElement(DEFAULT_KEY);
			WikiEdit wEdit = new WikiEdit();
			List<HashMap<String,String>> hMapLst = wEdit.getContentsLst(root);

			for(HashMap<String,String> hMap : hMapLst ) {
				System.out.println("　　" + "ID：");
				System.out.println("　　　" + hMap.get("id"));
				System.out.println("　　" + "URL：");
				System.out.println("　　　" + hMap.get("url"));
				System.out.println("　　" + "TITLE：");
				System.out.println("　　　" + hMap.get("title"));
				System.out.println("　　" + "BODY：");
				System.out.println("　　　" + hMap.get("body"));
				System.out.println("　　" + "LENGTH：");
				System.out.println("　　　" + hMap.get("length"));
				System.out.println("　　" + "REDIRECT：");
				System.out.println("　　　" + hMap.get("redirect"));
				System.out.println("　　" + "STRICT：");
				System.out.println("　　　" + hMap.get("strict"));
				System.out.println("　　" + "DATETIME：");
				System.out.println("　　　" + hMap.get("datetime"));
			}
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
