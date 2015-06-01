package model

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

class XPathAssertion extends AbstractAssertion {

	String xpath
	String text

	@Override
	public boolean assertCondition(Object result) {
		def element = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(result.response.bytes)).documentElement
		def value = XPathFactory.newInstance().newXPath().evaluate( xpath, element, XPathConstants.STRING )
		return text == value
	}
}