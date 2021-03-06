/**
* Fragment that appears in the "content_frame", shows critical information
*/
package com.group7.fragment;

import com.group7.data.*;
import com.group7.testlayout.R;

import android.os.Bundle;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class VisitInformation extends Fragment {
    public static final String ARG_CATERGORY_NUMBER = "catergory_number";
	//public static String ARG_CATERGORY_NUMBER;
    TextView tw1,tw2;
    Button bt1, bt2,bt3,bt4;
    //tw=(TextView) getView().findViewById(R.id.textView1);

    public VisitInformation() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.visit_information, container, false);
        int i = getArguments().getInt(ARG_CATERGORY_NUMBER);
        String catergory = getResources().getStringArray(R.array.catergory_array)[i];

        //((RelativeLayout) rootView.findViewById(R.id.image)).setBackgroundColor(#000000);
        getActivity().setTitle(catergory);
        return rootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) { 
		super.onActivityCreated(savedInstanceState);
		
		bt1=(Button)getView().findViewById(R.id.Performer);
		bt1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tw1=(TextView) getView().findViewById(R.id.textView1);
				tw1.setText(StoreData.getPerfomer());
			}
		}
		);
		bt2=(Button)getView().findViewById(R.id.VisitReason);
		bt2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tw1=(TextView) getView().findViewById(R.id.textView1);
				tw1.setText(StoreData.getReasonForVisit());
			}
		}
		);
		/*
		bt3=(Button)getView().findViewById(R.id.Performer);
		bt3.setOnClickListener(new View.OnClickListener(){
			public void onCLick(View v){
				tw1=(TextView)getView().findViewById(R.id.textView1);
				tw1.setText(StoreData.getReasonForVisit());
			}
		}
		);*/
    }
		
		
		/*
		tw1=(TextView) getView().findViewById(R.id.button1);
		tw2=(TextView) getView().findViewById(R.id.button1);
		tw1.setMovementMethod(ScrollingMovementMethod.getInstance());
		
//		findData content=new findData();  
		
		tw1.setText(StoreData.getPerformer());
		*/
		/*
		bt1=(Button)getView().findViewById(R.id.Performer);
		bt1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tw1=(TextView) getView().findViewById(R.id.history1);
				tw1.setText(StoreData.getProcedures());
			}
		}
		);
		*/
		/*
		bt2=(Button)getView().findViewById(R.id.SocialHistory);
		bt2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tw1=(TextView) getView().findViewById(R.id.history1);
				tw1.setText(StoreData.getSocialHistory());
			}
		}
		);
		bt3=(Button)getView().findViewById(R.id.Immunizations);
		bt3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tw1=(TextView) getView().findViewById(R.id.history1);
				tw1.setText(StoreData.getImmunizations());
			}
		}
		);
		bt4=(Button)getView().findViewById(R.id.FamilyHistory);
		bt4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tw1=(TextView) getView().findViewById(R.id.history1);
				tw1.setText(StoreData.getFamilyHistory());
			}
		}
		);
		bt5=(Button)getView().findViewById(R.id.Problems);
		bt5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tw1=(TextView) getView().findViewById(R.id.history1);
				tw1.setText(StoreData.getProblems());
			}
		}
		);
		
//		tw2=(TextView) getView().findViewById(R.id.history2);
//		tw1.setMovementMethod(ScrollingMovementMethod.getInstance());
		
//		findData content=new findData();  
		
//		tw1.setText(StoreData.getStorePatientHistory());
 * 
 */
    
}

