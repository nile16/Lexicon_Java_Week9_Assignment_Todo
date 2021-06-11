package se.lexicon.data;

import se.lexicon.model.Todo;

import java.util.Collection;

public interface ToDoItems {
    public Todo create(Todo todo);
    public Collection<Todo> findAll();
    public Todo findById(int id);
}
