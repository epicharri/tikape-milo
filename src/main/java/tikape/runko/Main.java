package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();
 
        ViestiDao vDao = new ViestiDao(database);
        ViestiketjuDao vkDao = new ViestiketjuDao(database);
        AihealueDao aDao = new AihealueDao(database);
        ViimeisinViestiDao vvDao = new ViimeisinViestiDao(database);

 
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
 
        get("/aihealue", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aihealueet", vvDao.AihealueJaViimeisinViesti());
            map.put("viestienMaara", vDao.kaikkiViestit());
            return new ModelAndView(map, "aihealue");
        }, new ThymeleafTemplateEngine());
 
       
        post("/aihealue", (req, res) -> {
            aDao.createAihealue(req.queryParams("aihealue"));
            res.redirect("/aihealue");
            return "ok";
        });
 
        get("/aihealue/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestiketjut", vvDao.ViestiketjuJaViimeisinViesti(Integer.parseInt(req.params("id"))));
            map.put("aihealue", aDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("viestienMaara", vDao.kaikkiViestitByAihealue(Integer.parseInt(req.params("id"))));
            return new ModelAndView(map, "viestiketju");
        }, new ThymeleafTemplateEngine());
 
        post("/aihealue/:id", (req, res) -> {
            vkDao.createViestiketju(Integer.parseInt(req.params("id")), req.queryParams("viestiketju"), req.queryParams("nimimerkki"), req.queryParams("sisalto"));
            res.redirect("/aihealue/" + req.params(":id"));
            return "ok";
        });
 
        get("/aihealue/viestiketju/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", vDao.findByViestiketju(Integer.parseInt(req.params("id"))));
            map.put("viestiketju", vkDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("aihealue", aDao.findOne(vkDao.findOne(Integer.parseInt(req.params("id"))).getAihealue().getId()));
            map.put("viestienMaara", vDao.kaikkiViestitByViestiketju(Integer.parseInt(req.params("id"))));
            return new ModelAndView(map, "viesti");
        }, new ThymeleafTemplateEngine());
       
        post("/aihealue/viestiketju/:id", (req, res) -> {
            vDao.createViesti(Integer.parseInt(req.params("id")), req.queryParams("nimimerkki"), req.queryParams("sisalto"));
            res.redirect("/aihealue/viestiketju/" + req.params(":id"));
            return "ok";
        });        
 
        get("/viestit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", vDao.findAll());
 
            return new ModelAndView(map, "viesti");
        }, new ThymeleafTemplateEngine());
 
    }
}
