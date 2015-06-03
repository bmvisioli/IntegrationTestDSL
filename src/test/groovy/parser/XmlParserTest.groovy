package parser

import java.sql.ResultSet
import java.sql.ResultSetMetaData;

import org.junit.Test

import static org.mockito.Mockito.*

class XmlParserTest {

	private ResultSet createMockedResultSet() {
		def resultSetMock = mock(ResultSet);
		when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false)
		when(resultSetMock.getObject(1)).thenReturn("value1")
		when(resultSetMock.getObject(2)).thenReturn("value2")
		def resultSetMetadataMock = mock(ResultSetMetaData)
		when(resultSetMetadataMock.getColumnCount()).thenReturn(2)
		when(resultSetMetadataMock.getColumnName(1)).thenReturn("column1")
		when(resultSetMetadataMock.getColumnName(2)).thenReturn("column2")
		when(resultSetMock.getMetaData()).thenReturn(resultSetMetadataMock)

		return resultSetMock
	}

	private String getParsedString() {
		return "<rows><row><column1>value1</column1><column2>value2</column2></row><row><column1>value1</column1><column2>value2</column2></row></rows>"
	}

	@Test
	void "Parsing from ResultSet produces a valid XML"() {
		assert XmlParser.toXml(createMockedResultSet()) == getParsedString()
	}
}
