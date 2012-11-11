/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.gr.java_conf.hakusai.excel.dump.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import jp.gr.java_conf.hakusai.exeption.AppException;
import jp.gr.java_conf.hakusai.util.ApiUtils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Create SQL文を生成するクラス
 *
 * @author k-osanai
 */
public class CreateSql {

	/** 読み込みパス */
    private static final String rDirPath = "./resource/read/";
    private static final String rFileName = "create_sample.xlsx";

	/** 書き込みパス */
    private static final String wDirPath = "./resource/write/";
    private static final String wExtention = ".sql";

    /** シート名 */
    private static final String rSheetName = "category";

	/** テーブル論理名 行番号 */
	private static final int tblLgcNmGyo = 3;

	/** テーブル論理名 列番号 */
	private static final int tblLgcNmKt = 2;

	/** テーブル物理名 行番号 */
	private static final int tblPysNmGyo = 4;

	/** テーブル物理名 列番号 */
	private static final int tblPysNmKt = 2;

	/** テーブルコメント 行番号 */
	private static final int tblCmntGyo = 5;

	/** テーブルコメント 列番号 */
	private static final int tblCmntKt = 2;

	/** 項目 行番号 */
	private static final int kmkStrtGyo = 8;

	/** 項目 物理名 */
	private static final int kmkLgcNmKt = 1;

	/** 項目 論理名 */
	private static final int kmkPysNmKt = 2;

	/** 項目 型名 */
	private static final int kmkTypKt = 3;

	/** 項目 桁数 */
	private static final int kmkKtKt = 4;

	/** 項目 初期値 */
	private static final int kmkInitKt = 5;

	/** 項目 プライマリキー */
	private static final int kmkPrmryKeyKt = 6;

	/** 項目 not null */
	private static final int kmkNtNllKt = 9;

    /**
     * @param args
     */
    public static void main(String[] args) {

    	// 情報をログで出力
        System.out.println("システム情報");
        System.out.println("エンコード：" + System.getProperty( "file.encoding"));
        System.out.println();

        // 書き込みファイルを開く
        String wFileName = ApiUtils.getDelExtention(rFileName) + wExtention;
        File file = new File(wDirPath + wFileName);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        } catch(IOException e) {
        	e.printStackTrace();
        }

        FileInputStream in = null;
        Workbook wb = null;

        try {
            in = new FileInputStream(rDirPath + rFileName);
            wb = WorkbookFactory.create(in);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }

        // シートを読み込み
        Sheet sheet = wb.getSheet(rSheetName);

        try {
        	// クリエイト文生成
            CreateLogic logic = new CreateLogic(
            		sheet,
            		tblPysNmGyo,
            		tblPysNmKt,
            		tblLgcNmGyo,
            		tblLgcNmKt,
            		tblCmntGyo,
            		tblCmntKt,
            		kmkStrtGyo,
            		kmkPysNmKt,
            		kmkLgcNmKt,
            		kmkTypKt,
            		kmkKtKt,
            		kmkInitKt,
            		kmkPrmryKeyKt,
            		kmkNtNllKt);
            // クリエイト文取得
            String createStr = logic.getCreateSntnc();

            // ファイルに出力
            pw.write(createStr);

            pw.close();

        } catch (AppException e) {
        	e.printStackTrace();
        }

    }
}
