package soapUI;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.StandaloneSoapUICore;
import com.eviware.soapui.config.TestStepConfig;
import com.eviware.soapui.config.WsdlRequestConfig;
import com.eviware.soapui.impl.WsdlInterfaceFactory;
import com.eviware.soapui.impl.support.AbstractHttpRequest;
import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlRequest;
import com.eviware.soapui.impl.wsdl.WsdlTestSuite;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.registry.WsdlTestRequestStepFactory;
import com.eviware.soapui.model.support.PropertiesMap;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.testsuite.TestRunner.Status;
import com.eviware.soapui.support.types.StringToObjectMap;

import org.jdom.*;

//import groovy.lang.*;
//import groovy.util.*;

public class MySoapUI 
{

	public static void main(String[] args) throws Exception 
	{
		// TODO Auto-generated method stub
		Scanner keyboard = new Scanner(System.in);
		String projectName = "MyTestSoapUIJavaProject";
		String project_WSDL = "http://wsf.cdyne.com/WeatherWS/Weather.asmx?WSDL";
		String testSuiteName = "myTestSuiteName";
		
		//		System.out.println("Hello, Welcome to the Demon SoapUI Project Creator");
		//
		//		System.out.println("Please Enter the name of your new SoapUI Project: ");
		//		projectName = keyboard.nextLine();
		//		
		//		System.out.println("Please specify your WSDL location: ");
		//		project_WSDL = keyboard.nextLine();
		//--------------------------------------------------
		// create File
		File projectFile = new File("DemoResults1.xml");//Creates the file to which the new project will be save

		//SoapUI.setSoapUICore(new StandaloneSoapUICore(true));
		WsdlProject project = new WsdlProject();//Creates a new SoapUI Project Object
		project.setName(projectName);//Sets the SoapUI Project Name

		WsdlInterface wsdl = WsdlInterfaceFactory.importWsdl(project, project_WSDL, true)[0];//Binds the specified Wsdl to an Interface so that it can be manipulate
		int c = wsdl.getOperationCount();//For PTC SITE Sync, There is only 1 operation (PTC Request)
		//System.out.println(c);//Check to see if the operation count is as expected

		WsdlTestSuite myTestSuite = project.addNewTestSuite(testSuiteName);//Create a new Test Suite and adds it to the SoapUI project
		myTestSuite.setName(testSuiteName);//Sets Test Suite name
		System.out.println("\r\n");
		
//		for(int i =0; i < 1; i++)
//		{
//			
			WsdlOperation operation = wsdl.getOperationAt(0);//retrieves an operation located in the WSDL and initializes it to an operation object
			/* Modifies the operations Request Schema before adding to a testcase .... Using groovy */ 
			String opName = operation.getName();//retrieves the operation name and set it to a string
			String opContents = operation.getRequestAt(0).getRequestContent();// Saves the Operation's Request Content to a String for Manipulation
			
			ArrayList<String> xmlHolder = XmlManip.requestToList(opContents);
			
			
			String myZipHolder = xmlHolder.get(5);
			String myZip = myZipHolder.replace("?", "23513");
			xmlHolder.remove(5);
			xmlHolder.add(5, myZip+"\r\n");
			String myRequest = ""; 
			
			for(int i = 0; i < xmlHolder.size(); i++)
			{
				myRequest += xmlHolder.get(i);
			}
			
			//System.out.println(xmlHolder.toString());
			//System.out.println(myZip);
			WsdlRequest request = operation.addNewRequest("My City Test");
			request.setRequestContent(myRequest);
			//System.out.println(operation.getRequestByName("My City Test").getRequestContent());//Prints the Operation's Request Schema
			TestStepConfig testStepConfig = WsdlTestRequestStepFactory.createConfig(request, "my"+opName);
			WsdlTestCase testCase = myTestSuite.addNewTestCase("my"+opName);
			WsdlTestStep testStep = testCase.addTestStep(testStepConfig);
			testStep.setName("my"+opName+"Step");
			
		//}
				
		project.saveIn(projectFile);//Saves SoapUI Project

		
		WsdlTestCaseRunner myTestRunner = new WsdlTestCaseRunner((WsdlTestCase) testCase, new StringToObjectMap(testCase.getProperties()) );;

		for(int j=0; j < myTestSuite.getTestCaseCount(); j++)
		{
			
			System.out.println(myTestSuite.getTestCaseAt(j).getName());//Returns the list of Test Cases in the SoapUI Suite
			System.out.println("Now executing Test Suite: "+ myTestSuite.getName());
			System.out.println("------------------------------------>");
			System.out.println("Now executing Test case: "+ myTestSuite.getTestCaseAt(j).getName());
			//myTestRunner = myTestSuite.getTestCaseAt(j).run( null, false);//Takes in a property Map and a Test Case for the Test Runner
			System.out.println(XmlManip.runStepAndGetResponseContent(myTestRunner, myTestSuite.getTestCaseAt(j).getTestStepAt(0)));
			
		}

		

	}

}
