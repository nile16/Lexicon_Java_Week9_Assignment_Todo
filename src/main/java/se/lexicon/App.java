package se.lexicon;


import se.lexicon.data.PersonDAO;
import se.lexicon.model.Person;

import java.sql.SQLException;
import java.util.Collection;

public class App
{
    public static void main( String[] args ) throws SQLException {
        PersonDAO pDao = new PersonDAO();
        //print(PersonDAO.create(new Person("Lars", "Larsson")));
        print(pDao.findById(4));
        print(pDao.update(new Person(4, "Lasse", "Karlson")));
        print(pDao.findById(4));

        print(pDao.findByName("arls"));

        //print(pDao.findAll());
        //print(pDao.deleteById(3));
        //print(pDao.findAll());

    }

    public static void print(Collection c) {
        for (Object item : c) {
            System.out.println(item);
        }
        System.out.println();
    }

    public static void print(Object o) {
        System.out.println(o);
        System.out.println();
    }
}
