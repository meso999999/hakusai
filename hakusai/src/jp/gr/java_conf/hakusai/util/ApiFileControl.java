package jp.gr.java_conf.hakusai.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

import jp.gr.java_conf.hakusai.exeption.AppException;

import org.apache.commons.lang.StringUtils;

public abstract class ApiFileControl {

	/** 読み込みファイル名 */
	private String frName = "";

	/** 書き込みファイル名 */
	private String fwName = "";

	/** 一時ファイル名 */
	private String ftName = "";

	/** 行数 */
	private int fLine = 1;

	/** 改行コード  */
	private String fLineCode = "";

	/** 現在行 */
	private int crntLine = 1;

	/**
	 * 読み込みファイル名と書き込みファイル名が同じ場合
	 *
	 * @param fName
	 *            ファイル名
	 */
	protected ApiFileControl(String fName) throws AppException {
		this.fwName = fName;
		this.frName = fName;
		this.ftName = fName + "_" + ApiUtils.getRndAZStr(30);

		readFile();
	}

	/**
	 * 読み込みファイルと書き込みファイルが異なる場合
	 *
	 * @param frName
	 *            読み込みファイル
	 * @param fwName
	 *            書き込みファイル
	 */
	protected ApiFileControl(String frName, String fwName) throws AppException {
		this.fwName = frName;
		this.frName = fwName;
		this.ftName = frName + "_" + ApiUtils.getRndAZStr(30);

		readFile();
	}

	/** */
	public void fileControl() throws AppException {
		readFile();
		editFile();
		writeFile();
	}

	/**
	 * 論理チェックを行う。
	 */
	private void chkFile() throws AppException {
		if (StringUtils.isEmpty(frName)) {
			throw new AppException("読み込みファイル名を設定して下さい。");
		}
		if (StringUtils.isEmpty(frName)) {
			throw new AppException("書き込みファイル名を設定して下さい。");
		}
		if (StringUtils.isEmpty(ftName)) {
			throw new AppException("一時ファイル名を設定して下さい。");
		}
		if (StringUtils.isEmpty(fLineCode)) {
			throw new AppException("改行コードを設定して下さい。");
		}
	}

	/**
	 * ファイルを読み込み、一時ファイルを作成する。<br />
	 * 以後修正は一時ファイルを使用して行う。
	 **/
	private void readFile() throws AppException {
		chkFile();

		try {
			// 読み込みファイル
			File rf = new File(frName);
			FileReader fr = new FileReader(rf);
			LineNumberReader inr = new LineNumberReader(fr);

			// 書き込みファイル
			File wf = new File(ftName);
			FileWriter fw = new FileWriter(wf);
			BufferedWriter bw = new BufferedWriter(fw);

			String line = "";

			int lineNum = 0;
			while ((line = inr.readLine()) != null) {
				lineNum++;
				bw.write(line);
				bw.write(fLineCode);
			}
			this.fLine = lineNum;
		} catch (IOException e) {
			throw new AppException(e);
		}
	};

	/**
	 * ファイルを編集する。
	 */
	private void editFile() throws AppException {
		chkFile();
	};

	/**
	 * ファイルを編集する。 指定された行を削除
	 */
	private void delLine(int delLen) throws AppException {
		try {
			// 読み込みファイル
			File rf = new File(frName);
			FileReader fr = new FileReader(rf);
			LineNumberReader inr = new LineNumberReader(fr);

			// 書き込みファイル
			File wf = new File(ftName);
			FileWriter fw = new FileWriter(wf);
			BufferedWriter bw = new BufferedWriter(fw);

			String line = "";

			int lineRNum = 0;
			int lineWNum = 0;
			while ((line = inr.readLine()) != null) {
				lineRNum++;
				if (lineRNum != delLen) {
					bw.write(line);
					bw.write(fLineCode);
					lineWNum++;
				}
			}
			this.fLine = lineWNum;
		} catch (IOException e) {
			throw new AppException(e);
		}
	}

	/**
	 * ファイルを編集する。 指定された行の指定された箇所の文字（前からカウント）を削除
	 */
	private void delCharFromStart(int delLen, int delChar) throws AppException {
		try {
			// 読み込みファイル
			File rf = new File(frName);
			FileReader fr = new FileReader(rf);
			LineNumberReader inr = new LineNumberReader(fr);

			// 書き込みファイル
			File wf = new File(ftName);
			FileWriter fw = new FileWriter(wf);
			BufferedWriter bw = new BufferedWriter(fw);

			String line = "";

			int lineRNum = 0;
			int lineWNum = 0;
			while ((line = inr.readLine()) != null) {
				lineRNum++;
				if (lineRNum != delLen) {
					StringBuffer sBuf = new StringBuffer(line);
					sBuf.deleteCharAt(delChar);
					bw.write(sBuf.toString());
					bw.write(fLineCode);
					lineWNum++;
				}
			}
			this.fLine = lineWNum;
		} catch (IOException e) {
			throw new AppException(e);
		}
	}

	/**
	 * ファイルを編集する。 指定された行の指定された箇所の文字（前からカウント）を削除
	 */
	private void delCharFromEnd(int delLen, int delChar) throws AppException {
//		try {
//			// 読み込みファイル
//			File rf = new File(frName);
//			FileReader fr = new FileReader(rf);
//			LineNumberReader inr = new LineNumberReader(fr);
//
//			// 書き込みファイル
//			File wf = new File(ftName);
//			FileWriter fw = new FileWriter(wf);
//			BufferedWriter bw = new BufferedWriter(fw);
//
//			String line = "";
//
//			int lineRNum = 0;
//			int lineWNum = 0;
//			while ((line = inr.readLine()) != null) {
//				lineRNum++;
//				if (lineRNum != delLen) {
//					int lineNum =
//					StringBuffer sBuf = new StringBuffer(line);
//					sBuf.deleteCharAt(delChar);
//					bw.write(sBuf.toString());
//					bw.write(fLineCode);
//					lineWNum++;
//				}
//			}
//			this.fLine = lineWNum;
//		} catch (IOException e) {
//			throw new AppException(e);
//		}
	}

	/**
	 * ファイル書き込み。 書き込みが終了した場合は一時ファイルを削除する。
	 */
	private void writeFile() throws AppException {
		chkFile();

		try {
			// 読み込みファイル
			File rf = new File(ftName);
			FileReader fr = new FileReader(rf);
			LineNumberReader inr = new LineNumberReader(fr);

			// 書き込みファイル
			File wf = new File(fwName);
			FileWriter fw = new FileWriter(wf);
			BufferedWriter bw = new BufferedWriter(fw);

			String line = "";

			while ((line = inr.readLine()) != null) {
				bw.write(line);
				bw.write(fLineCode);
			}
		} catch (IOException e) {
			throw new AppException(e);
		}
	}

}
