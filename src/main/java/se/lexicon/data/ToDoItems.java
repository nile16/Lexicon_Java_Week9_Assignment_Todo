package se.lexicon.data;

import se.lexicon.model.Person;
import se.lexicon.model.Todo;

import java.util.Collection;

public interface ToDoItems {
    public Todo create(Todo todo);
    public Collection<Todo> findAll();
    public Todo findById(int id);
    public Collection<Todo> findByDoneStatus(Boolean done);
    public Collection<Todo> findByAssignee(int assigneeId);
    public Collection<Todo> findByAssignee(Person assignee);
    public Collection<Todo> findByUnassignedTodoItems();
    public Todo update(Todo todo);
    public Boolean deleteById(int id);
}
