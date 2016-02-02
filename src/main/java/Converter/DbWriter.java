package Converter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DbWriter {

    private static Connection c;
    private static String outputName;
    private static String[] header;

    public DbWriter(String outputName) {
        this.outputName = outputName;
    }

    private static void startConnection() throws Exception{
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:" + outputName);
        //open connection
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:");
        c.setAutoCommit(false);
    }

    public void writeData(List<String[]> mutaties) {
        for (String[] mutatie : mutaties) {
            addToDB();
        }

        String line;
        while ((line = br.readLine()) != null) {
            mutatie = line.split("\",\"");
            addToDB(header,mutatie);
        }
    }

    private void addToDB(String[] cols, String[] mutatie) throws Exception{

        Statement stmt = c.createStatement();
        String sql = "INSERT INTO MUTATIONS (" + toString(cols) + ") " +
                "VALUES (" + arrayToString(mutatie) + ");";
        System.out.println(sql);
        stmt.executeUpdate(sql);

        stmt.close();
        c.commit();
    }

    public static void createTable(String cols) throws Exception {
        header = cols;
        Statement stmt = c.createStatement();
        String sql = "CREATE TABLE MUTATIONS (" + cols + ")";
        stmt.executeUpdate(sql);
        stmt.close();
    }

    private static String arrayToString(String[] stringArr) throws Exception {
        String snake = "";
        for (String x : stringArr) {
            snake += "'" + x.replace("\"", "").replace("'", "") + "', ";
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
