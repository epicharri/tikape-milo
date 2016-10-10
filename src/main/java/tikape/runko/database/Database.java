package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
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
        lista.add("CREATE TABLE Viesti (\n" +
        "viesti_id integer PRIMARY KEY, \n" +
        "aikadatetime NOT NULL, \n" +
        "nimimerkki varchar(20) NOT NULL, \n" +
        "sisalto varchar(300) NOT NULL, \n" +
        "viestiketju_id, FOREIGN KEY(viestiketju_id) REFERENCES Viestiketju(viestiketju_id)););");
        lista.add("CREATE TABLE Viestiketju (\n" +
        "viestiketju_id integer PRIMARY KEY, \n" +
        "otsikko varchar(30) NOT NULL,\n" +
        "aihealue_id, \n" +
        "FOREIGN KEY(aihealue_id) REFERENCES Aihealue(aihealue_id));");
        lista.add("CREATE TABLE Aihealue (\n" +
        "aihealue_id integer PRIMARY KEY, \n" +
        "nimi varchar(30) NOT NULL);");
        lista.add("INSERT INTO Aihealue(nimi) VALUES ('Koirat');");
        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Homeros');");
        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Homeros');");
        lista.add("INSERT INTO Opiskelija (nimi) VALUES ('Homeros');");

        return lista;
    }
}
