package com.group7.data;

import java.util.ArrayList;

public class findData {
	String[] s1=new String[50];
	String[] str1=new String[50];
	
	String[] s2= new String[50];
	String[] str2=new String[50];
	
	String[] s3= new String[50];
	String[] str3=new String[50];
	
	String[] s5= new String[50];
	String[] str5=new String[50];
	
//	String[] s= new String[50];
//	String[] str=new String[50];
	String[] procedure=new String[50];
	String[] sochist=new String[50];
	String[] immunization=new String[50];
	String[] fhistory=new String[50];
	String[] problem=new String[50];
	String[] text=new String[200];
	String[] rVisit=new String[50];
	
	int p=0;
	String sProcedures="";
	ArrayList<String> Procedures=new ArrayList<String>();
	
	String sSocialHistory="";
	ArrayList<String> SocialHistory=new ArrayList<String>();
	
	String sImmunizations="";
	ArrayList<String> Immunizations=new ArrayList<String>();
	
	String sFamilyHistory="";
	ArrayList<String> FamilyHistory=new ArrayList<String>();
	
	String sProblems="";
	ArrayList<String> Problems=new ArrayList<String>();
	
	String sReasonForVisit="";
	ArrayList<String> ReasonForVisit=new ArrayList<String>();
	
	String sdocument="";
	String sPatienthistory="";
	String sdrcontact="";
	String sperformer = "";
	//String docInfo = "";
	ArrayList<String> Patienthistory=new ArrayList<String>();
	//////////////////////////////
	ArrayList<String> documentid=new ArrayList<String>();
	String sdocumentid="";
	
	ArrayList<String> atime=new ArrayList<String>();
	String stime="";
	
	ArrayList<String> aauthor=new ArrayList<String>();
	String rsauthor="";
	
	ArrayList<String> aentered=new ArrayList<String>();
	String rsentered="";
	
	String[] root=new String[1000];
	String[] time=new String[1000];
	String[] author=new String[1000];
	String[] dcontact=new String[1000];
	String[] entered=new String[1000];
	//////////////////////
	char[] cyear=new char[10];
	char[] cmonth=new char[10];
	char[] cday=new char[10];
	char[] chour=new char[10];
	char[] cmins=new char[10];
	char[] csec=new char[10];
	char[] cother=new char[10];
	String syear="";
	String[] smonth={"January", "February", "March", "April", "May", "June", 
			"July", "August", "September", "October", "November", "December"};
	String sday="";
	String shour="";
	String smins="";
	String ssec="";
	String sother="";
	ArrayList<String> document=new ArrayList<String>();
	////////////////////////////
	String[] ppcp=new String[1000];
	String[] RParty=new String[1000];
	String[] DContactInfo=new String[1000];
	
	ArrayList<String> DrContact=new ArrayList<String>();
	
	///////////////////////////
	public String getProcedures(){
		Procedures.add("\nPROCEDURES");
		for(int i=0;i<50;i++){
			s1[i]="procedure"+(i+1);
			str1[i]="//content[@ID='"+s1[i]+"']/text()";
		}
		
		for(int i=0;i<str1.length && str1[i]!=null; i++){
			procedure=TestXPath.read(str1[i]);
			if(procedure[0]==null){
				break;
			}
			Procedures.add((i+1)+". "+procedure[0]);		
		}
		for(int i=0;i<Procedures.size();i++)
		{
//			System.out.println(Patienthistory.get(i));
			sProcedures=sProcedures+Procedures.get(i)+"\n\n";
		}	
		return sProcedures;	
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	//****************GET PERFROMER
    //////////////////////////////////////////////////////////////////////////////////////
	public String getPerformer(){
		String performer ="";
		String performerpcp="";
//		DrContact.add("Performer:");
		ppcp=TestXPath.read("//serviceEvent/performer[@typeCode='PRF']/assignedEntity/assignedPerson/name/*/text()");
//		performerpcp=ppcp[0]; 
//		System.out.println(performerpcp);
		for(int i =0; i<3; ++i)
		{
			performerpcp=performerpcp+ppcp[i]+" "; 
		}
		return performerpcp;
		
	}
	
	public String getSocialHistory(){	
		SocialHistory.add("\nSOCIAL HISTORY");
		
		for(int i=0;i<50;i++){
			s2[i]="sochist"+(i+1);
			str2[i]="//content[@ID='"+s2[i]+"']/text()";
		}
		
		for(int i=0;i<str2.length && str2[i]!=null; i++){
			sochist=TestXPath.read(str2[i]);
			if(sochist[0]==null){
				break;
			}
//			System.out.println((i+1)+". "+sochist[0]);
//			text[p]=(i+1)+". "+sochist[0];p++;
			SocialHistory.add((i+1)+". "+sochist[0]);
		}
		for(int i=0;i<SocialHistory.size();i++)
		{
//			System.out.println(Patienthistory.get(i));
			sSocialHistory=sSocialHistory+SocialHistory.get(i)+"\n\n";
		}	
		return sSocialHistory;	
	}
	public String getImmunizations(){
		Immunizations.add("\nIMMUNIZATIONS");
		
		for(int i=0;i<50;i++){
			s3[i]="immunization"+(i+1);
			str3[i]="//content[@ID='"+s3[i]+"']/text()";
		}
		
		for(int i=0;i<str3.length && str3[i]!=null; i++){
			immunization=TestXPath.read(str3[i]);
			if(immunization[0]==null){
				break;
			}
//			System.out.println((i+1)+". "+immunization[0]);
//			text[p]=(i+1)+". "+immunization[0];p++;
			Immunizations.add((i+1)+". "+immunization[0]);
		}
		
		for(int i=0;i<Immunizations.size();i++)
		{
//			System.out.println(Patienthistory.get(i));
			sImmunizations=sImmunizations+Immunizations.get(i)+"\n\n";
		}	
		return sImmunizations;	
		
	}
	
	public String getFamilyHistory(){	
		FamilyHistory.add("\nFAMILY HISTORY");		
		fhistory=TestXPath.read("//paragraph/text()");
		FamilyHistory.add(fhistory[2]);
		
		for(int i=0;i<FamilyHistory.size();i++)
		{
//			System.out.println(Patienthistory.get(i));
			sFamilyHistory=sFamilyHistory+FamilyHistory.get(i)+"\n\n";
		}	
		return sFamilyHistory;	
		
	}
	
	public String getReasonForVisit(){	
		ReasonForVisit.add("\nREASON FOR VISIT");		
		rVisit=TestXPath.read("//paragraph/text()");
		//rVisit=TestXPath.read("//section/code[@code='29299-5']/paragraph/text()");
		ReasonForVisit.add(rVisit[2]);
		
		for(int i=0;i<ReasonForVisit.size();i++)
		{
//			System.out.println(Patienthistory.get(i));
			sReasonForVisit=sReasonForVisit+ReasonForVisit.get(i)+"\n\n";
		}	
		return sReasonForVisit;	
		
	}
	
	public String getProblems(){	
		Problems.add("\nPROBLEMS");
		
		for(int i=0;i<50;i++){
			s5[i]="problem"+(i+1);
			str5[i]="//content[@ID='"+s5[i]+"']/text()";
		}
		
		for(int i=0;i<str5.length && str5[i]!=null; i++){
			problem=TestXPath.read(str5[i]);
			if(problem[0]==null){
				break;
			}

			Problems.add((i+1)+". "+problem[0]);
		}
		
		for(int i=0;i<Problems.size();i++)
		{
//			System.out.println(Patienthistory.get(i));
			sProblems=sProblems+Problems.get(i)+"\n\n";
		}	
		return sProblems;	
		
	}
	public String getPatienthistory(){		

	   /* Patienthistory.add("PROCEDURES");
		for(int i=0;i<50;i++){
			s[i]="procedure"+(i+1);
			str[i]="//content[@ID='"+s[i]+"']/text()";
		}
		
		for(int i=0;i<str.length && str[i]!=null; i++){
			procedure=TestXPath.read(str[i]);
			if(procedure[0]==null){
				break;
			}
			Patienthistory.add((i+1)+". "+procedure[0]);
			
		}*/
//		System.out.println("\nSOCIAL HISTORY");
//		text[p]="\nSOCIAL HISTORY";p++;
		/*Patienthistory.add("\nSOCIAL HISTORY");
		
		for(int i=0;i<50;i++){
			s[i]="sochist"+(i+1);
			str[i]="//content[@ID='"+s[i]+"']/text()";
		}
		
		for(int i=0;i<str.length && str[i]!=null; i++){
			sochist=TestXPath.read(str[i]);
			if(sochist[0]==null){
				break;
			}
//			System.out.println((i+1)+". "+sochist[0]);
//			text[p]=(i+1)+". "+sochist[0];p++;
			Patienthistory.add((i+1)+". "+sochist[0]);
		}*/
		
//		System.out.println("\nIMMUNIZATIONS");
//		text[p]="\nIMMUNIZATIONS";p++;
		/*Patienthistory.add("\nIMMUNIZATIONS");
		
		for(int i=0;i<50;i++){
			s[i]="immunization"+(i+1);
			str[i]="//content[@ID='"+s[i]+"']/text()";
		}
		
		for(int i=0;i<str.length && str[i]!=null; i++){
			immunization=TestXPath.read(str[i]);
			if(immunization[0]==null){
				break;
			}
//			System.out.println((i+1)+". "+immunization[0]);
//			text[p]=(i+1)+". "+immunization[0];p++;
			Patienthistory.add((i+1)+". "+immunization[0]);
		}*/
		
//		System.out.println("\nFAMILY HISTORY");
//		text[p]="\nFAMILY HISTORY";p++;
		
		/*Patienthistory.add("\nFAMILY HISTORY");
		
		fhistory=TestXPath.read("//paragraph/text()");
//		System.out.println(fhistory[2]);
//		text[p]=fhistory[2];p++;
		Patienthistory.add(fhistory[2]);*/
		
//		System.out.println("\nPROBLEMS");
//		text[p]="\nPROBLEMS";p++;
		/*Patienthistory.add("\nPROBLEMS");
		
		for(int i=0;i<50;i++){
			s[i]="problem"+(i+1);
			str[i]="//content[@ID='"+s[i]+"']/text()";
		}
		
		for(int i=0;i<str.length && str[i]!=null; i++){
			problem=TestXPath.read(str[i]);
			if(problem[0]==null){
				break;
			}
//			System.out.println((i+1)+". "+problem[0]);
//			text[p]=(i+1)+". "+problem[0];p++;
			Patienthistory.add((i+1)+". "+problem[0]);
		}
//		System.out.println("\n text");
		for(int i=0;i<30 ;i++){
			if(text[i]!=null){
//				System.out.println(text[i]);
//				output1=output1+text[i]+"\n";
			}
			
		}
//		System.out.println(output1);
		for(int i=0;i<Patienthistory.size();i++)
		{
//			System.out.println(Patienthistory.get(i));
			sPatienthistory=sPatienthistory+Patienthistory.get(i)+"\n";
		}	*/
		return sPatienthistory;	
	}
	
	public String getDocumentId(){
		
//		documentid.add("Document Id:");
		root=TestXPath.read("//id[@extension='Test CCDA']/@root");
		for(int i=0;i<10;i++){
			if(root[i]!=null)
//			System.out.println(root[i]);
			documentid.add("Test CCDA"+root[i]);
		}
		for(int i=0;i<documentid.size();i++)
		{
//			System.out.println(Patienthistory.get(i));
			sdocumentid=sdocumentid+documentid.get(i)+"\n";
		}	
		
		return sdocumentid;
	}
	
	public String getTime(){
		time=TestXPath.read("//effectiveTime/@value");
//		System.out.println(time[0]);
		time[0].getChars(0, 4, cyear, 0);
		time[0].getChars(4, 6, cmonth, 0);
		time[0].getChars(6, 8, cday, 0);
		time[0].getChars(8, 10, chour, 0);
		time[0].getChars(10, 12, cmins, 0);
		time[0].getChars(12, 14, csec, 0);
		time[0].getChars(14, 19, cother, 0);
		
//		in=Integer.parseInt(cmonth.toString());
//		int a=Integer.parseInt(cmonth.toString());

		int y=0;
		y=Integer.parseInt(String.valueOf(cmonth).trim(),10);
		syear=String.valueOf(cyear).trim();
		sday=String.valueOf(cday).trim();
		shour=String.valueOf(chour).trim();
		smins=String.valueOf(cmins).trim();
		ssec=String.valueOf(csec).trim();
		sother=String.valueOf(cother).trim();

		String out=smonth[y-1]+" "+sday+", "+syear+", "+shour+":"+smins+":"+ssec+" "+sother;
//		System.out.println(out);
//		atime.add("\nDocument Created:");
		atime.add(out);
		
		for(int i=0;i<atime.size();i++)
		{
//			System.out.println(Patienthistory.get(i));
			stime=stime+atime.get(i)+"\n";
		}	
		
		return stime;
	}

	public String getAuthor(){
		author=TestXPath.read("//author/assignedAuthor/assignedPerson/name/*/text()");
//		author=TestXPath.read("//name/given/text()");
		String sauthor="";
		for(int i=0;i<10;i++){
			if(author[i]!=null){
				sauthor=sauthor+author[i]+" ";	
			}
		}
//		System.out.println(sauthor);
//		aauthor.add("\nAuthor:");
		aauthor.add(sauthor);
		
		
		String scontact="";
		dcontact=TestXPath.read("//serviceProviderOrganization/addr/streetAddressLine/text()");
		aauthor.add(dcontact[0]);
		dcontact=TestXPath.read("//serviceProviderOrganization/addr/city/text()");
		scontact=dcontact[0]+", ";
		dcontact=TestXPath.read("//serviceProviderOrganization/addr/state/text()");
		scontact=dcontact[0]+" ";
		dcontact=TestXPath.read("//serviceProviderOrganization/addr/postalCode/text()");
		scontact=scontact+dcontact[0]+", ";
		dcontact=TestXPath.read("//serviceProviderOrganization/addr/country/text()");
		scontact=scontact+dcontact[0];
		aauthor.add(scontact);
		
		dcontact=TestXPath.read("//serviceProviderOrganization/telecom/@value");
		aauthor.add(dcontact[0]);	
		

		for(int i=0;i<aauthor.size();i++)
		{
//			System.out.println(Patienthistory.get(i));
			rsauthor=rsauthor+aauthor.get(i)+"\n";
		}	
		
		return rsauthor;
	}
	
	public String getEntered(){
		
		String senterd="";
		entered =TestXPath.read("//dataEnterer/assignedEntity/assignedPerson/name/*/text()");
		for(int i=0;i<10;i++){
			if(entered[i]!=null){
				senterd=senterd+entered[i]+" ";
//				System.out.println(entered[i]);
			}					
		}
//		System.out.println(senterd);
//		document.add("\nEntered by: ");
		aentered.add(senterd);
		for(int i=0;i<aentered.size();i++)
		{
//			System.out.println(Patienthistory.get(i));
			rsentered=rsentered+aentered.get(i)+"\n";
		}	
		
		return rsentered;
	}
	
	public String getDocumentationInfo(){
	/*	document.add("Document Id:");
		root=TestXPath.read("//id[@extension='Test CCDA']/@root");
		for(int i=0;i<10;i++){
			if(root[i]!=null)
//			System.out.println(root[i]);
			document.add("Test CCDA"+root[i]);
		}*/
		/*time=TestXPath.read("//effectiveTime/@value");
//		System.out.println(time[0]);
		time[0].getChars(0, 4, cyear, 0);
		time[0].getChars(4, 6, cmonth, 0);
		time[0].getChars(6, 8, cday, 0);
		time[0].getChars(8, 10, chour, 0);
		time[0].getChars(10, 12, cmins, 0);
		time[0].getChars(12, 14, csec, 0);
		time[0].getChars(14, 19, cother, 0);
		
//		in=Integer.parseInt(cmonth.toString());
//		int a=Integer.parseInt(cmonth.toString());

		int y=0;
		y=Integer.parseInt(String.valueOf(cmonth).trim(),10);
		syear=String.valueOf(cyear).trim();
		sday=String.valueOf(cday).trim();
		shour=String.valueOf(chour).trim();
		smins=String.valueOf(cmins).trim();
		ssec=String.valueOf(csec).trim();
		sother=String.valueOf(cother).trim();

		String out=smonth[y-1]+" "+sday+", "+syear+", "+shour+":"+smins+":"+ssec+" "+sother;
//		System.out.println(out);
		document.add("\nDocument Created:");
		document.add(out);*/
		
/*
//		author=TestXPath.read("//name/given/text()");
		String sauthor="";
		for(int i=0;i<10;i++){
			if(author[i]!=null){
				sauthor=sauthor+author[i]+" ";	
			}
		}
//		System.out.println(sauthor);
		document.add("\nAuthor:");
		document.add(sauthor);
		
		
		String scontact="";
		dcontact=TestXPath.read("//serviceProviderOrganization/addr/streetAddressLine/text()");
		document.add(dcontact[0]);
		dcontact=TestXPath.read("//serviceProviderOrganization/addr/city/text()");
		scontact=dcontact[0]+", ";
		dcontact=TestXPath.read("//serviceProviderOrganization/addr/state/text()");
		scontact=dcontact[0]+" ";
		dcontact=TestXPath.read("//serviceProviderOrganization/addr/postalCode/text()");
		scontact=scontact+dcontact[0]+", ";
		dcontact=TestXPath.read("//serviceProviderOrganization/addr/country/text()");
		scontact=scontact+dcontact[0];
		document.add(scontact);
		
		dcontact=TestXPath.read("//serviceProviderOrganization/telecom/@value");
		document.add(dcontact[0]);	*/
//		for(int i=0;i<10;i++){
//			if(dcontact[i]!=null){
//				scontact=scontact+dcontact[i]+" ";	
//			}			
////			document.add(dcontact[i]);
//		}
//		System.out.println(scontact);
		
		
		String senterd="";
		entered =TestXPath.read("//dataEnterer/assignedEntity/assignedPerson/name/*/text()");
		for(int i=0;i<10;i++){
			if(entered[i]!=null){
				senterd=senterd+entered[i]+" ";
//				System.out.println(entered[i]);
			}					
		}
		System.out.println(senterd);
		document.add("\nEntered by: ");
		document.add(senterd);
		
		for(int i=0;i<document.size();i++)
		{
//			System.out.println(Patienthistory.get(i));
			sdocument=sdocument+document.get(i)+"\n";
		}	
		
		return sdocument;
	}
	public String getDrContact(){
		String performer ="";
		String performerpcp="";
		DrContact.add("Performer:");
		ppcp=TestXPath.read("//serviceEvent/performer[@typeCode='PRF']/functionCode/@displayName");
		performerpcp="\nPerformer "+ppcp[0]+": ";
	//	ppcp=TestXPath.read("//performer[@typeCode='PRF']/assignedEntity/assignedPerson/*/text()");
		ppcp=TestXPath.read("//serviceEvent/performer[@typeCode='PRF']/assignedEntity/assignedPerson/name/*/text()");
//		System.out.println(ppcp[0]);
		for(int i=0;i<10;i++){
			if(ppcp[i]!=null){
				performer=performer+ppcp[i]+" ";
				if(i%3==2){
					DrContact.add(performer);
					performer="";
				}
				if (i==2){DrContact.add(performerpcp);}
			}
		}
		
		DrContact.add("\nContact info: ");
		String spcontact="";
		dcontact=TestXPath.read("//serviceEvent/performer[@typeCode='PRF']/assignedEntity/addr/streetAddressLine/text()");
		DrContact.add(dcontact[1]);
		dcontact=TestXPath.read("//serviceEvent/performer[@typeCode='PRF']/assignedEntity/addr/city/text()");
		spcontact=dcontact[0]+", ";
		dcontact=TestXPath.read("//serviceEvent/performer[@typeCode='PRF']/assignedEntity/addr/state/text()");
		spcontact=dcontact[0]+" ";
		dcontact=TestXPath.read("//serviceEvent/performer[@typeCode='PRF']/assignedEntity/addr/postalCode/text()");
		spcontact=spcontact+dcontact[0]+", ";
		dcontact=TestXPath.read("//serviceEvent/performer[@typeCode='PRF']/assignedEntity/addr/country/text()");
		spcontact=spcontact+dcontact[0];
		DrContact.add(spcontact);	
		dcontact=TestXPath.read("//serviceEvent/performer[@typeCode='PRF']/assignedEntity/telecom/@value");
		DrContact.add(dcontact[0]);
		
		for(int i=0;i<DrContact.size();i++)
		{
//			System.out.println(Patienthistory.get(i));
			sdrcontact=sdrcontact+DrContact.get(i)+"\n";
		}
		
		return sdrcontact;
	}
}
