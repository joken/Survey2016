package joken.ac.jp.survey2016;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	public static final String DEFAULT_ID = "200";
	public static final String CURRENT_ID_INFO = "current_id_info";
	private static final String CURRENT_ID = "current_id";

	private Context mContext;//保存ロジック実行用
	private int userId;//ID
	private String userName;//なまえ
	private short userSex;//性別
	private ArrayList<Integer> answers;//回答

	UserAnswer(){
		userSex = MALE;
		userName = "";
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

	/**
	 * 回答情報をcsvに保存する。
	 * */
	public void exportUser(){
		//フォーマット化文字列作成
		StringBuilder builder = new StringBuilder();
		builder.append(getUserId()).append(",")
			.append(getUserName()).append(",")
			.append(getUserSex()).append(",");
		for(int ans : answers){
				builder.append(ans).append(",");
		}
		builder.deleteCharAt(builder.lastIndexOf(","));
		String data = builder.toString();

		SharedPreferences pref = mContext.getSharedPreferences(QuestionFragment.CURRENT_QUESTION_TABLE, Context.MODE_PRIVATE);
		int day = pref.getInt(QuestionFragment.CURRENT_QUESTION, 0);
		String filename = "/answer_day" + day + ".csv";
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath()+filename);
		try{
			if(!file.exists()){
				if(!file.createNewFile())return;
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))){
			writer.write(data);
			writer.newLine();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	/** setter & getter */

	public int getUserId(){ return userId; }

	public void setUserSex(short usersex){
		userSex = usersex;
	}

	public short getUserSex(){
		return userSex;
	}

	public void setUserName(String name){ userName = name; }

	public String getUserName(){ return userName; }

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
		userName = "";
		answers.clear();
		SharedPreferences pref = mContext.getSharedPreferences(CURRENT_ID_INFO, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(CURRENT_ID, String.valueOf(userId));
		editor.apply();
		userId++;
	}

	/** SharedPreferencesからIDをとってくる */
	private int getCurrentID(Context context){
		SharedPreferences pref = context.getSharedPreferences(CURRENT_ID_INFO, Context.MODE_PRIVATE);
		return Integer.valueOf(pref.getString(CURRENT_ID, DEFAULT_ID));
	}

}
