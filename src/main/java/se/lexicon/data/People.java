package se.lexicon.data;

import se.lexicon.model.Person;

import java.util.Collection;

public interface People {
    public Person create(Person person);
    public Collection<Person> findAll();
    public Person findById(int id);
    public Collection<Person> findByName(String name);
    public Person update(Person person);
    public Boolean deleteById(int id);
}
