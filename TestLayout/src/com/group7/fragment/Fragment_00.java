package com.group7.fragment;

import com.group7.testlayout.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment_00 extends Fragment{
	
	TextView tw;
    Button bt;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {	
        return inflater.inflate(R.layout.fragment_catergory, container, false);
    }
	@Override 
	public void onActivityCreated(Bundle savedInstanceState) { 
		super.onActivityCreated(savedInstanceState);
		tw=(TextView) getView().findViewById(R.id.textView1);		
		bt=(Button) getView().findViewById(R.id.button1);
		
		tw.setText("Send Referral");
		bt.setVisibility(View.VISIBLE);
		}

}
