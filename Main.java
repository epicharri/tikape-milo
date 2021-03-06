package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.*;

public class Main {
//toimiiko githubin filedropperi
    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();

        ViestiDao vDao = new ViestiDao(database);
        ViestiketjuDao vkDao = new ViestiketjuDao(database);
        AihealueDao aDao = new AihealueDao(database);
        
        System.out.println("viestejä" + vDao.kaikkiViestit());

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/aihealue", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aihealueet", aDao.findAll());
            map.put("viestienMaara", vDao.kaikkiViestit());
            
            //map.put("uudetViestit", vDao.uusinViesti());
           
//Elisan kokeiluja: 
//            List<Aihealue> kaikkiaihealueet = new ArrayList<>();
//            kaikkiaihealueet = aDao.findAll();
//
//            for (Aihealue a : kaikkiaihealueet) {
//                if (!aDao.viimeisinViesti(a.getId()).equals(null)) {
//                    map.put(a.getNimi(), aDao.viimeisinViesti(a.getId()));
//                }
//
//            }

            return new ModelAndView(map, "aihealue");
        }, new ThymeleafTemplateEngine());

        
        post("/aihealue", (req, res) -> {
            aDao.createAihealue(req.queryParams("aihealue"));
            res.redirect("/aihealue");
            return "ok";
        });

        get("/aihealue/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestiketjut", vkDao.findByAihelue(Integer.parseInt(req.params("id"))));
            map.put("aihealue", aDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("viestienMaara", vDao.findByViestiketju(Integer.parseInt(req.params("id"))));
            return new ModelAndView(map, "viestiketju");
        }, new ThymeleafTemplateEngine());

        post("/aihealue/:id", (req, res) -> {
            vkDao.createViestiketju(Integer.parseInt(req.params("id")), req.queryParams("viestiketju"));
            res.redirect("/aihealue/" + req.params(":id"));
            return "ok";
        });

        get("/aihealue/viestiketju/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", vDao.findByViestiketju(Integer.parseInt(req.params("id"))));
            map.put("viestiketju", vkDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("aihealue", aDao.findOne(vkDao.findOne(Integer.parseInt(req.params("id"))).getAihealue().getId()));
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
