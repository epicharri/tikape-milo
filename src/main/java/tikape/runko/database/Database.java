package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;
    private boolean debug;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
        
        
    }
    
    //tämä kopsattu 28.HelloOneToMany tehtävästä:
    public void setDebugMode(boolean d) {
        debug = d;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
//        lista.add("CREATE TABLE Viesti (\n"
//                + "id integer PRIMARY KEY, \n"
//                + "aika datetime NOT NULL, \n"
//                + "nimimerkki varchar(20) NOT NULL, \n"
//                + "sisalto varchar(300) NOT NULL, \n"
//                + "viestiketju, FOREIGN KEY(viestiketju) REFERENCES Viestiketju(id));");
//        lista.add("CREATE TABLE Viestiketju (\n"
//                + "id integer PRIMARY KEY, \n"
//                + "otsikko varchar(30) NOT NULL,\n"
//                + "aihealue, \n"
//                + "FOREIGN KEY(aihealue) REFERENCES Aihealue(id));");
//        lista.add("CREATE TABLE Aihealue (\n"
//                + "id integer PRIMARY KEY, \n"
//                + "nimi varchar(30) NOT NULL);");
//        lista.add("INSERT INTO Aihealue(nimi) VALUES ('Koirat');");
//        lista.add("INSERT INTO Aihealue(nimi) VALUES ('Kissat');");
//        lista.add("INSERT INTO Aihealue(nimi) VALUES ('Kilpikonnat'");
//        lista.add("INSERT INTO Viestiketju(otsikko, aihealue) VALUES ('Milo on cute!', 1);");
//        lista.add("INSERT INTO Viestiketju(otsikko, aihealue) VALUES ('Kissat on parast', 2);");
//        lista.add("INSERT INTO Viestiketju(otsikko, aihealue) VALUES ('Kilpparit haisee', 3);");
//        lista.add("INSERT INTO Viesti(aika, nimimerkki, sisalto, viestiketju) VALUES(datetime('now', 'localtime'), 'Liitu', 'Heippa kaikki. Kilpparit on oikeesti ihan tyhmii.', 4);");
//        lista.add("INSERT INTO Viesti(aika, nimimerkki, sisalto, viestiketju) VALUES(datetime('now', 'localtime'), 'Patu', 'Heippa kaikki. Kissat on oikeesti ihan supertyhmiityhmii.', 2);");
        return lista;
    }
    
    
    //tämä kopsattu 28.HelloOneToMany tehtävästä:
    public int update(String updateQuery, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(updateQuery);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        int changes = stmt.executeUpdate();

        if (debug) {
            System.out.println("---");
            System.out.println(updateQuery);
            System.out.println("Changed rows: " + changes);
            System.out.println("---");
        }

        stmt.close();
        conn.close();

        return changes;
    }


    private void debug(ResultSet rs) throws SQLException {
        int columns = rs.getMetaData().getColumnCount();
        for (int i = 0; i < columns; i++) {
            System.out.print(
                    rs.getObject(i + 1) + ":"
                    + rs.getMetaData().getColumnName(i + 1) + "  ");
        }

        System.out.println();
    }

}
