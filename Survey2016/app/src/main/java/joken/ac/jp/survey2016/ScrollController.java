package joken.ac.jp.survey2016;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

/**
 * Created by mi_24 on 2016/11/03.
 * スクロール・コントローラ
 */
public class ScrollController implements RecyclerView.OnItemTouchListener {
	private boolean isScrollable = false;
	@Override
	public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
		return isScrollable;//trueだとユーザからのスクロールが不可。
	}

	@Override
	public void onTouchEvent(RecyclerView rv, MotionEvent e) {
	}

	@Override
	public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
	}

	public void enableScroll(){
		isScrollable = false;
	}

	public void diableScroll(){
		isScrollable = true;
	}
}
