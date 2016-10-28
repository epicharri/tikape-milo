package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tikape.runko.domain.Aihealue;

public class AihealueDao implements Dao<Aihealue, Integer> {

    private Database database;

    public AihealueDao(Database database) {
        this.database = database;
    }

    @Override
    public Aihealue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM "
                + "Aihealue WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        int id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        rs.close();
        stmt.close();
        connection.close();

        return new Aihealue(id, nimi);

    }

    @Override
     public List<Aihealue> findAll() throws SQLException {
 
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihealue");
        ResultSet rs = stmt.executeQuery();
 
        List<Aihealue> aihealueet = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nimi = rs.getString("nimi");
 
            aihealueet.add(new Aihealue(id, nimi));
        }
 
        rs.close();
        stmt.close();
        connection.close();
        Collections.sort(aihealueet);
        return aihealueet;
 
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Aihealue WHERE id = ?");
        stmt.setString(1, key.toString());
        stmt.executeQuery();
        stmt.close();
        connection.close();

    }

    public void createAihealue(String nimi) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Aihealue(nimi) VALUES(?)");
        stmt.setString(1, nimi);
        stmt.executeUpdate();
        stmt.close();
        connection.close();

    } 
    
}
