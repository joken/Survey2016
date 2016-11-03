package joken.ac.jp.survey2016;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements QuestionFragment.OnListFragmentInteractionListener {

	@BindView(R.id.MainToolbar)
	Toolbar MainToolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		MainToolbar.setTitle(getString(R.string.app_name));
	}


	/**
	 * Fragmentからのコールバック
	 */
	@Override
	public void onListFragmentInteraction(QuestionContent.QuestionItem item) {
		//なんかあったらかく
	}
}
