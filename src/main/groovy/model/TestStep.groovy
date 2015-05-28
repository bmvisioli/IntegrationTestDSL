package model

public interface TestStep extends Executable {

	Assertion addAssertion(Assertion assertion)
	Object getResult()
				
}
