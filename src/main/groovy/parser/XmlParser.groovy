package parser

import java.sql.ResultSet

class XmlParser {

	static String toXml(ResultSet resultSet) {
		StringBuffer result = new StringBuffer()
		result.append("<rows>")
		while(resultSet.next()) {
			result.append("<row>")
			for(int columnIndex = 1 ; columnIndex < resultSet.getMetaData().getColumnCount()+1; columnIndex++) {
				if(resultSet.getObject(columnIndex) != null)	 {
					result.append("<"+resultSet.getMetaData().getColumnName(columnIndex)+">")
					result.append(resultSet.getObject(columnIndex))
					result.append("</"+resultSet.getMetaData().getColumnName(columnIndex)+">")
				}
			}
			result.append("</row>")
		}
		result.append("</rows>")
		return result.toString()
	}
	
}
