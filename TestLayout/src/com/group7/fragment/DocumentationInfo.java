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
import android.widget.TextView;

public class DocumentationInfo extends Fragment {
    public static final String ARG_CATERGORY_NUMBER = "catergory_number";
    TextView tw1,tw2,tw3,tw4;
    //tw=(TextView) getView().findViewById(R.id.textView1);

    public DocumentationInfo() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.documentinfo_layout, container, false);
        int i = getArguments().getInt(ARG_CATERGORY_NUMBER);
        String catergory = getResources().getStringArray(R.array.catergory_array)[i];

        //((RelativeLayout) rootView.findViewById(R.id.image)).setBackgroundColor(#000000);
        getActivity().setTitle(catergory);
        return rootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) { 
		super.onActivityCreated(savedInstanceState);
		tw1=(TextView) getView().findViewById(R.id.docID);
		tw2=(TextView) getView().findViewById(R.id.date);
		tw3=(TextView) getView().findViewById(R.id.author);
		tw4=(TextView) getView().findViewById(R.id.enter);
//		tw1.setMovementMethod(ScrollingMovementMethod.getInstance());
		
//		findData content=new findData();  
		
		tw1.setText(StoreData.getDocumentId());
		tw2.setText(StoreData.getTime());
		tw3.setText(StoreData.getAuthor());
		tw4.setText(StoreData.getEntered());
    }
    
    
    
}