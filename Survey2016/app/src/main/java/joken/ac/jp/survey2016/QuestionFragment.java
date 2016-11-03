package joken.ac.jp.survey2016;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import joken.ac.jp.survey2016.QuestionContent.QuestionItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class QuestionFragment extends Fragment {
	public static final String CURRENT_QUESTION_TABLE = "current_question_table";
	public static final String CURRENT_QUESTION = "current_question";

	private OnListFragmentInteractionListener mListener;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public QuestionFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		UserAnswer.THIS.setUserId(getContext());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_question_list, container, false);

		// Set the adapter
		if (view instanceof RecyclerView) {
			Context context = view.getContext();
			RecyclerView recyclerView = (RecyclerView) view;
			LinearLayoutManager manager = new LinearLayoutManager(context);
			manager.setOrientation(LinearLayoutManager.HORIZONTAL);
			recyclerView.setLayoutManager(manager);
			recyclerView.addOnItemTouchListener(new ScrollController());
			//問題読み込み
			loadQuestions();
			recyclerView.setAdapter(new QuestionRecyclerViewAdapter(QuestionContent.ITEMS, mListener, recyclerView));
		}
		return view;
	}

	private void loadQuestions(){
		SharedPreferences pref = getContext().getSharedPreferences(CURRENT_QUESTION_TABLE, Context.MODE_PRIVATE);
		int currentQuestion = pref.getInt(CURRENT_QUESTION, 0);
		int questionRes;
		switch (currentQuestion){
			case 0:
				questionRes = R.raw.day1;
				break;
			case 1:
				questionRes = R.raw.day2;
				break;
			default:
				questionRes = R.raw.day1;
		}
		try{
			InputStream s = getResources().openRawResource(questionRes);
			QuestionContent.createQuestionItem(s);
		}catch(IOException e){
			e.printStackTrace();
		}
	}


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnListFragmentInteractionListener) {
			mListener = (OnListFragmentInteractionListener) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnListFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p/>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnListFragmentInteractionListener {
		// TODO: Update argument type and name
		void onListFragmentInteraction(QuestionItem item);
	}
}
