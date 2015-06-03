package model

abstract class AbstractAssertion {
	
	String name
	
	abstract boolean assertCondition(Object result)
}
