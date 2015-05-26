package jcsla.korail;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_splash);
	    
	    Handler h = new Handler() {
	    	public void handleMessage(Message msg) {
	    		finish();
	    		startActivity(new Intent(SplashActivity.this, SearchActivity.class));
	    	}
	    };
	    h.sendEmptyMessageDelayed(0, 1500);
	}
}
