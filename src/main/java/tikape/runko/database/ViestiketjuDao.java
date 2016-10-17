package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Aihealue;
import tikape.runko.domain.Viestiketju;

public class ViestiketjuDao implements Dao<Viestiketju, Integer> {

    private Database database;
    private Aihealue aihealue;
    private AihealueDao aihealueDao;

    public ViestiketjuDao(Database database) {
        this.database = database;
        this.aihealueDao = new AihealueDao(database);
    }

    @Override
    public Viestiketju findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        int id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");

        Aihealue aihealue = this.aihealueDao.findOne(rs.getInt("aihealue"));

        Viestiketju v = new Viestiketju(id, otsikko, aihealue);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Viestiketju> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju");

        ResultSet rs = stmt.executeQuery();
        List<Viestiketju> viestit = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");

            Aihealue aihealue = this.aihealueDao.findOne(rs.getInt("aihealue"));

            viestit.add(new Viestiketju(id, otsikko, aihealue));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        
        try (Connection connection = database.getConnection();) {

            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM Viestiketju WHERE id = " + key);

            stmt.close();
            connection.close();

        } catch (Throwable t) {

            System.out.println("Error >> " + t.getMessage());
        }

        
    }

}
