/*
 * 	   Created by Daniel Nadeau
 * 	   daniel.nadeau01@gmail.com
 * 	   danielnadeau.blogspot.com
 * 
 * 	   Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.android.easyfitness.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class PieGraph extends View {
	private static final float THICKNESS_CONSTANT = 80f;
	private ArrayList<PieSlice> slices = new ArrayList<PieSlice>();
	private Paint paint = new Paint();
	private Path path = new Path();
	private float midX;
	private float absoluteRadius;
	private float innerRadius;
	private float midY;

	private int indexSelected = -1;
	private int thickness;
	private OnSliceClickedListener listener;

	private boolean drawCompleted = false;

	// private float thicknessConstant = 81f;

	public PieGraph(Context context) {
		super(context);
		thickness = (int) (THICKNESS_CONSTANT * context.getResources()
				.getDisplayMetrics().density);
	}

	public PieGraph(Context context, AttributeSet attrs) {
		super(context, attrs);
		thickness = (int) (THICKNESS_CONSTANT * context.getResources()
				.getDisplayMetrics().density);
	}

	public void clearSlices() {
		slices = new ArrayList<PieSlice>();
	}

	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.TRANSPARENT);
		paint.reset();
		paint.setAntiAlias(true);
		float midX, midY, radius, innerRadius;
		path.reset();

		float currentAngle = 270;
		float currentSweep = 0;
		int totalValue = 0;
		float padding = 2;

		midX = getWidth() / 2;
		midY = getHeight() / 2;
		this.midX = midX;
		this.midY = midY;
		if (midX < midY) {
			radius = midX;
		} else {
			radius = midY;
		}
		this.absoluteRadius = radius;
		radius -= padding;
		innerRadius = radius - thickness;

		this.innerRadius = innerRadius;
		for (PieSlice slice : slices) {
			totalValue += slice.getValue();
		}
		int count = 0;
		for (PieSlice slice : slices) {
			Path p = new Path();
			paint.setColor(slice.getColor());
			currentSweep = (slice.getValue() / totalValue) * (360);
			p.arcTo(new RectF(midX - radius, midY - radius, midX + radius, midY
					+ radius), currentAngle + padding, currentSweep - padding);
			p.arcTo(new RectF(midX - innerRadius, midY - innerRadius, midX
					+ innerRadius, midY + innerRadius),
					(currentAngle + padding) + (currentSweep - padding),
					-(currentSweep - padding));
			p.close();

			slice.setPath(p);
			slice.setRegion(new Region((int) (midX - radius),
					(int) (midY - radius), (int) (midX + radius),
					(int) (midY + radius)));
			canvas.drawPath(p, paint);

			// paintText.setStyle(Style.FILL);
			// paintText.setColor(Color.WHITE);
			// paintText.setTextAlign(Paint.Align.CENTER);
			// paintText.setTextSize(50);
			// canvas.drawText("Hola", viewWidthHalf, viewHeightHalf, paint);

			if (indexSelected == count && listener != null) {
				path.reset();
				paint.setColor(slice.getColor());
				paint.setColor(slice.getSelectedColor());

				if (slices.size() > 1) {
					path.arcTo(new RectF(midX - radius - (padding * 2), midY
							- radius - (padding * 2), midX + radius
							+ (padding * 2), midY + radius + (padding * 2)),
							currentAngle, currentSweep + padding);
					path.arcTo(new RectF(midX - innerRadius + (padding * 2),
							midY - innerRadius + (padding * 2), midX
									+ innerRadius - (padding * 2), midY
									+ innerRadius - (padding * 2)),
							currentAngle + currentSweep + padding,
							-(currentSweep + padding));
					path.close();
				} else {
					path.addCircle(midX, midY, radius + padding, Direction.CW);
				}

				canvas.drawPath(path, paint);
				paint.setAlpha(255);
			}

			currentAngle = currentAngle + currentSweep;

			count++;
		}

		drawCompleted = true;

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int[] location = new int[2];
		if (drawCompleted) {
			PieGraph.this.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			PieGraph.this.getLocationOnScreen(location);
			Log.i("PieGraph", "location in screen is " + location[0] + ", "
					+ location[1]);
			int d = location[0];
			d = d + (int) midX;

			location[0] = d;

			int sd = location[1];
			sd = sd + (int) midY;
			location[1] = sd;

			Point point = new Point();
			point.x = (int) event.getX();
			point.y = (int) event.getY();

			int count = 0;
			for (PieSlice slice : slices) {
				Region r = new Region();
				r.setPath(slice.getPath(), slice.getRegion());
				if (r.contains((int) point.x, (int) point.y)
						&& event.getAction() == MotionEvent.ACTION_DOWN) {
					indexSelected = count;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					if (r.contains((int) point.x, (int) point.y)
							&& listener != null) {
						if (indexSelected > -1) {
							Log.i("PieGraph", "x is " + point.x + " and y is "
									+ point.y);
							// listener.onClick(indexSelected, (int) point.x,
							// (int) point.y);

							int e = (int) ((innerRadius - absoluteRadius) - (midX - absoluteRadius));
							int f = (int) ((midY + absoluteRadius) - (midY - absoluteRadius));
							int rw = e;
							if (e > f) {
								rw = e;
							} else {
								rw = f;
							}

							listener.onClick(indexSelected, (int) location[0],
									(int) location[1],
									(int) (innerRadius - absoluteRadius));
						}
						indexSelected = -1;
					}

				} else if (event.getAction() == MotionEvent.ACTION_CANCEL)
					indexSelected = -1;
				count++;
			}

			if (event.getAction() == MotionEvent.ACTION_DOWN
					|| event.getAction() == MotionEvent.ACTION_UP
					|| event.getAction() == MotionEvent.ACTION_CANCEL) {
				postInvalidate();
			}
		}

		return true;
	}

	public ArrayList<PieSlice> getSlices() {
		return slices;
	}

	public void setSlices(ArrayList<PieSlice> slices) {
		this.slices = slices;
		postInvalidate();
	}

	public PieSlice getSlice(int index) {
		return slices.get(index);
	}

	public void addSlice(PieSlice slice) {
		this.slices.add(slice);
		postInvalidate();
	}

	public void setOnSliceClickedListener(OnSliceClickedListener listener) {
		this.listener = listener;
	}

	public int getThickness() {
		return thickness;
	}

	public void setThickness(int thickness) {
		this.thickness = thickness;
		postInvalidate();
	}

	public void removeSlices() {
		for (int i = slices.size() - 1; i >= 0; i--) {
			slices.remove(i);
		}
		postInvalidate();
	}

	public static interface OnSliceClickedListener {
		public abstract void onClick(int index, int x, int y, int thickness);
	}

}
