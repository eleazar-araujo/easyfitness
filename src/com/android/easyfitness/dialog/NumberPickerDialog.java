package com.android.easyfitness.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.easyfitness.R;
import com.android.easyfitness.view.NumberPicker;

public class NumberPickerDialog extends Dialog {

	private PickerListener listener;
	private int resourceSelected;
	private Context context;
	private NumberPicker numberPicker;
	private int currentNumber;

	public NumberPickerDialog(Activity activity, PickerListener listener,
			int resourceToUpdate, int currentNumber) {
		super(activity);
		this.currentNumber = currentNumber;
		this.context = activity;
		this.listener = listener;
		this.resourceSelected = resourceToUpdate;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_number_picker);
		TextView textHint = (TextView) findViewById(R.id.textHint);
		numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
		numberPicker.setCurrent(currentNumber);

		setCanceledOnTouchOutside(true);
		String text = "Hello World";

		switch (resourceSelected) {
		case R.id.layoutAge:
			text = "Put your age in years";
		case R.id.layoutHeight:
			text = "Put your height in centimetres";
			break;

		case R.id.layoutWeight:
			text = "Put your weight in kilograms";
			break;

		default:
			break;
		}
		textHint.setText(text);
		TextView textView = (TextView) findViewById(R.id.confirm);
		textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String numberToUpdate = String.valueOf(numberPicker
						.getCurrent());
				if (numberToUpdate == null || numberToUpdate.equals("")) {
					Toast.makeText(context, "Please enter a valid number",
							Toast.LENGTH_SHORT).show();

				} else {
					int numberExtracted = numberPicker.updateCurrent();
					if (numberExtracted != -1) {
						numberToUpdate = String.valueOf(numberExtracted);
						listener.onConfirm(resourceSelected, numberToUpdate);
						NumberPickerDialog.this.dismiss();
					} else {
						Toast.makeText(context, "Please enter a valid number",
								Toast.LENGTH_SHORT).show();

					}

				}

			}
		});
		textView = (TextView) findViewById(R.id.cancel);
		textView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				NumberPickerDialog.this.dismiss();

			}
		});

	}

	public interface PickerListener {
		public void onConfirm(int resourceToUpdate, String textToUpdate);
	}
}