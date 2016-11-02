package joken.ac.jp.survey2016;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import joken.ac.jp.survey2016.QuestionFragment.OnListFragmentInteractionListener;
import joken.ac.jp.survey2016.QuestionContent.QuestionItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link QuestionItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private final List<QuestionItem> mValues;
	private final OnListFragmentInteractionListener mListener;

	/** View生成時にどのLayoutを使うか特定する一意な識別子 */
	private static final int VIEW_HEADER = 4545;
	private static final int VIEW_FOOTER = 1919;

	public QuestionRecyclerViewAdapter(List<QuestionItem> items, OnListFragmentInteractionListener listener) {
		mValues = items;
		mListener = listener;
	}

	/** ViewHolder作成(LayoutをInflateする) */
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view;
		switch (viewType){
			case VIEW_HEADER://TODO ヘッダレイアウト作成
				view = inflater.inflate(R.layout.fragment_question, parent, false);
				return new HeaderViewHolder(view);
			case VIEW_FOOTER://TODO フッタレイアウト作成
				view = inflater.inflate(R.layout.fragment_question, parent, false);
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
			((HeaderViewHolder)holder).onBindViewHolder();
		}
		if(holder instanceof FooterViewHolder){
			((FooterViewHolder)holder).onBindViewHolder();
		}
		if(holder instanceof QuestionViewHolder){
			((QuestionViewHolder)holder).onBindViewHolder(mValues.get(position));
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
		public final TextView mIdView;
		public final TextView mContentView;
		public QuestionItem mItem;

		public QuestionViewHolder(View view) {
			super(view);
			mView = view;
			mIdView = (TextView) view.findViewById(R.id.question_id);
			mContentView = (TextView) view.findViewById(R.id.content);
		}

		public void onBindViewHolder(QuestionItem item){
			this.mIdView.setText(item.id);
			this.mContentView.setText(item.content);

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
	class HeaderViewHolder extends RecyclerView.ViewHolder{//TODO 中身実装

		public HeaderViewHolder(View itemView) {
			super(itemView);
		}

		public void onBindViewHolder(){}
	}

	/** フッタのViewHolder */
	class FooterViewHolder extends RecyclerView.ViewHolder{//TODO 中身実装

		public FooterViewHolder(View itemView){
			super(itemView);
		}

		public void onBindViewHolder(){}
	}

}
