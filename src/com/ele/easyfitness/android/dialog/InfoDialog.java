package com.ele.easyfitness.android.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.widget.TextView;

import com.ele.easyfitness.android.R;

public class InfoDialog extends Dialog {

	public static final int FORMULAS = 1;
	public static final int BMR = 2;
	public static final int BMI = 3;
	public static final int WATER_INTAKE = 4;
	public static final int TARGET_HEART_RATE = 5;

	private int infoIndex;
	private Context context;

	public InfoDialog(Activity activity, int infoIndex) {
		super(activity);
		this.context = activity;
		this.infoIndex = infoIndex;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_info);

		setCanceledOnTouchOutside(true);
		TextView textInfo = (TextView) findViewById(R.id.textInfo);
		String textToSet = null;

		switch (infoIndex) {
		case FORMULAS:
			textToSet = context.getResources()
					.getString(R.string.info_formulas);
			break;
		case BMR:
			textToSet = context.getResources().getString(R.string.info_bmr);
			break;
		case BMI:
			textToSet = context.getResources().getString(R.string.info_bmi);
			break;

		case WATER_INTAKE:
			textToSet = context.getResources().getString(
					R.string.info_water_intake);
			break;

		case TARGET_HEART_RATE:
			textToSet = context.getResources().getString(
					R.string.info_target_heart_rate);
			break;

		default:
			break;
		}
		if (textToSet != null) {
			textInfo.setText(textToSet);
		} else {
			dismiss();
		}
	}

	public interface DietListener {
		public void onDietSelected(int dietIdPicked);
	}
}
