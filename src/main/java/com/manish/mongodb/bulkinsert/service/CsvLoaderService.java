package com.manish.mongodb.bulkinsert.service;


import com.manish.mongodb.bulkinsert.model.Person;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.List;

@Service
@Slf4j
public class CsvLoaderService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void loadCsvDataWithBulkOps() throws Exception {
        var csvReader = new InputStreamReader(new ClassPathResource("person_records.csv").getInputStream());

        List<Person> people = new CsvToBeanBuilder<Person>(csvReader)
                .withType(Person.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
                .parse();

        // Clean up existing data
        mongoTemplate.dropCollection(Person.class);


        // save() in loop
        long start1 = System.currentTimeMillis();
        for (Person p : people) {
            mongoTemplate.save(p);
        }
        long end1 = System.currentTimeMillis();
        log.info("Save in loop: {} records in {} ms", people.size(), (end1 - start1));

        mongoTemplate.dropCollection(Person.class);

        // insertAll()
        long start2 = System.currentTimeMillis();
        
        mongoTemplate.insertAll(people);
        long end2 = System.currentTimeMillis();
        log.info("insertAll(): {} records in {} ms", people.size(), (end2 - start2));


        mongoTemplate.dropCollection(Person.class);

        // bulkOps.insert()
        long start3 = System.currentTimeMillis();
        BulkOperations bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Person.class);

        bulkOps.insert(people);

        bulkOps.execute();
        long end3 = System.currentTimeMillis();
        log.info("bulkOps.insert(): {} records in {} ms", people.size(), (end3 - start3));


    }
}
