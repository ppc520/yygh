package com.ppc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Document("ppcTestActor")
public class Actor {
   // @Id
    private Integer id;
 //   @Field("act_name")
    private String actName;
    private Boolean gender;
    private Date birth;
}
