package joken.ac.jp.survey2016;

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

	private static final int COUNT = 25;

	static {
		// Add some sample items.
		for (int i = 1; i <= COUNT; i++) {
			addItem(createDummyItem(i));
		}
	}

	private static void addItem(QuestionItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	private static QuestionItem createDummyItem(int position) {
		return new QuestionItem(String.valueOf(position), "Item " + position, makeDetails(position));
	}

	private static String makeDetails(int position) {
		StringBuilder builder = new StringBuilder();
		builder.append("Details about Item: ").append(position);
		for (int i = 0; i < position; i++) {
			builder.append("\nMore details information here.");
		}
		return builder.toString();
	}

	/**
	 * A question item representing a piece of content.
	 */
	public static class QuestionItem {
		public final String id;
		public final String content;
		public final String answer;

		public QuestionItem(String id, String content, String answer) {
			this.id = id;
			this.content = content;
			this.answer = answer;
		}

		@Override
		public String toString() {
			return id + ":" +content;
		}
	}
}
