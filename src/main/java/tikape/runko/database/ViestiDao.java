package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Viesti;
import tikape.runko.domain.Viestiketju;

public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;
    private ViestiketjuDao viestiketjuDao;

    public ViestiDao(Database database) {
        this.database = database;
        this.viestiketjuDao = new ViestiketjuDao(database);
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        int id = rs.getInt("id");
        String nimimerkki = rs.getString("nimimerkki");
        String aika = rs.getString("aika");
        String sisalto = rs.getString("sisalto");

        Viestiketju viestiketju = this.viestiketjuDao.findOne(rs.getInt("id"));

        Viesti v = new Viesti(id, viestiketju, nimimerkki, sisalto, aika);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nimimerkki = rs.getString("nimimerkki");
            String aika = rs.getString("aika");
            String sisalto = rs.getString("sisalto");

            //muokkaa tämä!!!:
            Viestiketju viestiketju = null;

            viestit.add(new Viesti(id, viestiketju, nimimerkki, sisalto, aika));
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
            stmt.executeUpdate("DELETE FROM Viesti WHERE id = " + key);

            stmt.close();
            connection.close();

        } catch (Throwable t) {

            System.out.println("Error >> " + t.getMessage());
        }

    }

}
