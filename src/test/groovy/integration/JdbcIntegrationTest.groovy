package integration
import org.junit.Testimport state.TestCaseBuilder
class JdbcIntegrationTest extends TestCaseBuilder {
	void "Make a JDBC queue and validate the result"() {				testCase("Make a JDBC")		.jdbc("jdbc:oracle:thin:candes/soa@localhost:1521", "SELECT * FROM CANDES.F55CM01P")		.stepName("JDBC CANDES.F55CM01P")		.contains("WR55378")				assert context.execute()			}		
}
