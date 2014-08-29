package com.example.elements;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class CurrencyConverterActivity extends Activity {

	EditText SEK;
	EditText INR;
	// double sek;
	// double inr;
	RadioButton INRtoSEK;
	RadioButton SEKtoINR;
	Button convert;
	Button todayRate;
	RadioGroup rb;

	boolean mInrToSek = true;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.currency_converter);

		SEK = (EditText) this.findViewById(R.id.SEK);
		INR = (EditText) this.findViewById(R.id.INR);

		rb = (RadioGroup) this.findViewById(R.id.RadioGroup);

		INRtoSEK = (RadioButton) this.findViewById(R.id.INRtoSEK);
		INRtoSEK.setChecked(true);
		SEKtoINR = (RadioButton) this.findViewById(R.id.SEKtoINR);

		convert = (Button) this.findViewById(R.id.convert);
		convert.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub

				if (TextUtils.isEmpty(INR.getText().toString())
						&& TextUtils.isEmpty(SEK.getText().toString())) {
					Log.i("Inside empty if", "Inside INR empty if");
					alertDialog("Enter INR or SEK");
				}

				else if (!(INR.getText().toString().isEmpty())
						&& !(SEK.getText().toString().isEmpty())) {
					Log.i("Inside empty if", "Inside INR empty if");
					alertDialog("Enter either SEK or INR");
				}

				else {

					convert();
				}
			}
		});

		todayRate = (Button) this.findViewById(R.id.TodayRate);
		todayRate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CurrencyConverterActivity.this,
						readJSONFeedActivity.class);
				startActivity(intent);

			}

		});

		rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.INRtoSEK:
					mInrToSek = true;
					break;

				case R.id.SEKtoINR:
					mInrToSek = false;
					break;

				default:
					break;
				}
				convertUi();

			}
		});

	}

	protected void convertUi() {
		// TODO Auto-generated method stub
		SEK.setText("");
		INR.setText("");
		if (mInrToSek) {
			INR.setHint("INR");
			SEK.setHint("SEK");
		} else {
			INR.setHint("SEK");
			SEK.setHint("INR");
		}
	}

	protected void convert() {
		double val = Double.parseDouble(INR.getText().toString());
		if (mInrToSek) {
			val = val * 0.11;
		} else {
			val = val / 0.11;
		}
		SEK.setText(Double.toString(val));

	}

	protected void convertSEKtoINR() {
		double val = Double.parseDouble(SEK.getText().toString());
		val = val / 0.11;
		INR.setText(Double.toString(val));
	}

	void alertDialog(String iMessage) {

		/*
		 * Log.i("Inside Alert", "Inside Alert");
		 * 
		 * 
		 * AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
		 * CurrencyConverterActivity.this);
		 * 
		 * // set title alertDialogBuilder.setTitle("Alert!");
		 * 
		 * // set dialog message alertDialogBuilder.setMessage(iMessage)
		 * .setCancelable(false) .setPositiveButton("Enter", new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) { //
		 * TODO Auto-generated method stub Intent intent = new
		 * Intent(CurrencyConverterActivity.this,
		 * CurrencyConverterActivity.class); startActivity(intent); } });
		 * AlertDialog alert = alertDialogBuilder.create();
		 * 
		 * // show it alert.show();
		 */

		final Dialog dialog = new Dialog(CurrencyConverterActivity.this);
		dialog.setContentView(R.layout.custom_alert_dialog);
		dialog.setTitle("Alert");

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.alertText);
		text.setText(iMessage);

		Button dialogButton = (Button) dialog
				.findViewById(R.id.alertEnterButton);

		dialogButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CurrencyConverterActivity.this,
						CurrencyConverterActivity.class);
				startActivity(intent);
			}
		});

		dialog.show();

	}
}
