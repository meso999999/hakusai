package junit.test.jp.gr.java_conf.hakusai.util;

import static org.junit.Assert.*;
import jp.gr.java_conf.hakusai.util.ApiUtils;
import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ApiUtilsTest {

	// ファイル名
	private String fNm = "";
	// ファイル名拡張子ないバージョン
	private String fNmDelExt = "";
	// 拡張子
	private String ext = "";
	// カウント
	private int count = 0;


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
	public void testGetExtention() {
		try {
			System.out.println(new Throwable().getStackTrace()[0].getMethodName());
			// テスト１
			System.out.println("　テスト１:");
			fNm = "abc.txt";
			System.out.println("　　ファイル名:" + fNm);
			ext = ApiUtils.getExtention(fNm);
			System.out.println("　　実行後拡張子:" + ext);
			assertEquals(ext,".txt");
			// テスト２
			System.out.println("　テスト２:");
			fNm = "abc.txt.doc";
			System.out.println("　　ファイル名:" + fNm);
			ext = ApiUtils.getExtention(fNm);
			System.out.println("　　実行後拡張子:" + ext);
			assertEquals(ext,".doc");
			// テスト３
			System.out.println("　テスト３:");
			fNm = ".doc";
			System.out.println("　　ファイル名:" + fNm);
			ext = ApiUtils.getExtention(fNm);
			System.out.println("　　実行後拡張子:" + ext);
			assertEquals(ext,".doc");
			// テスト４
			System.out.println("　テスト４:");
			fNm = "doc.";
			System.out.println("　　ファイル名:" + fNm);
			ext = ApiUtils.getExtention(fNm);
			System.out.println("　　実行後拡張子:" + ext);
			assertEquals(ext,".");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGetDelExtention() {
		try {
			System.out.println(new Throwable().getStackTrace()[0].getMethodName());
			// テスト１
			System.out.println("　テスト１:");
			fNm = "abc.txt";
			System.out.println("　　ファイル名:" + fNm);
			fNmDelExt = ApiUtils.getDelExtention(fNm);
			System.out.println("　　実行後ファイル名:" + fNmDelExt);
			assertEquals(fNmDelExt,"abc");
			// テスト２
			System.out.println("　テスト２:");
			fNm = "abc.txt.doc";
			System.out.println("　　ファイル名:" + fNm);
			fNmDelExt = ApiUtils.getDelExtention(fNm);
			System.out.println("　　実行後ファイル名:" + fNmDelExt);
			assertEquals(fNmDelExt,"abc.txt");
			// テスト３
			System.out.println("　テスト３:");
			fNm = ".doc";
			System.out.println("　　ファイル名:" + fNm);
			fNmDelExt = ApiUtils.getDelExtention(fNm);
			System.out.println("　　実行後ファイル名:" + fNmDelExt);
			assertEquals(fNmDelExt,"");
			// テスト４
			System.out.println("　テスト４:");
			fNm = "doc.";
			System.out.println("　　ファイル名:" + fNm);
			fNmDelExt = ApiUtils.getDelExtention(fNm);
			System.out.println("　　実行後ファイル名:" + fNmDelExt);
			assertEquals(fNmDelExt,"doc");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
