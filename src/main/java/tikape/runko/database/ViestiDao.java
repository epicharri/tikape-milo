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
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM "
                + "Viesti WHERE id = ?;");
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
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti;");
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
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Viesti WHERE id = ?;");
        stmt.setString(1, key.toString());
        stmt.executeUpdate();
 
        stmt.close();
        connection.close();
 
    }
 
    public List<Viesti> findByViestiketju(Integer viestiketjuId) throws SQLException {
 
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM "
                + "Viesti WHERE viestiketju = ?;");
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
    
 
    public void createViesti(Integer viestiketjuId, String nimimerkki, String sisalto) throws SQLException {
        
        if (nimimerkki.isEmpty()|| sisalto.isEmpty()){
            return;
        } else {
            
        
        Connection connection = database.getConnection();
        
 
        java.util.Date paiva = new java.util.Date();
        Timestamp aika = new Timestamp(paiva.getTime());
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti(viestiketju, aika, nimimerkki, sisalto) "
                + "VALUES(?,?,?,?);");
        stmt.setString(1, viestiketjuId.toString());
        stmt.setString(2, aika.toString());
        stmt.setString(3, nimimerkki.toString());
        stmt.setString(4, sisalto.toString());
        stmt.executeUpdate();
        stmt.close();
        connection.close();
        }
    }
 
    public int kaikkiViestit() throws SQLException, NullPointerException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(id) FROM Viesti;");
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
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(v.id) " +
        "FROM Aihealue a, Viestiketju vk, Viesti v " +
        "WHERE a.id = vk.aihealue " +
        "AND vk.id = v.viestiketju " +
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
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(v.id) " +
        "FROM Viestiketju vk, Viesti v " +
        "WHERE vk.id = v.viestiketju " +
        "AND vk.id = ?;");
        stmt.setString(1, id.toString());
        ResultSet rs = stmt.executeQuery();
        int määrä = rs.getInt(1);
 
        rs.close();
        stmt.close();
        connection.close();
        return määrä;
    }
 
   

}