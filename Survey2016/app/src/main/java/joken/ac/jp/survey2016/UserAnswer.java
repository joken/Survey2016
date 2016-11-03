package joken.ac.jp.survey2016;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by mi-24 on 2016/11/03.
 * 回答クラス(Singleton)
 * ID割り振りのために必ず {@code setUserId(Context)} を呼び出すこと。
 */
public enum UserAnswer {
	THIS;

	public static final short MALE = 0;
	public static final short FEMALE = 1;
	public static final int DEFAULT_ID = 200;
	private static final String CURRENT_ID_INFO = "current_id_info";
	private static final String CURRENT_ID = "current_id";

	private Context mContext;//保存ロジック実行用
	private int userId;//ID
	private short userSex;//性別
	private ArrayList<Integer> answers;//回答

	UserAnswer(){
		userSex = MALE;
		answers = new ArrayList<>();
	}

	/**
	 * IDを割り振る。
	 * @param context {@link SharedPreferences} を用いるための {@link Context} 。
	 * */
	public void setUserId(Context context){
		mContext = context;
		userId = getCurrentID(mContext);
	}

	/** setter & getter */

	public void setUserSex(short usersex){
		userSex = usersex;
	}

	public short getUserSex(){
		return userSex;
	}

	public void addAnswer(int value){
		answers.add(value);
	}

	public ArrayList<Integer> getAnswers(){
		return answers;
	}

	public void setAnswer(int position, int value){
		answers.set(position, value);
	}

	/** 再初期化。 ただしIDはインクリメントされる。 */
	public void clearAnswer(){
		userSex = MALE;
		answers.clear();
		SharedPreferences pref = mContext.getSharedPreferences(CURRENT_ID_INFO, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt(CURRENT_ID, userId);
		editor.apply();
		userId++;
	}

	/** SharedPreferencesからIDをとってくる */
	private int getCurrentID(Context context){
		SharedPreferences pref = context.getSharedPreferences(CURRENT_ID_INFO, Context.MODE_PRIVATE);
		int id = pref.getInt(CURRENT_ID, DEFAULT_ID);
		return id;
	}

}
