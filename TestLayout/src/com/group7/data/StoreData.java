package com.group7.data;

import com.group7.data.*;

public class StoreData {
	static findData fd= new findData();
	
	static String sProcedures="";
	static String sSocialHistory="";
	static String sImmunizations="";
	static String sFamilyHistory="";
	static String sProblems="";
	static String sReasonForVisit="";
	/////////////////////////
	
	static String sDocumentId;
	static String sTime;
	static String sAuthor;
	static String sEntered;
	
	static String performer = " ";
	
	static String patientHistory="";
	static String drContact="";
	static String docInfo="";
	static boolean status=true;
	
	public StoreData(){
		sProcedures=fd.getProcedures();
		sSocialHistory=fd.getSocialHistory();
		sImmunizations=fd.getImmunizations();
		sFamilyHistory=fd.getFamilyHistory();
		sProblems=fd.getProblems();
		performer = fd.getPerformer();
		sReasonForVisit = fd.getReasonForVisit();
		
//		patientHistory=fd.getPatienthistory();
		sDocumentId=fd.getDocumentId();
		sTime=fd.getTime();
		sAuthor=fd.getAuthor();
		sEntered=fd.getEntered();
		
		drContact=fd.getDrContact();
//		docInfo=fd.getDocumentationInfo();
		status=false;
	}
	
	public static String getProcedures(){	
		return sProcedures;
	}
	public static String getSocialHistory(){	
		return sSocialHistory;
	}
	public static String getImmunizations(){	
		return sImmunizations;
	}
	public static String getFamilyHistory(){	
		return sFamilyHistory;
	}
	public static String getProblems(){	
		return sProblems;
	}
	
	//////////////////
	public static String getDocumentId(){	
		return sDocumentId;
	}
	public static String getTime(){	
		return sTime;
	}
	public static String getAuthor(){	
		return sAuthor;
	}
	public static String getEntered(){	
		return sEntered;
	}
	
	
	public static String getStorePatientHistory(){	
		return patientHistory;
	}
	public static String getDrContact(){	
		return drContact;
	}
	public static String getDocInfo(){	
		return docInfo;
	}
	public static boolean getStatus(){	
		return status;
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	//******************John's code
    /////////////////////////////////////////////////////////////////////////////////
	
	public static String getPerfomer(){
		return performer;
	}
	public static String getReasonForVisit(){
		return sReasonForVisit;
	}
/*
	public static CharSequence getPerformer() {
		// TODO Auto-generated method stub
		return null;
	}
*/
}
