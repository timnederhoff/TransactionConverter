package Converter;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DbWriter {

    private Connection c;
    private String outputName;
    private String[] header;
    private String tableName;
    private Statement stmt;
    private int mutationCounter;

    public DbWriter(String outputName) {
        this.outputName = outputName;
        tableName = "MUTATIONS";
        mutationCounter = 0;
    }

    public void startConnection() throws Exception{
        File outputPath = new File(outputName);
        Files.deleteIfExists(outputPath.toPath());
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:" + outputName);
        c.setAutoCommit(false);
    }

    public void writeData(List<String[]> mutaties) throws Exception {
        stmt = c.createStatement();
        for (String[] mutatie : mutaties) {
            addToDB(mutatie);
            mutationCounter++;
        }
        stmt.close();
        c.commit();
    }

    private void addToDB(String[] mutatie) throws Exception{
        String sql = "INSERT INTO " + tableName + " (" + arrayToCsv(header) + ") " +
                "VALUES (" + arrayToCsv(mutatie) + ");";
        stmt.executeUpdate(sql);
    }

    public void createTable(String[] header) throws Exception {
        this.header = header;
        Statement stmt = c.createStatement();
        String sql = "CREATE TABLE " + tableName + " (" + arrayToCsv(header).replace(",", "TEXT,") + ")";
        stmt.executeUpdate(sql);
        stmt.close();
    }

    private String arrayToCsv(String[] stringArr) throws Exception {
        String snake = "";
        for (String x : stringArr) {
            snake += "'" + x.replace("\"", "") + "', ";
        }
        return snake.substring(0,snake.length()-2);
    }

    public int getMutationCounter() {
        return mutationCounter;
    }

    public void closeConnection() {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
