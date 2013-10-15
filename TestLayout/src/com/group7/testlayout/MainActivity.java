package com.group7.testlayout;

import java.io.File;
import java.util.Locale;

import com.group7.data.StoreData;
import com.group7.data.findData;
import com.group7.fragment.DocumentationInfo;
import com.group7.fragment.DrContact;
import com.group7.fragment.Fragment_00;
import com.group7.fragment.PhistoryFragment;
import com.group7.fragment.VisitInformation;
import com.group7.tozip.ExDialog;
import com.group7.tozip.MyActivity;





import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	private String[] mCatergoryTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList; 
    
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		StoreData sd=new StoreData();

		mTitle = mDrawerTitle = getTitle();
		mCatergoryTitles = getResources().getStringArray(R.array.catergory_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mCatergoryTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
        	/** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
	
	/* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        //switch(item.getItemId()) {
        //case R.id.action_settings:
            // create intent to perform web search for this planet
            //Intent intent = new Intent(Intent.ACTION_SETTING);
            //intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            //if (intent.resolveActivity(getPackageManager()) != null) {
                //startActivity(intent);
            //} else {
                //Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            //}
            //return true;
        //default:
            return super.onOptionsItemSelected(item);
        //}
    }
    
	
	/* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    
    
    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
    	// update the main content by replacing fragments
        // bundle args and create fragment manager and transactor   
    	Bundle args = new Bundle();
    	FragmentManager fragmentManager = getFragmentManager();
    	FragmentTransaction fragmentTx = fragmentManager.beginTransaction();
    
    	//based on item position selected, decide which fragment to replace
        switch (position){
//    	case 0: 
//    			break;
    	case 1: VisitInformation vInfo = new VisitInformation();
        		args.putInt(VisitInformation.ARG_CATERGORY_NUMBER,position);
        		vInfo.setArguments(args);
        		
        		fragmentTx.replace(R.id.content_frame, vInfo);
        		fragmentTx.commit();
    			break;
//    	case 2: //critical info Fragment critfrag
//    			break:
    	case 3: 
    		PhistoryFragment phistory = new PhistoryFragment();
    		args.putInt(PhistoryFragment.ARG_CATERGORY_NUMBER, position);
    		phistory.setArguments(args);

    		// Insert the fragment by replacing any existing fragment
    		fragmentTx.replace(R.id.content_frame, phistory);
    		fragmentTx.commit();
			break;
    	case 4:
    		DrContact drcontact = new DrContact();
    		args.putInt(DrContact.ARG_CATERGORY_NUMBER, position);
    		drcontact.setArguments(args);

    		// Insert the fragment by replacing any existing fragment
    		fragmentTx.replace(R.id.content_frame, drcontact);
    		fragmentTx.commit();
			break;
    	case 5: 
    		DocumentationInfo docInfo = new DocumentationInfo();
    		args.putInt(DocumentationInfo.ARG_CATERGORY_NUMBER, position);
    		docInfo.setArguments(args);

    		// Insert the fragment by replacing any existing fragment
    		fragmentTx.replace(R.id.content_frame, docInfo);
    		fragmentTx.commit();
    		break;
    			
    	default: //
	    		Fragment catfragment = new CatergoryFragment();
	    		args.putInt(PhistoryFragment.ARG_CATERGORY_NUMBER, position);
	    		catfragment.setArguments(args);
	
	    		// Insert the fragment by replacing any existing fragment
	    		fragmentTx.replace(R.id.content_frame, catfragment);
	    		fragmentTx.commit();
    	}
    	
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mCatergoryTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
    
    
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK )  
        {  
            // ���������������������  
        	//create exit dialog
            AlertDialog isExit = new AlertDialog.Builder(this).create();  
            // ���������������������  
            //setting title
            isExit.setTitle("System Prompt");  
            // ���������������������  
            //Settings messages
            isExit.setMessage("Did you Encrypt your document?");  
            // ���������������������������������  
            //add listener
            isExit.setButton("yes,I did", listener);  
            isExit.setButton2("cancel", listener);  
            // ���������������  
            //show dialog box
            isExit.show();  
  
        }  
          
        return false;  
          
    }  
    /**������������������������button������������*/ 
    /*add listener*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()  
    {  
        public void onClick(DialogInterface dialog, int which)  
        {  
            switch (which)  
            {  
            case AlertDialog.BUTTON_POSITIVE:// "yes" 
                finish();  
                break;  
            case AlertDialog.BUTTON_NEGATIVE:// "cancel" 
                break;  
            default:  
                break;  
            }  
        }  
    };  
    
    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class CatergoryFragment extends Fragment {
        public static final String ARG_CATERGORY_NUMBER = "catergory_number";
        TextView tw;
        Button bt;
        Button send;
        int i;
//        Fragment_00 fragment_00=new Fragment_00();
        public CatergoryFragment() {
            // Empty constructor required for fragment subclasses
        	
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_catergory, container, false);
            i = getArguments().getInt(ARG_CATERGORY_NUMBER);
            String catergory = getResources().getStringArray(R.array.catergory_array)[i];

            //int imageId = getResources().getIdentifier(catergory.toLowerCase(Locale.getDefault()),
            //                "drawable", getActivity().getPackageName());
            //((RelativeLayout) rootView.findViewById(R.id.image)).setBackgroundColor(#000000);
            getActivity().setTitle(catergory);
            return rootView;
        }
       
        @Override
        public void onActivityCreated(Bundle savedInstanceState) { 
    		super.onActivityCreated(savedInstanceState);
    		tw=(TextView) getView().findViewById(R.id.textView1);
    		tw.setMovementMethod(ScrollingMovementMethod.getInstance());
    		
    		bt=(Button) getView().findViewById(R.id.button1);
    		bt.setVisibility(View.GONE);
    		bt.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				startActivity(new Intent(getActivity(), MyActivity.class));
//    				tw.setText("turn to another activity");		
    			}
    		});
    		
    		
    		send=(Button) getView().findViewById(R.id.send);
    		send.setVisibility(View.INVISIBLE);
    		send.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				File file = new File("/sdcard/zip/CDA.zip"); //attachment path
    				Intent intent = new Intent(Intent.ACTION_SEND);
    				intent.putExtra("subject", file.getName()); //
    				intent.putExtra("body", "Hello Dr." +
    						",The PIN of CDA.zip is ****** "); //������  text
    				intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file)); //������������������������file������  add attachment 
    				if (file.getName().endsWith(".gz")) {
    					intent.setType("application/x-gzip"); //���������gz������gzip���mime  
    					} else if (file.getName().endsWith(".txt")) {
    						intent.setType("text/plain"); //���������������text/plain���mime  
    						} else {
    							intent.setType("application/octet-stream"); //���������������������������������������������������
    							}
    				startActivity(intent); //���������������mail���������������������  send by using system email
//    				Toast.makeText(getActivity(), "Send success", 1).show();  
    			}
    		});
    		
    		findData content=new findData();  		
    		if(i==0){
    			
    			tw.setText("Personal information");
    		}else if(i==1){
    			tw.setText("Visit Information");
    		}else if(i==2){
    			tw.setText("Critical Info");
    		}else if(i==3){
    			tw.setText("p history");//content.getPatienthistory()
    		}else if(i==4){
    			tw.setText("Dr. contact");//content.getDrContact()
    		}else if(i==5){
    			tw.setText("Documentation Info");//content.getDocumentationInfo()
    		}else if(i==6){
    			bt.setVisibility(View.VISIBLE);
    			send.setVisibility(View.VISIBLE);
    			tw.setText("Please encrypt your file before sending");
    			
//    			fragment_00.onActivityCreated(savedInstanceState);		   			
    		}
    	}

      
    }

    
}
