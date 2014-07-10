package com.android.easyfitness.fragment;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.easyfitness.R;
import com.android.easyfitness.view.PieGraph;
import com.android.easyfitness.view.PieGraph.OnSliceClickedListener;
import com.android.easyfitness.view.PieSlice;

public class MacroGraphsFragment extends Fragment {

	private static final int KETOGENIC_ID = 1;
	private static final int MODERATE_ID = 2;
	private static final int LOW_FAT_ID = 3;
	private static final int LOW_CARB_ID = 4;
	private int currentDietId;
	private int numberCaloriesPerDay;
	private TextView textCaloriesPerDay;
	private TextView textDietType;
	private PieGraph pieGraphDiet;

	private Button buttonCalculate;
	private TextView textHeight;
	private TextView textWeight;
	private RadioButton radioMasculine;
	private TextView textBMR;
	private TextView textBMI;
	private TextView textWaterIntake;
	private TextView textMaxHeartRate;
	private TextView textAnaerobic;
	private TextView textIntervalTraining;
	private TextView textAerobic;

	private RelativeLayout layoutAge;
	private RelativeLayout layoutWeight;
	private RelativeLayout layoutHeight;
	private RelativeLayout layoutProfile;
	private LinearLayout layoutBodyFat;
	private Spinner spinnerExerciseLevel;
	private Spinner spinnerFormula;

	private static final double[] ketogenicDiet = new double[] { 0.05, 0.3,
			0.65 };
	private static final double[] moderateDiet = new double[] { 0.5, 0.25, 0.25 };
	private static final double[] lowFatDiet = new double[] { 0.6, 0.25, 0.15 };
	private static final double[] lowCarbDiet = new double[] { 0.25, 0.40, 0.35 };
	private double[] currentDietValues;

	public MacroGraphsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);
		currentDietId = 1;
		currentDietValues = ketogenicDiet;
		setViews();
		setListeners();
	}

	private void setViews() {
		textCaloriesPerDay = (TextView) getActivity().findViewById(
				R.id.textCaloriesPerDay);
		numberCaloriesPerDay = Integer.valueOf(textCaloriesPerDay.toString());
		textDietType = (TextView) getActivity().findViewById(R.id.textDietType);
		pieGraphDiet = (PieGraph) getActivity().findViewById(R.id.pieGraphDiet);

		layoutAge = (RelativeLayout) getActivity().findViewById(R.id.layoutAge);
		layoutWeight = (RelativeLayout) getActivity().findViewById(
				R.id.layoutWeight);
		layoutHeight = (RelativeLayout) getActivity().findViewById(
				R.id.layoutHeight);
		layoutProfile = (RelativeLayout) getActivity().findViewById(
				R.id.layoutProfile);
		layoutBodyFat = (LinearLayout) getActivity().findViewById(
				R.id.layoutBodyFat);

		textHeight = (TextView) getActivity().findViewById(R.id.textHeight);
		textWeight = (TextView) getActivity().findViewById(R.id.textWeight);
		radioMasculine = (RadioButton) getActivity().findViewById(
				R.id.radioMasculine);
		textBMR = (TextView) getActivity().findViewById(R.id.textBMR);
		textBMI = (TextView) getActivity().findViewById(R.id.textBMI);
		textWaterIntake = (TextView) getActivity().findViewById(
				R.id.textWaterIntake);
		textMaxHeartRate = (TextView) getActivity().findViewById(
				R.id.textMaxHeartRate);
		textAnaerobic = (TextView) getActivity().findViewById(
				R.id.textAnaerobic);
		textIntervalTraining = (TextView) getActivity().findViewById(
				R.id.textIntervalTraining);
		textAerobic = (TextView) getActivity().findViewById(R.id.textAerobic);
		buttonCalculate = (Button) getActivity().findViewById(
				R.id.buttonCalculate);

		spinnerExerciseLevel = (Spinner) getActivity().findViewById(
				R.id.spinnerExerciseLevel);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.exercise_level_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerExerciseLevel.setAdapter(adapter);

		spinnerFormula = (Spinner) getActivity().findViewById(
				R.id.spinnerFormula);
		spinnerFormula
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						String item = (String) parent.getItemAtPosition(pos);
						if (item.equals("Katch-McArdle (Body Fat %)")) {
							layoutBodyFat.setVisibility(View.VISIBLE);
						} else {
							layoutBodyFat.setVisibility(View.GONE);
						}
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		adapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.formulas_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerFormula.setAdapter(adapter);

		drawDietGraph();
	}

	private void setListeners() {
		buttonCalculate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
	}

	private void drawDietGraph() {
		final Resources resources = getResources();
		int indexOfSlice = 0;

		Double carbsDouble = numberCaloriesPerDay * currentDietValues[0];
		Double proteinDouble = numberCaloriesPerDay * currentDietValues[1];
		Double fatDouble = numberCaloriesPerDay * currentDietValues[2];

		int carbsInt = carbsDouble.intValue();
		int proteinInt = proteinDouble.intValue();
		int carbsInt = carbsDouble.intValue();

		for (Option option : optionList) {
			int optionResults = resultsPerOption.get(indexOfSlice);
			PieSlice slice = new PieSlice();
			slice.setColor(colorList.get(indexOfSlice));
			slice.setSelectedColor(resources.getColor(R.color.blue));
			slice.setValue(optionResults);
			if (optionResults > 0) {
				pieGraphDiet.addSlice(slice);
			}
			indexOfSlice++;
		}

		pieGraphDiet.setOnSliceClickedListener(new OnSliceClickedListener() {

			@Override
			public void onClick(final int index, int x, int y, int thickness) {
				Log.i(LOG_TAG, "setOnSliceClickedListener index clicked "
						+ index);
				showPercentagePie(x, y, colorList.get(index),
						percentages.get(index), thickness);
				PollResultsActivity.showFragment(index);
			}
		});
	}
}
