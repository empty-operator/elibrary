package org.elibrary.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {

    private final DataSource source;
    private static DBManager dbManager;

    private DBManager() throws NamingException {
        source = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/postgres");
    }

    public static synchronized Connection getConnection() throws SQLException, NamingException {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager.source.getConnection();
    }

}
