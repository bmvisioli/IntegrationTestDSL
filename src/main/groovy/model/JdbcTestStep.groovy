package model

import groovy.sql.Sql
import parser.XmlParser
import groovy.util.logging.Slf4j

@Slf4j
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
		assertions.each {
			 def assertionResult = it.assertCondition(xmlResult)
			 log.info("Assertion ${it?.name} ${assertionResult ? 'succeed' : 'failed'}")
			 result &= assertionResult
		}
		return result
	}
	
}