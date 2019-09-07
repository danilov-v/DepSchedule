package com.varb.schedule.integration;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Transactional
public class TestSqlExecutor {

    //TODO refactor to set the values from resources
    private final String dbHostUrl = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1";
    private final String dbUserName = "sa";
    private final String dbPassword = "";

    public void executeSqlScript(String sqlFilePath) throws SQLException {
        if (sqlFilePath == null) {
            return;
        }

        try (Connection conn = getDBConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource(sqlFilePath));
        }
    }

    //It is used to clear resources after test execution.
    //Possible to pass multiple table names as parameter separated by ';' literal.
    //If you do so, consider an order of the tables that have references
    public void deleteAllFrom(String compositeTableName) throws SQLException {
        if (compositeTableName == null) {
            return;
        }

        //Check if we have more than one table
        String[] tables = compositeTableName.split(";");

        try (Connection conn = getDBConnection()) {
            Statement s = conn.createStatement();
            for (String table : tables) {
                String query = "DELETE FROM " + table;
                s.execute(query);
            }
        }
    }

    private Connection getDBConnection() throws SQLException {
        return DriverManager.getConnection(dbHostUrl, dbUserName, dbPassword);
    }
}
