package model

import groovy.sql.Sql
import parser.XmlParser
import groovy.util.logging.Slf4j

@Slf4j
class SqlTestStep extends AbstractTestStep {
	
	static Map dbConnections = [:]
	
	String connectionURL
	String sql
	
	@Override
	public boolean run() {
		def con = getDbConnection(connectionURL)
		if(sql ==~ "SELECT.*") {
			con.query(sql) { result = XmlParser.toXml(it) }
		} else {
			con.executeUpdate(sql)
		}
		return true
	}
	
	private static Sql getDbConnection(String url) {
		if(dbConnections[url]) return dbConnections[url]
		def con = Sql.newInstance(url)
		dbConnections.put(url,con)
		return con
	}
	
}