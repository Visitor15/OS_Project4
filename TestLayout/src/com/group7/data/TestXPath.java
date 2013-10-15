package com.group7.data;


import java.io.IOException;  
import java.io.InputStream;  

import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.ParserConfigurationException;  
import javax.xml.xpath.XPath;  
import javax.xml.xpath.XPathConstants;  
import javax.xml.xpath.XPathExpression;  
import javax.xml.xpath.XPathExpressionException;  
import javax.xml.xpath.XPathFactory;  

import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.xml.sax.SAXException;  

public class TestXPath {  

//  public static void main(String[] args) {  
//      read();  //get specific data using XPath, get the data you want
//    
//  }  
    
  public static String[] read(String str) {  
	  String[] strr=new String[50];
	  
      try {  
    	  
          DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
          DocumentBuilder builder = dbf.newDocumentBuilder();  
          InputStream in = TestXPath.class.getClassLoader().getResourceAsStream("CDA.xml");  
          Document doc = builder.parse(in);  
          XPathFactory factory = XPathFactory.newInstance();  
          XPath xpath = factory.newXPath();  
            
          // XPath Introduction： http://w3school.com.cn/xpath/  
          
//          XPathExpression expr = xpath.compile("//class[@name='class1']/student/@name");  
//          XPathExpression expr = xpath.compile("//raceCode/@displayName");  
//          XPathExpression expr = xpath.compile("//list[@listType='ordered']/item/content[@ID='procedure1']");  
//          XPathExpression expr = xpath.compile("//content[@ID='procedure1']/text()"); 
//          XPathExpression expr = xpath.compile("//component/section/title/text()"); 
          XPathExpression expr = xpath.compile(str); 
          NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);  
          for (int i = 0; i < nodes.getLength(); i++) {  
//              System.out.println("name = " + nodes.item(i).getNodeValue());  
        	  if(nodes.item(i).getNodeValue()!=null){
        		  strr[i]=nodes.item(i).getNodeValue();
//        		  System.out.println("name = " + nodes.item(i).getNodeValue());  
        	  }
                  }  
      } catch (XPathExpressionException e) {  
          e.printStackTrace();  
      } catch (ParserConfigurationException e) {  
          e.printStackTrace();  
      } catch (SAXException e) {  
          e.printStackTrace();  
      } catch (IOException e) {  
          e.printStackTrace();  
      }  
      return strr;
  }  
    
}  