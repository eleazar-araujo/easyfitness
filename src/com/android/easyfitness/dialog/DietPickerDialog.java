package com.android.easyfitness.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.easyfitness.R;
import com.android.easyfitness.adapter.DietTypeAdapter;
import com.android.easyfitness.dialog.NumberPickerDialog.PickerListener;
import com.android.easyfitness.fragment.MacroGraphsFragment;
import com.android.easyfitness.model.DietType;
import com.quietlycoding.android.picker.NumberPicker;

public class DietPickerDialog extends Dialog {

	private DietListener listener;
	private Context context;

	public DietPickerDialog(Activity activity, DietListener listener) {
		super(activity);
		this.context = activity;
		this.listener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_diet_picker);

		setCanceledOnTouchOutside(true);

		List<DietType> dietTypeList = new ArrayList<DietType>();
		DietType dietType = new DietType();
		dietType.setName("Moderate");
		dietType.setMacroPercentages(MacroGraphsFragment.moderateDiet);
		dietTypeList.add(dietType);

		dietType = new DietType();
		dietType.setName("Low Carb");
		dietType.setMacroPercentages(MacroGraphsFragment.lowCarbDiet);
		dietTypeList.add(dietType);

		dietType = new DietType();
		dietType.setName("Low Fat");
		dietType.setMacroPercentages(MacroGraphsFragment.lowFatDiet);
		dietTypeList.add(dietType);

		dietType = new DietType();
		dietType.setName("Ketogenic");
		dietType.setMacroPercentages(MacroGraphsFragment.ketogenicDiet);
		dietTypeList.add(dietType);

		DietTypeAdapter adapter = new DietTypeAdapter(dietTypeList, context);

		ListView listDietTypes = (ListView) findViewById(R.id.listDietTypes);
		listDietTypes.setAdapter(adapter);

		listDietTypes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				DietType dietType = (DietType) arg0.getItemAtPosition(arg2);
				int dietIdPicked;
				if (dietType.getName().equals("Moderate")) {
					dietIdPicked = MacroGraphsFragment.MODERATE_ID;
				} else if (dietType.getName().equals("Low Carb")) {
					dietIdPicked = MacroGraphsFragment.LOW_CARB_ID;
				} else if (dietType.getName().equals("Low Fat")) {
					dietIdPicked = MacroGraphsFragment.LOW_FAT_ID;
				} else if (dietType.getName().equals("Ketogenic")) {
					dietIdPicked = MacroGraphsFragment.KETOGENIC_ID;
				} else {
					dietIdPicked = MacroGraphsFragment.MODERATE_ID;
				}
				listener.onDietSelected(dietIdPicked);
				DietPickerDialog.this.dismiss();

			}
		});
	}

	public interface DietListener {
		public void onDietSelected(int dietIdPicked);
	}
}