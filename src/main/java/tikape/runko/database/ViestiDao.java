package tikape.runko.database;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Aihealue;
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
 
        rs.close();
        stmt.close();
        connection.close();
 
        return new Viesti(id, viestiketju, nimimerkki, sisalto, aika);
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
 
            Viestiketju viestiketju = this.viestiketjuDao.findOne(rs.getInt("id"));
 
            viestit.add(new Viesti(id, viestiketju, nimimerkki, sisalto, aika));
        }
 
        rs.close();
        stmt.close();
        connection.close();
 
        return viestit;
    }
 
    @Override
    public void delete(Integer key) throws SQLException {
 
        Connection connection = database.getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM Viesti WHERE id = " + key);
 
        stmt.close();
        connection.close();
 
    }
 
    public List<Viesti> findByViestiketju(Integer viestiketjuId) throws SQLException {
 
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE viestiketju = ?");
        stmt.setObject(1, Integer.toString(viestiketjuId));
        ResultSet rs = stmt.executeQuery();
 
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nimimerkki = rs.getString("nimimerkki");
            String aika = rs.getString("aika");
            String sisalto = rs.getString("sisalto");
 
            Viestiketju viestiketju = this.viestiketjuDao.findOne(rs.getInt("id"));
 
            viestit.add(new Viesti(id, viestiketju, nimimerkki, sisalto, aika));
        }
 
        rs.close();
        stmt.close();
        connection.close();
 
        return viestit;
    }
    // Aika puuttuu!
 
    public void createViesti(Integer viestiketjuId, String nimimerkki, String sisalto) throws SQLException {
        Connection connection = database.getConnection();
        Statement stmt = connection.createStatement();
 
        java.util.Date paiva = new java.util.Date();
        Timestamp aika = new Timestamp(paiva.getTime());
 
        stmt.executeUpdate("INSERT INTO Viesti(viestiketju, aika, nimimerkki, sisalto) VALUES('" + viestiketjuId + "', '" + aika + "', '" + nimimerkki + "', '" + sisalto + "')");
 
        stmt.close();
        connection.close();
    }
 
    public int kaikkiViestit() throws SQLException, NullPointerException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(id) FROM Viesti");
        ResultSet rs = stmt.executeQuery();
       
        int määrä;
       
        määrä = rs.getInt(1);
               
        rs.close();
        stmt.close();
        connection.close();
        return määrä;
    }
   
    public int kaikkiViestitByAihealue(Integer id) throws SQLException, NullPointerException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(v.id) \n" +
        "FROM Aihealue a, Viestiketju vk, Viesti v\n" +
        "WHERE a.id = vk.aihealue\n" +
        "AND vk.id = v.viestiketju\n" +
        "AND a.id = ?;");
        stmt.setString(1, id.toString());
        ResultSet rs = stmt.executeQuery();
        int määrä = rs.getInt(1);
 
        rs.close();
        stmt.close();
        connection.close();
        return määrä;
    }
    public int kaikkiViestitByViestiketju(Integer id) throws SQLException, NullPointerException{
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(v.id) \n" +
        "FROM Viestiketju vk, Viesti v\n" +
        "WHERE vk.id = v.viestiketju\n" +
        "AND vk.id = ?;");
        stmt.setString(1, id.toString());
        ResultSet rs = stmt.executeQuery();
        int määrä = rs.getInt(1);
 
        rs.close();
        stmt.close();
        connection.close();
        return määrä;
    }
 
   
    public List<Viesti>  uusinViesti() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT Viesti.sisalto FROM Viestiketju, Viesti, Aihealue"
                + " WHERE Aihealue.id = Viestiketju.aihealue"
                        + "AND Viestiketju.id = Viesti.viestiketju"
                        + "ORDER BY(Viesti.aika)"
                        + "DESC LIMIT 1");
        //stmt.setObject(1, Integer.toString(aihealueId) );
        ResultSet rs = stmt.executeQuery();
        //Viesti viesti = this.viestiketjuDao.findOne(aihealueId);
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nimimerkki = rs.getString("nimimerkki");
            String aika = rs.getString("aika");
            String sisalto = rs.getString("sisalto");
 
            Viestiketju viestiketju = this.viestiketjuDao.findOne(rs.getInt("id"));
 
            viestit.add(new Viesti(id, viestiketju, nimimerkki, sisalto, aika));
        }
       
        //String viesti = rs.getString("sisalto");
        rs.close();
        stmt.close();
        connection.close();
 
        return viestit;
    }
}