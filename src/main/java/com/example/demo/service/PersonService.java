package com.example.demo.service;

import com.example.demo.model.PersonModel;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    private PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository){
        this.repository = repository;
    }

    public PersonModel create(PersonModel person){
        return repository.save(person);
    }

    public PersonModel readById(Long Id){
        return repository.findById(Id).get();
    }

    public List<PersonModel> readAll(){
        Iterable<PersonModel> allPeople = repository.findAll();
        List<PersonModel> personModelList = new ArrayList<>();
        allPeople.forEach(personModelList::add);
        return personModelList;
    }

    public PersonModel update(Long Id, PersonModel newPersonData){
        PersonModel personInDatebase = this.readById(Id);
        personInDatebase.setFirstName(newPersonData.getFirstName());
        personInDatebase.setLastName(newPersonData.getLastName());
        personInDatebase.setBirthDate(newPersonData.getBirthDate());
        personInDatebase = repository.save(personInDatebase);
        return personInDatebase;
    }

    public PersonModel deleteById(Long Id){
        PersonModel personToBeDeleted = this.readById(Id);
        repository.delete(personToBeDeleted);
        return personToBeDeleted;
    }
}
