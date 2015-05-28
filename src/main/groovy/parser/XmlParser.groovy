package parser

import java.sql.ResultSet

class XmlParser {

	static String toXml(ResultSet resultSet) {
		StringBuffer result = new StringBuffer()
		while(resultSet.next()) {
			result.append("<row>")
			for(int columnIndex = 1 ; columnIndex < resultSet.getMetaData().getColumnCount()+1; columnIndex++) {
				result.append("<"+resultSet.getMetaData().getColumnName(columnIndex)+">")
				result.append(resultSet.getObject(columnIndex))
				result.append("</"+resultSet.getMetaData().getColumnName(columnIndex)+">")
			}
			result.append("</row>")
		}
		return result.toString()
	}
	
}
