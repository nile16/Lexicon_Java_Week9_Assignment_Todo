package se.lexicon;


import se.lexicon.data.PersonDAO;
import se.lexicon.data.ToDoDAO;
import se.lexicon.model.Person;
import se.lexicon.model.Todo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

public class App
{
    public static void main( String[] args ) throws SQLException {
        PersonDAO pDao = new PersonDAO();
        ToDoDAO tDao = new ToDoDAO();
        Person p1, p2, p3;
        Todo t1, t2, t3;

        print("Create Persons");
        print(p1 = pDao.create(new Person("Lars", "Larsson")));
        print(p2 = pDao.create(new Person("Kalle", "Karlsson")));
        print(p3 = pDao.create(new Person("Karl", "Johansson")));

        //print("Create Persons in error");
        //print(pDao.create(null));
        //print(pDao.create(new Person(13, "Lars", "Larsson")));

        print("Find all Persons");
        print(pDao.findAll());

        print("Find Person with Id " + p2.getId());
        print(pDao.findById(p2.getId()));

        print("Find Person with invalid Id 0");
        print(pDao.findById(0));

        print("Find Persons with name Karl");
        print(pDao.findByName("karl"));

        print("Update Person");
        p1.setFirstName("Lasse");
        p1.setLastName("Larson");
        print(pDao.update(p1));

        print("Create Todos");
        print(t1 = tDao.create(new Todo("Gräs", "Klipp gräset", LocalDate.of(2021, 10, 1), false, p1.getId())));
        print(t2 = tDao.create(new Todo("Snö", "Skotta uppfarten", LocalDate.of(2021, 12, 23), true, p2.getId())));
        print(t3 = tDao.create(new Todo("Ved", "Hugg ved", LocalDate.of(2021, 6, 1), false, null)));

        //print("Create Todos in error");
        //print(tDao.create(null));
        //print(tDao.create(new Todo(13, "Gräs", "Klipp gräset", LocalDate.of(2021, 10, 1), false, null)));
        //print(tDao.create(new Todo("Gräs", "Klipp gräset", LocalDate.of(2021, 10, 1), false, 0)));

        print("Find all Todos");
        print(tDao.findAll());

        print("Find Todo with id " + t1.getId());
        print(tDao.findById(t1.getId()));

        print("Find Todo with invalid id 0");
        print(tDao.findById(0));

        print("Find not done Todos");
        print(tDao.findByDoneStatus(false));

        print("Find done Todos");
        print(tDao.findByDoneStatus(true));

        print("Todos with assignee id " + p2.getId());
        print(tDao.findByAssignee(p2.getId()));

        print("Todos without assignee");
        print(tDao.findByUnassignedTodoItems());

        print("Update Todo");
        t1.setTitle("Gräsklippning");
        t1.setDescription("Klipp gräset under sommaren");
        t1.setDeadline(LocalDate.of(2021, 7, 1));
        t1.setAssigneeId(null);
        t1.setDone(true);
        print(tDao.update(t1));

        print("Delete all todos");
        for (Todo todo : tDao.findAll()) {
            print(tDao.deleteById(todo.getId()));
        }

        print("Delete all Persons");
        for (Person person : pDao.findAll()) {
            print(pDao.deleteById(person.getId()));
        }

        print("Find all Todos:");
        print(tDao.findAll());

        print("Find all Persons:");
        print(pDao.findAll());
    }

    public static void print(Collection c) {
        if (c.isEmpty()) System.out.println("None");
        for (Object item : c) {
            System.out.println(item);
        }
    }

    public static void print(Object o) {
        System.out.println(o);
    }

    public static void print(String s) {
        System.out.println("\n*** " + s + " ***");
    }
}
