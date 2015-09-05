import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class GiroReader {
    public static void main(String[] args) {
        try {
            String filePath = "/home/tim/Documents/giroMutaties/15-07-2015_15-08-2015.csv", line, mutatie[], colString ="", header[];
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            header = br.readLine().split("\",\"");
            for (String x : header) {
                colString += x.toUpperCase().replace("\"","") + " TEXT,";
            }
            colString = colString.substring(0,colString.length()-1) + ")";
            createTable(colString);

            while ((line = br.readLine()) != null) {
                mutatie = line.split("\",\"");
                addToDB(header,mutatie);
            }
        }catch (Exception x){
            x.printStackTrace();
        }

    }

    private static void addToDB(String[] cols, String[] mutatie) throws Exception{
        Connection c = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:test.db");
        c.setAutoCommit(false);
        Statement stmt = c.createStatement();
        String sql = "INSERT INTO MUTATIONS (" + toString(cols) + ") " +
                "VALUES (" + toString(mutatie) + ");";
        System.out.println(sql);
        stmt.executeUpdate(sql);

        stmt.close();
        c.commit();
        c.close();
    }

    private static void createTable(String cols) throws Exception {
        Connection c = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:test.db");
        Statement stmt = c.createStatement();
        String sql = "CREATE TABLE MUTATIONS (" + cols;
        stmt.executeUpdate(sql);
        stmt.close();
    }

    private static void startDB() throws Exception{
        Connection c = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:test.db");
        c.close();
    }

    private static String toString(String[] stringArr) throws Exception {
        String snake = "";
        for (String x : stringArr) {
            snake += "'" + x.replace("\"", "").replace("'", "") + "', ";
        }
        return snake.substring(0,snake.length()-2);
    }

}
