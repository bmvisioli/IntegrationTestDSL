package model

abstract class AbstractAssertion {
	
	String name = "unnamed"
	
	abstract boolean assertCondition(Object result)
}
