package model

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

class XPathAssertion extends AbstractAssertion {

	String xpath
	String expectedValue

	public boolean assertCondition(HttpResponse result) {
		return assertCondition(result.response)
	}
	
	@Override
	public boolean assertCondition(Object result) {
		if(!result) return false
		def element = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(result.bytes)).documentElement
		def value = XPathFactory.newInstance().newXPath().evaluate( xpath, element, XPathConstants.STRING )
		return expectedValue == value
	}
}