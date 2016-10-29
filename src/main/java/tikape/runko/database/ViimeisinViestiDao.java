package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tikape.runko.domain.Aihealue;
import tikape.runko.domain.ViimeisinViesti;
import tikape.runko.domain.Viesti;

public class ViimeisinViestiDao {

    private Database database;
    private ViestiDao vDao;

    public ViimeisinViestiDao(Database database) {
        this.database = database;
        this.vDao = new ViestiDao(database);
    }

    public Aihealue findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<ViimeisinViesti> AihealueJaViimeisinViesti() throws SQLException, Exception {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Aihealue;");

        ResultSet rs = stmt.executeQuery();

        List<ViimeisinViesti> aihealueet = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            if (viimeisinViestiByAihealue(id) == null) {
                aihealueet.add(new ViimeisinViesti(id, nimi, "", ""));
                
            } else {
                String sisalto = viimeisinViestiByAihealue(id).getSisalto();
                String aika = viimeisinViestiByAihealue(id).getAika();

                aihealueet.add(new ViimeisinViesti(id, nimi, sisalto, aika));
                
            }

        }

        rs.close();
        stmt.close();
        connection.close();

        return aihealueet;

    }

    public List<ViimeisinViesti> ViestiketjuJaViimeisinViesti(Integer aihealueId) throws SQLException, Exception {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM "
                + "Viestiketju WHERE aihealue == ?;");
        stmt.setObject(1, aihealueId);
        //stmt.setObject(1, Integer.toString(aihealueId));

        ResultSet rs = stmt.executeQuery();

        List<ViimeisinViesti> viestiketjut = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nimi = rs.getString("otsikko");

            if (viimeisinViestiByViestiketju(id) == null) {
                viestiketjut.add(new ViimeisinViesti(id, nimi, "", "", 0));
                
            } else {
                String sisalto = viimeisinViestiByViestiketju(id).getSisalto();
                String aika = viimeisinViestiByViestiketju(id).getAika();

                viestiketjut.add(new ViimeisinViesti(id, nimi, sisalto, aika, vDao.kaikkiViestitByViestiketju(id)));
               
            }

        }

        rs.close();
        stmt.close();
        connection.close();
        
        Collections.sort(viestiketjut);
        
        List<ViimeisinViesti> kymmenen = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (viestiketjut.size()> i){
                kymmenen.add(viestiketjut.get(i));
            }            
        }
        return kymmenen;

    }

    public Viesti viimeisinViestiByAihealue(Integer key) throws Exception {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Viesti.id FROM Viesti, Viestiketju, Aihealue "
                + "WHERE Aihealue.id = Viestiketju.aihealue "
                + "AND Viestiketju.id = Viesti.viestiketju "
                + "AND Aihealue.id = ? "
                + "ORDER BY(Viesti.aika) DESC "
                + "LIMIT 1;");
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

    public Viesti viimeisinViestiByViestiketju(Integer key) throws Exception {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Viesti.id FROM Viesti, Viestiketju\n"
                + "WHERE Viestiketju.id = Viesti.viestiketju "
                + "AND Viestiketju.id = ? "
                + "ORDER BY(Viesti.aika) DESC "
                + "LIMIT 1;");
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
