package com.ele.easyfitness.android.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ele.easyfitness.android.MainActivity;
import com.ele.easyfitness.android.R;
import com.ele.easyfitness.android.SettingsActivity;
import com.ele.easyfitness.android.dialog.DietPickerDialog;
import com.ele.easyfitness.android.dialog.DietPickerDialog.DietListener;
import com.ele.easyfitness.android.view.PieGraph;
import com.ele.easyfitness.android.view.PieGraph.OnSliceClickedListener;
import com.ele.easyfitness.android.view.PieSlice;

public class MacroGraphsFragment extends Fragment implements DietListener {

	private static final String LOG_TAG = MacroGraphsFragment.class
			.getSimpleName();
	public static final int KETOGENIC_ID = 1;
	public static final int MODERATE_ID = 2;
	public static final int LOW_FAT_ID = 3;
	public static final int LOW_CARB_ID = 4;
	public static final double[] ketogenicDiet = new double[] { 0.05, 0.3, 0.65 };
	public static final double[] moderateDiet = new double[] { 0.5, 0.25, 0.25 };
	public static final double[] lowFatDiet = new double[] { 0.6, 0.25, 0.15 };
	public static final double[] lowCarbDiet = new double[] { 0.25, 0.40, 0.35 };
	private int mealsPerDay;
	private int carbsPerMeal;
	private int proteinPerMeal;
	private int fatPerMeal;
	private TextView textCaloriesPerDay;
	private TextView textDietType;
	private TextView textCarbsDay;
	private TextView textProteinDay;
	private TextView textFatDay;
	private TextView textCarbsMeal;
	private TextView textProteinMeal;
	private TextView textFatMeal;
	private TextView textCarbsPercentageDaily;
	private TextView textProteinPercentageDaily;
	private TextView textFatPercentageDaily;
	private PieGraph pieGraphDiet;
	private PieGraph pieGraphDaily;
	private Spinner spinnerMealsPerDay;
	private ImageView imageSelectorDiet;
	private double[] currentDietValues;
	private MainActivity mainActivity;
	private int colorTouched;

	public MacroGraphsFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_macro_graphics, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(getActivity(), SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_macro_graphics,
				container, false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);
		mainActivity = (MainActivity) getActivity();
		colorTouched = mainActivity.getResources().getColor(
				R.color.chart_touched);
		currentDietValues = moderateDiet;
		mealsPerDay = 3;
		setViews();
		setListeners();
		drawDietGraph();
		drawDailyGraph();
	}

	private void setViews() {
		pieGraphDaily = (PieGraph) getActivity().findViewById(
				R.id.pieGraphDaily);
		pieGraphDiet = (PieGraph) getActivity().findViewById(R.id.pieGraphDiet);
		imageSelectorDiet = (ImageView) getActivity().findViewById(
				R.id.imageSelectorDiet);
		textCaloriesPerDay = (TextView) getActivity().findViewById(
				R.id.textCaloriesPerDay);
		textDietType = (TextView) getActivity().findViewById(R.id.textDietType);

		textCarbsDay = (TextView) getActivity().findViewById(R.id.textCarbsDay);
		textProteinDay = (TextView) getActivity().findViewById(
				R.id.textProteinDay);
		textFatDay = (TextView) getActivity().findViewById(R.id.textFatDay);
		textCarbsMeal = (TextView) getActivity().findViewById(
				R.id.textCarbsMeal);
		textProteinMeal = (TextView) getActivity().findViewById(
				R.id.textProteinMeal);
		textFatMeal = (TextView) getActivity().findViewById(R.id.textFatMeal);
		textCarbsPercentageDaily = (TextView) getActivity().findViewById(
				R.id.textCarbsPercentageDaily);
		textFatPercentageDaily = (TextView) getActivity().findViewById(
				R.id.textFatPercentageDaily);
		textProteinPercentageDaily = (TextView) getActivity().findViewById(
				R.id.textProteinPercentageDaily);

		spinnerMealsPerDay = (Spinner) getActivity().findViewById(
				R.id.spinnerMealsPerDay);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.meals_per_day_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerMealsPerDay.setAdapter(adapter);
		spinnerMealsPerDay.setSelection(2);

	}

	private void setListeners() {
		imageSelectorDiet.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DietPickerDialog dialog = new DietPickerDialog(getActivity(),
						MacroGraphsFragment.this);
				dialog.show();
			}
		});

		spinnerMealsPerDay
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						String item = (String) parent.getItemAtPosition(pos);
						if (item.equals("1 meal/day")) {
							mealsPerDay = 1;
						} else if (item.equals("2 meals/day")) {
							mealsPerDay = 2;
						} else if (item.equals("3 meals/day")) {
							mealsPerDay = 3;
						} else if (item.equals("4 meals/day")) {
							mealsPerDay = 4;
						} else if (item.equals("5 meals/day")) {
							mealsPerDay = 5;
						}
						drawDailyGraph();
					}

					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
	}

	private void drawDietGraph() {
		Double carbsDouble = (mainActivity.getMinimumCalories() * currentDietValues[0]) / 4.00;
		Double proteinDouble = (mainActivity.getMinimumCalories() * currentDietValues[1]) / 4.00;
		Double fatDouble = (mainActivity.getMinimumCalories() * currentDietValues[2]) / 9.00;
		pieGraphDiet.clearSlices();
		final int carbsInt = carbsDouble.intValue();
		final int proteinInt = proteinDouble.intValue();
		final int fatInt = fatDouble.intValue();

		textCarbsDay.setText(String.valueOf(carbsInt) + " g/day");
		textProteinDay.setText(String.valueOf(proteinInt) + " g/day");
		textFatDay.setText(String.valueOf(fatInt) + " g/day");
		textCarbsPercentageDaily.setText(String
				.valueOf(currentDietValues[0] * 100) + "%");
		textProteinPercentageDaily.setText(String
				.valueOf(currentDietValues[1] * 100) + "%");
		textFatPercentageDaily.setText(String
				.valueOf(currentDietValues[2] * 100) + "%");

		PieSlice slice = new PieSlice();
		slice.setColor(Color.parseColor("#e16662"));
		slice.setSelectedColor(colorTouched);
		slice.setValue(carbsInt);
		pieGraphDiet.addSlice(slice);

		slice = new PieSlice();
		slice.setColor(Color.parseColor("#4ad0bc"));
		slice.setSelectedColor(colorTouched);
		slice.setValue(proteinInt);
		pieGraphDiet.addSlice(slice);

		slice = new PieSlice();
		slice.setColor(Color.parseColor("#fdbc27"));
		slice.setSelectedColor(colorTouched);
		slice.setValue(fatInt);
		pieGraphDiet.addSlice(slice);

		pieGraphDiet.setOnSliceClickedListener(new OnSliceClickedListener() {

			@Override
			public void onClick(final int index, int x, int y, int thickness) {
				String text = " g/day";
				switch (index) {
				case 0:
					text = carbsInt + text;
					break;

				case 1:
					text = proteinInt + text;
					break;

				case 2:
					text = fatInt + text;
					break;

				default:
					break;
				}

				Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void drawDailyGraph() {
		final Resources resources = getResources();

		Double carbsDouble = (mainActivity.getMinimumCalories() * currentDietValues[0]) / 4.00;
		Double proteinDouble = (mainActivity.getMinimumCalories() * currentDietValues[1]) / 4.00;
		Double fatDouble = (mainActivity.getMinimumCalories() * currentDietValues[2]) / 9.00;
		pieGraphDaily.clearSlices();
		carbsPerMeal = carbsDouble.intValue();
		proteinPerMeal = proteinDouble.intValue();
		fatPerMeal = fatDouble.intValue();

		carbsPerMeal = carbsPerMeal / mealsPerDay;
		proteinPerMeal = proteinPerMeal / mealsPerDay;
		fatPerMeal = fatPerMeal / mealsPerDay;

		textCarbsMeal.setText(String.valueOf(carbsPerMeal) + " g/meal");
		textProteinMeal.setText(String.valueOf(proteinPerMeal) + " g/meal");
		textFatMeal.setText(String.valueOf(fatPerMeal) + " g/meal");

		PieSlice slice = new PieSlice();
		slice.setColor(Color.parseColor("#8c304a"));
		slice.setSelectedColor(colorTouched);
		slice.setValue(carbsPerMeal);
		pieGraphDaily.addSlice(slice);

		slice = new PieSlice();
		slice.setColor(Color.parseColor("#00bcdd"));
		slice.setSelectedColor(colorTouched);
		slice.setValue(proteinPerMeal);
		pieGraphDaily.addSlice(slice);

		slice = new PieSlice();
		slice.setColor(Color.parseColor("#e18454"));
		slice.setSelectedColor(colorTouched);
		slice.setValue(fatPerMeal);
		pieGraphDaily.addSlice(slice);

		pieGraphDaily.setOnSliceClickedListener(new OnSliceClickedListener() {

			@Override
			public void onClick(final int index, int x, int y, int thickness) {
				String text = " g/meal";
				switch (index) {
				case 0:
					text = carbsPerMeal + text;
					break;

				case 1:
					text = proteinPerMeal + text;
					break;

				case 2:
					text = fatPerMeal + text;
					break;

				default:
					break;
				}

				Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onDietSelected(int dietIdPicked) {
		if (dietIdPicked == MODERATE_ID) {
			currentDietValues = moderateDiet;
			textDietType.setText("Moderate");
		} else if (dietIdPicked == LOW_CARB_ID) {
			currentDietValues = lowCarbDiet;
			textDietType.setText("Low Carb");
		} else if (dietIdPicked == LOW_FAT_ID) {
			currentDietValues = lowFatDiet;
			textDietType.setText("Low Fat");
		} else if (dietIdPicked == KETOGENIC_ID) {
			currentDietValues = ketogenicDiet;
			textDietType.setText("Ketogenic");
		} else {
			currentDietValues = moderateDiet;
			textDietType.setText("Moderate");
		}

		drawDietGraph();
		drawDailyGraph();

	}

	public void onMinimumCaloriesChanged(int minimumCalories) {
		textCaloriesPerDay = (TextView) getActivity().findViewById(
				R.id.textCaloriesPerDay);
		textCaloriesPerDay.setText(String.valueOf(minimumCalories));
		drawDietGraph();
		drawDailyGraph();

	}
}
