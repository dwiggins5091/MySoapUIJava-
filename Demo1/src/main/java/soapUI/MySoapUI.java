package soapUI;

import java.util.Scanner;

import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import groovy.lang.*;
import groovy.util.*;

public class MySoapUI 
{

	public static void main(String[] args) throws Exception 
	{
		// TODO Auto-generated method stub
		Scanner keyboard = new Scanner(System.in);
		String projectName = "MyTestSoapUIJavaProject";
		String project_WSDL = "http://wsf.cdyne.com/WeatherWS/Weather.asmx?WSDL";
		
//		System.out.println("Hello, Welcome to the Demon SoapUI Project Creator");
//
//		System.out.println("Please Enter the name of your new SoapUI Project: ");
//		projectName = keyboard.nextLine();
//		
//		System.out.println("Please specify your WSDL location: ");
//		project_WSDL = keyboard.nextLine();
//--------------------------------------------------
		
		WsdlProject project = new WsdlProject();//Creates a new SoapUI Project Object
		project.setName(projectName);//Sets the SoapUI Project Name
		
		WsdlInterface wsdl = WsdlInterfaceFactory.importWsdl(project, project_WSDL, true)[0];//Binds the specified Wsdl to an Interface so that it can be manipulate
		int c = wsdl.getOperationCount();//For PTC SITE Sync, There is only 1 operation (PTC Request)
		System.out.println(c);//Check to see if the operation count is as expected
	}

}
