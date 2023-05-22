package com.ppc.yygh.repository;

import com.ppc.bean.Actor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface ActorRepository extends MongoRepository<Actor,Integer> {//第一个泛型指定bean类，第二个泛型指定主键的类型
}
