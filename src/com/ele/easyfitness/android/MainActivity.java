package com.ele.easyfitness.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.ele.easyfitness.android.fragment.CalculatorFragment;
import com.ele.easyfitness.android.fragment.MacroGraphsFragment;

public class MainActivity extends ActionBarActivity {

	private CalculatorFragment calculatorFragment;
	private MacroGraphsFragment macroGraphsFragment;
	private RelativeLayout layoutCalculator;
	private RelativeLayout layoutMacroGraphs;
	private FragmentManager fragmentManager;
	private View highlightMacroGraphs;
	private View highlightCalculator;
	private int minimumCalories = 1571;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layoutCalculator = (RelativeLayout) findViewById(R.id.layoutCalculator);
		layoutMacroGraphs = (RelativeLayout) findViewById(R.id.layoutMacroGraphs);
		fragmentManager = getSupportFragmentManager();
		calculatorFragment = (CalculatorFragment) fragmentManager
				.findFragmentById(R.id.calculatorFragment);
		macroGraphsFragment = (MacroGraphsFragment) fragmentManager
				.findFragmentById(R.id.macroGraphsFragment);
		highlightCalculator = (View) findViewById(R.id.highlightCalculator);
		highlightMacroGraphs = (View) findViewById(R.id.highlightMacroGraphs);
		// fragmentManager.beginTransaction().hide(pollListFragment).commit();
		onClickChangeTab(layoutCalculator);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onClickChangeTab(View view) {
		switch (view.getId()) {
		case R.id.layoutCalculator:
			highlightMacroGraphs.setVisibility(View.INVISIBLE);
			highlightCalculator.setVisibility(View.VISIBLE);
			fragmentManager.beginTransaction().show(calculatorFragment)
					.hide(macroGraphsFragment).commit();
			break;

		case R.id.layoutMacroGraphs:
			highlightMacroGraphs.setVisibility(View.VISIBLE);
			highlightCalculator.setVisibility(View.INVISIBLE);
			fragmentManager.beginTransaction().show(macroGraphsFragment)
					.hide(calculatorFragment).commit();

			break;

		default:
			break;
		}
	}

	public int getMinimumCalories() {
		return minimumCalories;
	}

	public void setMinimumCalories(int minimumCalories) {
		this.minimumCalories = minimumCalories;
		macroGraphsFragment.onMinimumCaloriesChanged(minimumCalories);
	}
}
