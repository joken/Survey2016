package joken.ac.jp.survey2016;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing Question content for user interfaces created by
 * mi-24v.
 * <p>
 */
public class QuestionContent {

	/**
	 * An array of question items.
	 */
	public static final List<QuestionItem> ITEMS = new ArrayList<QuestionItem>();

	/**
	 * A map of question items, by ID.
	 */
	public static final Map<String, QuestionItem> ITEM_MAP = new HashMap<String, QuestionItem>();

	private static void addItem(QuestionItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * 問題を作成する。
	 * @param stream オープンした問題ファイル(csv)。
	 * */
	public static void createQuestionItem(InputStream stream) throws FileNotFoundException,IOException{
		//Headerを作成
		addItem(new QuestionItem("Dummy", "Header"));

		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		String line;
		int id = 0;
		while((line = in.readLine()) != null){
			addItem(new QuestionItem("Question."+String.valueOf(id), line));
			id++;
		}
		in.close();

		//Footerを作成
		addItem(new QuestionItem("Dummy", "Footer"));
	}

	/**
	 * A question item representing a piece of content.
	 */
	public static class QuestionItem {
		public final String id;
		public final String content;
		public final UserAnswer answer;//回答追加用のみに使用 初期化済みでなければならない。

		public QuestionItem(String id, String content) {
			this.id = id;
			this.content = content;
			this.answer = UserAnswer.THIS;
		}

		@Override
		public String toString() {
			return id + ":" +content;
		}
	}
}
