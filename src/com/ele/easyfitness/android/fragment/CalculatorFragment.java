package com.ele.easyfitness.android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ele.easyfitness.android.MainActivity;
import com.ele.easyfitness.android.R;
import com.ele.easyfitness.android.dialog.InfoDialog;
import com.ele.easyfitness.android.dialog.NumberPickerDialog;
import com.ele.easyfitness.android.dialog.NumberPickerDialog.PickerListener;

public class CalculatorFragment extends Fragment implements PickerListener {

	private Button buttonCalculate;
	private TextView textAge;
	private TextView textBodyFat;
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
	private ScrollView scrollView;
	private MainActivity mainActivity;

	public CalculatorFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		scrollView = (ScrollView) rootView;
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);
		mainActivity = (MainActivity) getActivity();
		setViews();
		setListeners();
	}

	private void setViews() {
		layoutAge = (RelativeLayout) getActivity().findViewById(R.id.layoutAge);
		layoutWeight = (RelativeLayout) getActivity().findViewById(
				R.id.layoutWeight);
		layoutHeight = (RelativeLayout) getActivity().findViewById(
				R.id.layoutHeight);
		layoutProfile = (RelativeLayout) getActivity().findViewById(
				R.id.layoutProfile);
		layoutBodyFat = (LinearLayout) getActivity().findViewById(
				R.id.layoutBodyFat);
		textAge = (TextView) getActivity().findViewById(R.id.textAge);
		textBodyFat = (TextView) getActivity().findViewById(R.id.textBodyFat);
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
	}

	private void setListeners() {
		layoutAge.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				NumberPickerDialog dialog = new NumberPickerDialog(
						getActivity(), CalculatorFragment.this, layoutAge
								.getId(), Integer.parseInt(textAge.getText()
								.toString()));
				dialog.show();
			}
		});
		layoutHeight.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				final String[] splittedHeight = textHeight.getText().toString()
						.split(" ");
				NumberPickerDialog dialog = new NumberPickerDialog(
						getActivity(), CalculatorFragment.this, layoutHeight
								.getId(), Integer.parseInt(splittedHeight[0]));
				dialog.show();
			}
		});
		layoutWeight.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final String[] splittedWeight = textWeight.getText().toString()
						.split(" ");
				NumberPickerDialog dialog = new NumberPickerDialog(
						getActivity(), CalculatorFragment.this, layoutWeight
								.getId(), Integer.parseInt(splittedWeight[0]));
				dialog.show();
			}
		});

		layoutProfile.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				InfoDialog dialog = new InfoDialog(getActivity(),
						InfoDialog.FORMULAS);
				dialog.show();

			}
		});
		layoutBodyFat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final String[] splittedBodyFat = textBodyFat.getText()
						.toString().split("%");
				NumberPickerDialog dialog = new NumberPickerDialog(
						getActivity(), CalculatorFragment.this, layoutBodyFat
								.getId(), Integer.parseInt(splittedBodyFat[0]));
				dialog.show();
			}
		});
		buttonCalculate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				calculate();

			}
		});

		RelativeLayout relativeLayout = (RelativeLayout) getActivity()
				.findViewById(R.id.layoutBMI);
		relativeLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				InfoDialog dialog = new InfoDialog(getActivity(),
						InfoDialog.BMI);
				dialog.show();

			}
		});
		relativeLayout = (RelativeLayout) getActivity().findViewById(
				R.id.layoutBMR);
		relativeLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				InfoDialog dialog = new InfoDialog(getActivity(),
						InfoDialog.BMR);
				dialog.show();

			}
		});
		relativeLayout = (RelativeLayout) getActivity().findViewById(
				R.id.layoutWater);
		relativeLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				InfoDialog dialog = new InfoDialog(getActivity(),
						InfoDialog.WATER_INTAKE);
				dialog.show();

			}
		});
		relativeLayout = (RelativeLayout) getActivity().findViewById(
				R.id.layoutHeartRate);
		relativeLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				InfoDialog dialog = new InfoDialog(getActivity(),
						InfoDialog.TARGET_HEART_RATE);
				dialog.show();

			}
		});

		relativeLayout = (RelativeLayout) getActivity().findViewById(
				R.id.layoutHeartRate);
		relativeLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				InfoDialog dialog = new InfoDialog(getActivity(),
						InfoDialog.TARGET_HEART_RATE);
				dialog.show();

			}
		});
	}

	private void calculate() {
		focusOnView();
		try {
			calculateBMI();
			calculateBMR();
			calculateWaterIntake();
			calculateHeartRates();
		} catch (Exception e) {
			Log.e("calculator", "calculateException", e);
		}

	}

	private void calculateHeartRates() {
		int ageNumber = Integer.parseInt(textAge.getText().toString());
		int maxHeart = 220 - ageNumber;
		textMaxHeartRate.setText(String.valueOf(maxHeart));
		Double ninetyPercent = maxHeart * 0.9;
		textAnaerobic.setText(String.valueOf(ninetyPercent.intValue()));
		Double eightyPercent = maxHeart * 0.8;
		textIntervalTraining.setText(String.valueOf(eightyPercent.intValue()));
		Double seventyPercent = maxHeart * 0.7;
		textAerobic.setText(String.valueOf(seventyPercent.intValue()));
		Double sixtyPercent = maxHeart * 0.6;
		textIntervalTraining.setText(String.valueOf(sixtyPercent.intValue()));

	}

	private void calculateBMI() {
		String weightString = textWeight.getText().toString();
		int indexofSpace = weightString.indexOf(" ");
		weightString = weightString.substring(0, indexofSpace);
		Double weightNumber = Double.parseDouble(weightString);
		Log.i("weight", weightString);

		String heightString = textHeight.getText().toString();
		indexofSpace = heightString.indexOf(" ");
		heightString = heightString.substring(0, indexofSpace);
		Double heightNumber = Double.parseDouble(heightString);
		Log.i("calculate", heightString);
		Log.i("calculate", heightNumber + "");

		String i = String.format("BMI is = %1$s / %2$s * %3$s", weightNumber,
				heightNumber / 100.00, heightNumber / 100.00);
		Log.i("calculate", i);
		Double bmi = weightNumber
				/ ((heightNumber / 100.00) * (heightNumber / 100.00));
		String bmiString = String.valueOf(bmi);
		if (bmiString.length() > 4) {
			bmiString = String.valueOf(bmi).substring(0, 4);
		} else if (bmiString.length() == 4) {
			bmiString = String.valueOf(bmi).substring(0, 3);
		} else if (bmiString.length() == 3) {
			bmiString = String.valueOf(bmi).substring(0, 2);
		} else {
			bmiString = String.valueOf(bmi).substring(0, 1);
		}
		textBMI.setText(bmiString);
	}

	private void calculateBMR() {
		boolean isMasculine = radioMasculine.isChecked();
		int ageNumber = Integer.parseInt(textAge.getText().toString());

		String heightString = textHeight.getText().toString();
		int indexofSpace = heightString.indexOf(" ");
		heightString = heightString.substring(0, indexofSpace);
		Double heightNumber = Double.parseDouble(heightString);

		String weightString = textWeight.getText().toString();
		indexofSpace = weightString.indexOf(" ");
		weightString = weightString.substring(0, indexofSpace);
		Double weightNumber = Double.parseDouble(weightString);

		String formulaString = spinnerFormula.getSelectedItem().toString();

		Double bmrDouble = 0.0;

		if (formulaString.equals("Mifflin-St Jeor")) {
			if (isMasculine) {
				bmrDouble = (10 * weightNumber) + (6.25 * heightNumber)
						- (5 * ageNumber) + 5;
			} else {
				bmrDouble = (10 * weightNumber) + (6.25 * heightNumber)
						- (5 * ageNumber) - 161;
			}
		} else if (formulaString.equals("Harris-Benedict")) {
			if (isMasculine) {
				bmrDouble = 88.362 + (13.397 * weightNumber)
						+ (4.799 * heightNumber) - (5.677 * ageNumber);
			} else {
				bmrDouble = 447.593 + (9.247 * weightNumber)
						+ (3.098 * heightNumber) - (4.330 * ageNumber);
			}
		} else if (formulaString.equals("Katch-McArdle (Body Fat %)")) {
			Double bodyFatDouble = Double.parseDouble(textBodyFat.getText()
					.toString());
			Double leanBodyMass = weightNumber
					- (weightNumber * (bodyFatDouble / 100.00));
			bmrDouble = 370 + (21.6 * leanBodyMass);
		}
		int bmrInt = bmrDouble.intValue();
		mainActivity.setMinimumCalories(bmrInt);
		textBMR.setText(String.valueOf(bmrInt) + " cal/day");
	}

	private void calculateWaterIntake() {
		String weightString = textWeight.getText().toString();
		int indexofSpace = weightString.indexOf(" ");
		weightString = weightString.substring(0, indexofSpace);
		Double weightNumber = Double.parseDouble(weightString);
		Double poundsDouble = weightNumber * 2.2046;
		Double waterOuncesDouble = poundsDouble / 2.00;
		Double waterMlDouble = waterOuncesDouble * 29.57;
		int waterMlInt = waterMlDouble.intValue();
		textWaterIntake.setText(waterMlInt + " ml");
	}

	@Override
	public void onConfirm(int resourceToUpdate, String textToUpdate) {
		switch (resourceToUpdate) {
		case R.id.layoutAge:
			textAge.setText(textToUpdate);
			break;

		case R.id.layoutHeight:
			textHeight.setText(textToUpdate + " cm");
			break;

		case R.id.layoutWeight:
			textWeight.setText(textToUpdate + " kg");
			break;

		case R.id.layoutBodyFat:
			textBodyFat.setText(textToUpdate);
			break;

		default:
			break;
		}

	}

	private final void focusOnView() {
		TextView textWaterIntakee = textWaterIntake;
		int i = textWaterIntakee.getBottom();
		final Float f = textAerobic.getX();
		final Float f2 = textAerobic.getX();

		new Handler().post(new Runnable() {
			@Override
			public void run() {
				scrollView.smoothScrollTo(f.intValue(), f2.intValue());
			}
		});
	}
}
