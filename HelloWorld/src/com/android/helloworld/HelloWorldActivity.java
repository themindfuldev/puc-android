package com.android.helloworld;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HelloWorldActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		makeShortToast("onCreate()");
        setContentView(R.layout.main);
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					Thread.sleep(1000);
					makeShortToast("Te amo, Cíntia!");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		makeShortToast("onDestroy()");
	}

	@Override
	protected void onPause() {
		super.onPause();
		makeShortToast("onPause()");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		makeShortToast("onRestart()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		makeShortToast("onResume()");
	}

	@Override
	protected void onStart() {
		super.onStart();
		makeShortToast("onStart()");
	}

	@Override
	protected void onStop() {
		super.onStop();
		makeShortToast("onStop()");
	}
	
	private void makeShortToast(String msg) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, msg, duration);
		toast.show();
	}
    
    
}