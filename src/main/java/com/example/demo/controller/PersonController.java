package com.example.demo.controller;

import com.example.demo.model.PersonModel;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
public class PersonController {
    private PersonService service;

    @Autowired
    public PersonController(PersonService service){
        this.service =service;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<PersonModel> create(@RequestBody PersonModel person){
        return new ResponseEntity<>(service.create(person), HttpStatus.CREATED);
    }

    @GetMapping(value = "/read/{Id}")
    public ResponseEntity<PersonModel> readById(@PathVariable Long Id){
        return new ResponseEntity<>(service.readById(Id), HttpStatus.OK);
    }

    @GetMapping(value = "/readAll")
    public ResponseEntity<List<PersonModel>> readAll(){
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{Id}")
    public ResponseEntity<PersonModel> updateById(@PathVariable Long Id, @RequestBody PersonModel newData){
        return new ResponseEntity<>(service.update(Id, newData), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{Id}")
    public ResponseEntity<PersonModel> deleteById(@PathVariable Long Id){
        return new ResponseEntity<>(service.deleteById(Id), HttpStatus.OK);
    }


}
