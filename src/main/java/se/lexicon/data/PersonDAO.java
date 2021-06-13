package se.lexicon.data;

import se.lexicon.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersonDAO implements People{

    public Person create(Person person) {

        if(person == null) throw new IllegalArgumentException("Person person was null");
        if(findById(person.getId()) != null) throw new IllegalArgumentException("Person person is already persisted");

        Person created = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("INSERT INTO person (first_name, last_name) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.execute();

            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            created = new Person(
                resultSet.getInt(1),
                person.getFirstName(),
                person.getLastName()
            );
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return created;
    }

    public Collection<Person> findAll() {

        List<Person> foundPersons = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM person");

            while(resultSet.next()){
                Person person = new Person(
                        resultSet.getInt("person_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                );
                foundPersons.add(person);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return foundPersons;
    }

    public Person findById(int id) {

        Person foundPerson = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("SELECT * FROM person WHERE person_id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                foundPerson = new Person(
                        resultSet.getInt("person_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return foundPerson;
    }

    public Collection<Person> findByName(String name) {

        List<Person> matchingPersons = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("SELECT * FROM person WHERE CONCAT(first_name, ' ', last_name) LIKE CONCAT('%',?,'%')");
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                Person person = new Person(
                        resultSet.getInt("person_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                );
                matchingPersons.add(person);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return matchingPersons;
    }

    public Person update(Person person) {

        if(person == null) throw new IllegalArgumentException("Person person was null");
        if(findById(person.getId()) == null ) throw new IllegalArgumentException("Person person is not yet persisted");

        Connection connection = null;
        PreparedStatement statement = null;
        int rowsAffected = 0;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("UPDATE person SET first_name = ?, last_name = ? WHERE person_id = ?");
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setInt(3, person.getId());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(statement, connection);
        }
        if (rowsAffected != 1) return null;

        return person;
    }

    public Boolean deleteById(int id) {

        Connection connection = null;
        PreparedStatement statement = null;
        int rowsAffected = 0;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("DELETE FROM person WHERE person_id = ?");
            statement.setInt(1, id);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(statement, connection);
        }
        return rowsAffected > 0;
    }

    private void closeAll(AutoCloseable...closeables){
        if(closeables != null){
            for(AutoCloseable closeable : closeables){
                if(closeable != null){
                    try {
                        closeable.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
