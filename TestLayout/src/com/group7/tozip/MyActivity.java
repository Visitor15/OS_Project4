package com.group7.tozip;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;
import java.io.File;

import com.group7.testlayout.R;



public class MyActivity extends Activity {

	private static final String TAG = "MyExplorerDemo";
	private static final int REQUEST_EX = 1;

	EditText PIN;
	Uri path=null;
	String sPath=null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.explorer_main);
//		getWindow().setBackgroundDrawableResource(R.drawable.bg);

		PIN=(EditText)findViewById(R.id.password);
		
		Button button = (Button) findViewById(R.id.button);
		button.setVisibility(View.INVISIBLE);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("explorer_title",
						getString(R.string.dialog_read_from_dir));
				intent.setDataAndType(Uri.fromFile(new File("/sdcard")), "*/*");
				intent.setClass(MyActivity.this, ExDialog.class);
				startActivityForResult(intent, REQUEST_EX);
			}
		});
		
		Button upToZip = (Button) findViewById(R.id.upToZip);
		upToZip.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ZipFileWithPw tozip=new ZipFileWithPw();
				
				String pin=PIN.getText().toString();			
//				if(path!=null && pin!=""){
//					tozip.upToZip(sPath, sPath+"CDA.zip", pin);
				if(pin!=""){
					tozip.upToZip("/sdcard/CDA", "/sdcard/zip/CDA.zip", pin);
					Toast.makeText(MyActivity.this, "Encrypt success", 1).show();  
					
				}
				
				finish();
			}
		}
		);
		
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_EX) {
				Uri uri = intent.getData();
//				TextView text = (TextView) findViewById(R.id.text);
//				text.setVisibility(View.INVISIBLE);
//				text.setText("select: " + uri);
				
				path=uri;
				
				
				sPath=path.toString();
				sPath=sPath.replace("file://","");
				String[] ary = sPath.split("\\/");
				sPath=sPath.replace(ary[ary.length-1],"");
//				TextView test = (TextView) findViewById(R.id.test);
//				test.setVisibility(View.INVISIBLE);
//				test.setText(sPath);
			}
		}
	}


}
