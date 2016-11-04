package joken.ac.jp.survey2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements QuestionFragment.OnListFragmentInteractionListener {

	@BindView(R.id.MainToolbar)
	Toolbar MainToolbar;

	private int toolCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		MainToolbar.setTitle(getString(R.string.app_name));
		MainToolbar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toolCount++;
				if(toolCount >= 10){
					toolCount = 0;
					Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(MainActivity.this, SettingsActivity.class));
				}else{
					Toast.makeText(getApplicationContext(), "Count:"+toolCount, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}


	/**
	 * Fragmentからのコールバック
	 */
	@Override
	public void onListFragmentInteraction(QuestionContent.QuestionItem item) {
		//なんかあったらかく
	}
}
