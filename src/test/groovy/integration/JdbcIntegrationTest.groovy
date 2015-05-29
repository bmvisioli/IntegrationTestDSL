package integration
import org.junit.Testimport state.TestCaseBuilder
class JdbcIntegrationTest extends TestCaseBuilder {		void "Make a JDBC query and validate the result"() {				testCase("Make a JDBC")			.sql("jdbc:oracle:thin:candes/soa@localhost:1521", "SELECT * FROM CANDES.F55CM01P")				.stepName("JDBC CANDES.F55CM01P")				.contains("WR55378")			.sql("jdbc:oracle:thin:candes/soa@localhost:1521", "SELECT * FROM CANDES.F55CM01H")				.contains("1432846281533")					assert context.execute()			}		void "Make a Http request and validate the result"() {				testCase("Make a JDBC")			.httpRequest("http://localhost:8080/soap", "</request>")				.contains("WR55378")					assert !context.execute()			}		
}
