package listener;


import java.util.Arrays;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import extentreports.config.ExtentService;
import extentreports.config.ExtentTestManager;



public class ExtentITestListenerAdapter implements ITestListener, IInvokedMethodListener {
	
	 private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>(); 
	 ExtentTest child;
	 
	@Override
    public synchronized void onStart(ITestContext context) {
    	
        ExtentService.getInstance().setAnalysisStrategy(AnalysisStrategy.SUITE);
    }

    @Override
    public synchronized void onFinish(ITestContext context) {

        ExtentService.getInstance().flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
    	
    	
       child = ExtentTestManager.createMethod(result, true);
       test.set(child);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
    	
		String methodName=result.getMethod().getMethodName();
		String logText="<b>"+"TEST CASE:- "+ methodName.toUpperCase()+ " PASSED"+"</b>";		
		Markup m=MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		test.get().pass(m);
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        
        Throwable excepionMessage=result.getThrowable();
		test.get().fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Main Exception Occured:Click to see"
				+ "</font>" + "</b >" + "</summary>" +excepionMessage+" \n");
		
		String excepionMessages=Arrays.toString(result.getThrowable().getStackTrace());
        test.get().fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Detail Exception Occured:Click to see"
        		+ "</font>" + "</b >" + "</summary>" +excepionMessages.replaceAll(",", "<br>")+"</details>"+" \n");
        
        test.get().fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Combined Exception Occured:Click to see"
        		+ "</font>" + "</b >" + "</summary>" +excepionMessage+ "\n"+excepionMessages.replaceAll(",", "<br>")+"</details>"+" \n");
		
		String failureLogg="TEST CASE FAILED";
		Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
		test.get().log(Status.FAIL, m);
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
    	
		String methodName=result.getMethod().getMethodName();
		String logText="<b>"+"Test Case:- "+ methodName+ " Skipped"+"</b>";		
		Markup m=MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		test.get().skip(m);;
    }

    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    	
    }

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

		
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult result) {

	}
    

}
