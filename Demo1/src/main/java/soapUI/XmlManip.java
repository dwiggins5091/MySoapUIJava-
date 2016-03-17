package soapUI;

import java.util.ArrayList;

import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestRequestStepResult;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.model.testsuite.TestStepResult;


public class XmlManip {
	
	public static ArrayList<String> requestToList(String wsdlRequest)
	{
		ArrayList<String> xmlTagHolder = new ArrayList<String>();
		
		String[] xmlTags = wsdlRequest.split("\n");//Splits the String based using \n - NewLine as the delimiter
		
		for(int i = 0; i < xmlTags.length; i++)
		{
			xmlTagHolder.add(xmlTags[i]);
		}
	
		return xmlTagHolder;
		
	}
	
	public static String runStepAndGetResponseContent(WsdlTestCaseRunner runner, TestStep testStep) {
        TestStepResult result = runner.runTestStep(testStep);
        if (result instanceof WsdlTestRequestStepResult) {
            return ((WsdlTestRequestStepResult) result).getResponse().getContentAsString();
        }
        return null;
}

}
