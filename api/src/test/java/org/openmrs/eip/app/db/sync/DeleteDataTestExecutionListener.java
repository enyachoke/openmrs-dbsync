/*
 * Add Copyright
 */
package org.openmrs.eip.app.db.sync;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * Custom TestExecutionListener that resets all tables in the database by deleting all rows in them.
 * Typically, this listener should be configured to run after every test method has executed
 */
public class DeleteDataTestExecutionListener extends AbstractTestExecutionListener {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	private static final String DELETE = "DELETE FROM ";
	
	/**
	 * @see AbstractTestExecutionListener#afterTestMethod(TestContext)
	 */
	@Override
	public void afterTestMethod(TestContext testContext) throws Exception {
		ApplicationContext ctx = testContext.getApplicationContext();
		DataSource dataSource = ctx.getBean(SyncConstants.OPENMRS_DATASOURCE_NAME, DataSource.class);
		
		log.debug("Deleting all data from OpenMRS DB tables...");
		
		try (Connection c = dataSource.getConnection()) {
			List<String> tables = getTableNames(c);
			Statement statement = c.createStatement();
			try {
				for (String tableName : tables) {
					statement.executeUpdate(DELETE + tableName);
				}
			}
			finally {
				if (statement != null) {
					statement.close();
				}
			}
		}
		
	}
	
	private static List<String> getTableNames(Connection connection) throws SQLException {
		DatabaseMetaData dbmd = connection.getMetaData();
		ResultSet tables = dbmd.getTables(null, null, null, new String[] { "TABLE" });
		List<String> tableNames = new ArrayList();
		while (tables.next()) {
			tableNames.add(tables.getString("TABLE_NAME"));
		}
		return tableNames;
	}
	
}
