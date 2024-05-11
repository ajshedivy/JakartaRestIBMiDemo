package com.github.ajshedivy.restfuldemo.resources;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400JDBCDataSource;
import com.ibm.as400.access.AS400SecurityException;

import io.github.theprez.dotenv_ibmi.IBMiDotEnv;

public class ConnectionUtils {

    public static Connection getConnection() throws SQLException, IOException, AS400SecurityException {
        final AS400 as400 = IBMiDotEnv.getNewSystemConnection(true);
        AS400JDBCDataSource ds = new AS400JDBCDataSource(as400);
        return ds.getConnection();
    }
}
