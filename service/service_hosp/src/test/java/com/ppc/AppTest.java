package com.ppc;

import com.mongodb.client.result.DeleteResult;
import com.ppc.bean.Actor;
import com.ppc.yygh.repository.ActorRepository;
import com.ppc.yygh.hosp.ServiceHospMainStarter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@SpringBootTest(classes = ServiceHospMainStarter.class)
public class AppTest {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public void testInsert(){
        mongoTemplate.insert(new Actor(5,"ppc",true,new Date()));
    }
    @Test
    public void testDelete(){
        Query query=new Query(Criteria.where("actName").is("ppc").and("id").is(5));
        DeleteResult result = mongoTemplate.remove(query, Actor.class);
        long count = result.getDeletedCount();
    }
    @Test
    public void testUpdate(){
        mongoTemplate.upsert(new Query(Criteria.where("id").is(7)),
                new Update().set("actName","ppc520"),
                Actor.class);
    }
    @Test
    public void testSelect(){
        Pattern pattern=Pattern.compile(".*p.*",Pattern.CASE_INSENSITIVE);
        List<Actor> actName = mongoTemplate.find(Query.query(Criteria.where("actName").regex(pattern)), Actor.class);
        System.out.println("actName = " + actName);
    }
    @Test
    public void testSelectPage(){
        Integer pageNum=1;
        Integer size=3;

        Map map=new HashMap();
        long total = mongoTemplate.count(Query.query(Criteria.where("gender").is(true)),
                Actor.class);
        map.put("total",total);
        List<Actor> rows = mongoTemplate.find(Query.query(Criteria.where("gender").is(true))
                .skip((pageNum-1)*size)
                .limit(3),
                Actor.class);
        map.put("rows",rows);
        for (Actor row : rows) {
            System.out.println("row = " + row);
        }
    }

    @Autowired
    private ActorRepository actorRepository;
    @Test
    public void testRepository(){
        List<Actor> all = actorRepository.findAll();
        for (Actor actor : all) {
            System.out.println("actor = " + actor);
        }
    }
}
