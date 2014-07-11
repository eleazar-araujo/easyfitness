package com.android.easyfitness.adapter;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.easyfitness.R;
import com.android.easyfitness.model.DietType;

public class DietTypeAdapter extends BaseAdapter {
	private static final String LOG_TAG = "QuizMaker-StudentQuizAdapter";
	private List<DietType> dietTypeList;
	private Context context;

	public DietTypeAdapter(List<DietType> evaluationDataList, Context context) {
		setDietTypeList(evaluationDataList);
		this.context = context;
	}

	@Override
	public int getCount() {
		return dietTypeList.size();
	}

	public void remove(int i) {
		dietTypeList.remove(i);
		this.notifyDataSetChanged();
	}

	public DietType getItem(int position) {
		return dietTypeList.get(position);
	}

	public long getItemId(int position) {
		return -1;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		TemplateAnswerHolder holder = new TemplateAnswerHolder();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row_diet_type_list, null);

			TextView textDietName = (TextView) view
					.findViewById(R.id.textDietName);
			TextView textPercentageCarbs = (TextView) view
					.findViewById(R.id.textPercentageCarbs);
			TextView textPercentageProtein = (TextView) view
					.findViewById(R.id.textPercentageProtein);
			TextView textPercentageFat = (TextView) view
					.findViewById(R.id.textPercentageFat);

			holder.textDietName = textDietName;
			holder.textPercentageCarbs = textPercentageCarbs;
			holder.textPercentageProtein = textPercentageProtein;
			holder.textPercentageFat = textPercentageFat;
			view.setTag(holder);
		} else {
			holder = (TemplateAnswerHolder) view.getTag();
		}

		final DietType dietType = dietTypeList.get(position);
		final double[] macroPercentages = dietType.getMacroPercentages();
		holder.textDietName.setText(dietType.getName());
		holder.textPercentageCarbs.setText(String
				.valueOf(macroPercentages[0] * 100) + "% Carbohydrate, ");
		holder.textPercentageProtein.setText(String
				.valueOf(macroPercentages[1] * 100) + "% Protein, ");
		holder.textPercentageFat.setText(String
				.valueOf(macroPercentages[2] * 100) + "% Fat");
		return view;
	}

	public void add(DietType dietType) {
		this.dietTypeList.add(dietType);
	}

	public void setDietTypeList(List<DietType> dietTypeList) {
		this.dietTypeList = dietTypeList;
		this.notifyDataSetChanged();
	}

	private static class TemplateAnswerHolder {
		public TextView textDietName;
		public TextView textPercentageCarbs;
		public TextView textPercentageProtein;
		public TextView textPercentageFat;

	}

}