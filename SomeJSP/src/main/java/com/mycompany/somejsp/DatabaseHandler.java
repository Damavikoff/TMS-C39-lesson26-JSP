/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.somejsp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.ds.PGSimpleDataSource;

/**
 *
 * @author SharpSchnitzel
 */
public class DatabaseHandler<T> {
    private final PGSimpleDataSource datasource;
    private String lastError;

    public DatabaseHandler() {
        this.datasource = new PGSimpleDataSource();
        datasource.setUrl("jdbc:postgresql://172.20.209.10:5432/postgres");
        datasource.setUser("subzero");
        datasource.setPassword("postgres");
    }
    
    private void handleSQLException(SQLException e) {
        
        switch (e.getSQLState()) {
            case "08001": {
                this.lastError = "Unable to connect.";
                break;
            }
            case "42P01": {
                this.lastError = "Specified table does not exist.";
                break;
            }
            case "42703": {
                this.lastError = "Wrong column name.";
                break;
            }
            case "23502": {
                this.lastError = "An attempt to assign a null value to a column with a not-null constraint.";
                break;
            }    
            case "42601": {
                this.lastError = "Syntax error.";
                break;
            }
            //...........
            default: {
                this.lastError = e.getSQLState();
                Logger.getLogger("WTH?!!").log(Level.SEVERE, null, e);
            }
        }
    }
    
    public String getLastError() {
        return this.lastError;
    }

    public List<Map<String, Object>> executeSelect(String query) {
        
        List<Map<String, Object>> result = new ArrayList<>();
        try (Connection cn = datasource.getConnection();
            PreparedStatement st = cn.prepareStatement(query);
            ResultSet rs = st.executeQuery()) {
            
            ResultSetMetaData meta = rs.getMetaData();
            int columns = meta.getColumnCount();
            
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= columns; i++) {
                    row.put(meta.getColumnName(i), rs.getObject(i));
                }
                result.add(row);
            }
            return result;
            
        } catch (SQLException e) {
            handleSQLException(e);
            return null;
        }
    }
    
    public Integer executeUpdate(String query) {
        try (Connection cn = datasource.getConnection();
            PreparedStatement st = cn.prepareStatement(query)) {
            return st.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
            return null;
        }
    }
}
