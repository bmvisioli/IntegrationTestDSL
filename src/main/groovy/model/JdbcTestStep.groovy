package model

import groovy.sql.Sql
import parser.XmlParser

class JdbcTestStep extends AbstractTestStep {
	
	String connectionURL
	String sql
	String result
	
	@Override
	public boolean execute() {
		boolean result = true
		def xmlResult = XmlParser.toXml(Sql.newInstance(connectionURL).executeQuery(sql))
		assertions.each { result &= it.assertCondition(xmlResult) }
		return result
	}
	
}