package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Aihealue;
import tikape.runko.domain.AihealueJaViimeisinViesti;
import tikape.runko.domain.Viesti;

public class AihealueJaViimeisinViestiDao {

    private Database database;
    private ViestiDao vDao;

    public AihealueJaViimeisinViestiDao(Database database) {
        this.database = database;
        this.vDao = new ViestiDao(database);
    }

    public Aihealue findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<AihealueJaViimeisinViesti> findAll() throws SQLException, Exception {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihealue");

        ResultSet rs = stmt.executeQuery();

        List<AihealueJaViimeisinViesti> aihealueet = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            if (viimeisinViesti(id) == null) {
                aihealueet.add(new AihealueJaViimeisinViesti(id, nimi, "", ""));
                System.out.println(nimi);
            } else {
                String sisalto = viimeisinViesti(id).getSisalto();
                String aika = viimeisinViesti(id).getAika();

                aihealueet.add(new AihealueJaViimeisinViesti(id, nimi, sisalto, aika));
                System.out.println(nimi);
            }

        }

        rs.close();
        stmt.close();
        connection.close();

        return aihealueet;

    }

    public Viesti viimeisinViesti(Integer key) throws Exception {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Viesti.id FROM Viesti, Viestiketju, Aihealue "
                + "WHERE Aihealue.id = Viestiketju.aihealue "
                + "AND Viestiketju.id = Viesti.viestiketju "
                + "AND Aihealue.id = ? "
                + "ORDER BY(Viesti.aika) DESC "
                + "LIMIT 1");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        int id = rs.getInt("id");

        Viesti v = this.vDao.findOne(id);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
