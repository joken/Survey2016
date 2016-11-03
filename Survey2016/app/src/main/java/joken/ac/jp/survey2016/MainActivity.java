package joken.ac.jp.survey2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements QuestionFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


	/** Fragmentからのコールバック */
    @Override
    public void onListFragmentInteraction(QuestionContent.QuestionItem item) {
        //なんかあったらかく
    }
}
