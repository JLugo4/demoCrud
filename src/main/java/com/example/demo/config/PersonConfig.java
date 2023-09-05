package com.example.demo.config;

import com.example.demo.model.PersonModel;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
public class PersonConfig {
    @Autowired
    private PersonRepository repository;

    @PostConstruct
    public void setup(){
        PersonModel person1 = new PersonModel();
        person1.setFirstName("Jarek");
        person1.setLastName("Lugo");

        PersonModel person2 = new PersonModel();
        person2.setFirstName("Dean");
        person2.setLastName("Walls");

        repository.saveAll(Arrays.asList(person1, person2));
    }
}
