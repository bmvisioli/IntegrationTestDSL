package model

import groovy.sql.Sql
import parser.XmlParser
import groovy.util.logging.Slf4j

@Slf4j
class SqlTestStep extends AbstractTestStep {
	
	String connectionURL
	String sql
	
	@Override
	public boolean run() {
		Sql.newInstance(connectionURL).query(sql) {
			result = XmlParser.toXml(it)
		}
		return true
	}
	
}