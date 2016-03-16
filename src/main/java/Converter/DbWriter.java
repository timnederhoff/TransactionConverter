package Converter;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class DbWriter {

    private Connection c;
    private String outputName;
    private String tableName;
    private Statement stmt;

    public DbWriter(String outputName) {
        this.outputName = outputName;
        tableName = "MUTATIONS";
    }

    public void startConnection() throws Exception{
        File outputPath = new File(outputName);
        Files.deleteIfExists(outputPath.toPath());
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:" + outputName);
        c.setAutoCommit(false);
    }

    public void writeData(List<List<String>> mutaties) throws Exception {
        stmt = c.createStatement();
        for (List<String> mutatie : mutaties) {
            addToDB(mutatie);
        }
        stmt.close();
        c.commit();
    }

    private void addToDB(List<String> mutatie) throws Exception{
        String sql = "INSERT INTO " + tableName + " VALUES (" + arrayToCsv(mutatie) + ");";
        stmt.executeUpdate(sql);
    }

    public void createTable(Map<String, String> header) throws Exception {
        Statement stmt = c.createStatement();
        String sql = "CREATE TABLE " + tableName + " (" + headersToString(header) + ")";
        System.out.println("create table sql: " + sql);
        stmt.executeUpdate(sql);
        stmt.close();
    }

    private String headersToString(Map<String, String> columnNames) {
        String snake = "";
        for (String columnName : columnNames.keySet()) {
            snake += columnName + " " + columnNames.get(columnName) + ",";
        }
        return snake.substring(0,snake.length() - 1);
    }

    private String arrayToCsv(List<String> stringArr) throws Exception {
        String snake = "";
        for (String x : stringArr) {
            snake += "'" + x.replace("\"", "") + "', ";
        }
        return snake.substring(0,snake.length()-2);
    }

    public void closeConnection() {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
