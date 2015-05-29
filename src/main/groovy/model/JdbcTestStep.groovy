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
		def xmlResult
		Sql.newInstance(connectionURL).query(sql) {
			xmlResult = XmlParser.toXml(it)
		}
		assertions.each { result &= it.assertCondition(xmlResult) }
		return result
	}
	
}