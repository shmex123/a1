package cs414.a1.stprice;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestAll {
	
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(
				CompanyTest.class,
				WorkerTest.class,
				ProjectTest.class,
				QualificationTest.class
		);
		for(Failure failure : result.getFailures()) {
			System.out.println(failure);
		}
		System.out.println(result.wasSuccessful() ? "Success" : "Fail");
	}

}
