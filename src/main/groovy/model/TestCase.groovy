package model

public interface TestCase extends Executable {
	
	TestStep addTestStep(TestStep testStep)
	Closure setSetupScript(Closure clousure)
	Closure setTearDownScript(Closure clousure)	
	
}
