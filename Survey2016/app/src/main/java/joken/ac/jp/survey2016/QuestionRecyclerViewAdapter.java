package joken.ac.jp.survey2016;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import joken.ac.jp.survey2016.QuestionFragment.OnListFragmentInteractionListener;
import joken.ac.jp.survey2016.QuestionContent.QuestionItem;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link QuestionItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private final List<QuestionItem> mValues;
	private final OnListFragmentInteractionListener mListener;
	private RecyclerView mRecyclerView;

	/** View生成時にどのLayoutを使うか特定する一意な識別子 */
	private static final int VIEW_HEADER = 4545;
	private static final int VIEW_FOOTER = 1919;

	public QuestionRecyclerViewAdapter(List<QuestionItem> items, OnListFragmentInteractionListener listener, RecyclerView recyclerView) {
		mValues = items;
		mListener = listener;
		mRecyclerView = recyclerView;
	}

	/** ViewHolder作成(LayoutをInflateする) */
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view;
		switch (viewType){
			case VIEW_HEADER:
				view = inflater.inflate(R.layout.fragment_header, parent, false);
				return new HeaderViewHolder(view);
			case VIEW_FOOTER:
				view = inflater.inflate(R.layout.fragment_footer, parent, false);
				return new FooterViewHolder(view);
			default://通常セル
				view = inflater.inflate(R.layout.fragment_question, parent, false);
				return new QuestionViewHolder(view);
		}
	}

	/** ViewHolderとViewのBind(ここでViewをつくる) */
	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
		if(holder instanceof HeaderViewHolder){
			((HeaderViewHolder)holder).onBindViewHolder(position);
		}
		if(holder instanceof FooterViewHolder){
			((FooterViewHolder)holder).onBindViewHolder();
		}
		if(holder instanceof QuestionViewHolder){
			((QuestionViewHolder)holder).onBindViewHolder(mValues.get(position), position);
		}
	}

	/** ViewHolderのTypeを決定する */
	@Override
	public int getItemViewType(int position) {
		if(position == 0){//ヘッダ
			return VIEW_HEADER;
		}else if(position == mValues.size() - 1){//フッタ
			return VIEW_FOOTER;
		}else{//問題
			return 0;
		}
	}

	@Override
	public int getItemCount() {
		return mValues.size();
	}

	/** 問題のViewHolder */
	class QuestionViewHolder extends RecyclerView.ViewHolder {
		public final View mView;
		@BindView(R.id.question_id) TextView mIdView;
		@BindView(R.id.content) TextView mContentView;
		@BindView(R.id.answer_group) RadioGroup mAnswerGroup;
		@BindView(R.id.answer_button) BootstrapButton answerButton;
		public QuestionItem mItem;

		public QuestionViewHolder(View view) {
			super(view);
			mView = view;
			ButterKnife.bind(this, mView);
		}

		public void onBindViewHolder(QuestionItem item, final int position){
			this.mIdView.setText(item.id);
			this.mContentView.setText(item.content);

			this.mAnswerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if(UserAnswer.THIS.getAnswers().size() < position){
						UserAnswer.THIS.addAnswer(checkedId);
					}else{
						UserAnswer.THIS.setAnswer(position, checkedId);
					}
				}
			});

			this.answerButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(position != QuestionContent.ITEMS.size() - 1){
						mRecyclerView.smoothScrollToPosition(position+1);
					}
				}
			});

			this.mView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (null != mListener) {
						// Notify the active callbacks interface (the activity, if the
						// fragment is attached to one) that an item has been selected.
						mListener.onListFragmentInteraction(mItem);
					}
				}
			});
		}

		@Override
		public String toString() {
			return super.toString() + "@" + mIdView.getText();
		}
	}

	/** ヘッダのViewHolder */
	class HeaderViewHolder extends RecyclerView.ViewHolder{
		@BindView(R.id.startButton) BootstrapButton startButton;
		@BindView(R.id.userNameText) BootstrapEditText userNameText;
		@BindView(R.id.maleButton) BootstrapButton maleButton;
		@BindView(R.id.femaleButton) BootstrapButton femaleButton;

		public HeaderViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		public void onBindViewHolder(final int position){
			userNameText.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					if(s.length() != 0 && (maleButton.isSelected() || femaleButton.isSelected())){
						startButton.setEnabled(true);
					}else{
						startButton.setEnabled(false);
					}
				}

				@Override
				public void afterTextChanged(Editable s) {
				}
			});

			BootstrapButton.OnCheckedChangedListener buttonlistener = new BootstrapButton.OnCheckedChangedListener() {
				@Override
				public void OnCheckedChanged(BootstrapButton bootstrapButton, boolean isChecked) {
					if(isChecked && userNameText.getText().length() != 0){
						startButton.setEnabled(true);
					}else{
						startButton.setEnabled(false);
					}
				}
			};

			maleButton.setOnCheckedChangedListener(buttonlistener);
			femaleButton.setOnCheckedChangedListener(buttonlistener);

			startButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					short userSex = UserAnswer.MALE;
					if(maleButton.isSelected())userSex = UserAnswer.MALE;
					if(femaleButton.isSelected())userSex = UserAnswer.FEMALE;

					UserAnswer.THIS.setUserName(userNameText.getText().toString());
					UserAnswer.THIS.setUserSex(userSex);

					mRecyclerView.smoothScrollToPosition(position+1);
				}
			});
		}
	}

	/** フッタのViewHolder */
	class FooterViewHolder extends RecyclerView.ViewHolder{
		@BindView(R.id.UserID_View) TextView userIdView;
		@BindView(R.id.endButton) BootstrapButton endButton;

		public FooterViewHolder(View itemView){
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		public void onBindViewHolder(){
			userIdView.setText(UserAnswer.THIS.getUserId());
			endButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					UserAnswer.THIS.exportUser();
					UserAnswer.THIS.clearAnswer();
					mRecyclerView.scrollToPosition(0);
				}
			});
		}
	}

}
