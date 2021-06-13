package se.lexicon.data;

import se.lexicon.model.Person;
import se.lexicon.model.Todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ToDoDAO implements ToDoItems {
    @Override
    public Todo create(Todo todo) {
        if(todo == null) throw new IllegalArgumentException("Todo todo was null");
        if(todo.getId() != 0) throw new IllegalArgumentException("Todo todo is already persisted");
        if (todo.getAssigneeId() != null) {
            if ((new PersonDAO()).findById(todo.getAssigneeId()) == null)
                throw new IllegalArgumentException("Assignee not persisted");
        }

        Todo created = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("INSERT INTO todo_item (title, description, deadline, done, assignee_id) VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, todo.getTitle());
            statement.setString(2, todo.getDescription());
            statement.setDate(3, java.sql.Date.valueOf(todo.getDeadline()));
            statement.setBoolean(4, todo.getDone());
            statement.setObject(5, todo.getAssigneeId());
            statement.execute();

            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            created = new Todo(
                    resultSet.getInt(1),
                    todo.getTitle(),
                    todo.getDescription(),
                    todo.getDeadline(),
                    todo.getDone(),
                    todo.getAssigneeId()
            );
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return created;
    }

    @Override
    public Collection<Todo> findAll() {

        List<Todo> foundDotos = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM todo_item");

            while(resultSet.next()){
                Todo todo = new Todo(
                        resultSet.getInt("todo_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("deadline").toLocalDate(),
                        resultSet.getBoolean("done"),
                        resultSet.getInt("assignee_id") != 0 ? resultSet.getInt("assignee_id") : null
                );
                foundDotos.add(todo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return foundDotos;
    }

    @Override
    public Todo findById(int id) {

        Todo foundDoto = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("SELECT * FROM todo_item WHERE todo_id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                foundDoto = new Todo(
                        resultSet.getInt("todo_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("deadline").toLocalDate(),
                        resultSet.getBoolean("done"),
                        resultSet.getInt("assignee_id") != 0 ? resultSet.getInt("assignee_id") : null
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return foundDoto;
    }

    @Override
    public Collection<Todo> findByDoneStatus(Boolean done) {

        List<Todo> foundDotos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("SELECT * FROM todo_item WHERE done = ?");
            statement.setBoolean(1, done);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                Todo todo = new Todo(
                        resultSet.getInt("todo_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("deadline").toLocalDate(),
                        resultSet.getBoolean("done"),
                        resultSet.getInt("assignee_id") != 0 ? resultSet.getInt("assignee_id") : null
                );
                foundDotos.add(todo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return foundDotos;
    }

    @Override
    public Collection<Todo> findByAssignee(int assigneeId) {

        List<Todo> foundDotos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("SELECT * FROM todo_item WHERE assignee_id = ?");
            statement.setInt(1, assigneeId);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                Todo todo = new Todo(
                        resultSet.getInt("todo_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("deadline").toLocalDate(),
                        resultSet.getBoolean("done"),
                        resultSet.getInt("assignee_id")
                );
                foundDotos.add(todo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return foundDotos;
    }

    @Override
    public Collection<Todo> findByAssignee(Person assignee) {

        List<Todo> foundDotos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("SELECT * FROM todo_item WHERE assignee_id = ?");
            statement.setInt(1, assignee.getId());
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                Todo todo = new Todo(
                        resultSet.getInt("todo_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("deadline").toLocalDate(),
                        resultSet.getBoolean("done"),
                        resultSet.getInt("assignee_id")
                );
                foundDotos.add(todo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return foundDotos;
    }

    @Override
    public Collection<Todo> findByUnassignedTodoItems() {

        List<Todo> foundDotos = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM todo_item WHERE assignee_id IS NULL");

            while(resultSet.next()){
                Todo todo = new Todo(
                        resultSet.getInt("todo_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("deadline").toLocalDate(),
                        resultSet.getBoolean("done"),
                        resultSet.getInt("assignee_id") != 0 ? resultSet.getInt("assignee_id") : null
                );
                foundDotos.add(todo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(resultSet, statement, connection);
        }
        return foundDotos;
    }

    @Override
    public Todo update(Todo todo) {
        if(todo == null) throw new IllegalArgumentException("Todo todo was null");
        if (findById(todo.getId()) == null) throw new IllegalArgumentException("Todo todo is not persisted");
        if (todo.getAssigneeId() != null) {
            if ((new PersonDAO()).findById(todo.getAssigneeId()) == null)
                throw new IllegalArgumentException("Assignee not persisted");
        }

        Connection connection = null;
        PreparedStatement statement = null;
        int rowsAffected = 0;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?");
            statement.setString(1, todo.getTitle());
            statement.setString(2, todo.getDescription());
            statement.setDate(3, java.sql.Date.valueOf(todo.getDeadline()));
            statement.setBoolean(4, todo.getDone());
            statement.setObject(5, todo.getAssigneeId());
            statement.setInt(6, todo.getId());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            closeAll(statement, connection);
        }
        if (rowsAffected != 1) return null;

        return todo;
    }

    @Override
    public Boolean deleteById(int id) {

        Connection connection = null;
        PreparedStatement statement = null;
        int rowsAffected = 0;

        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("DELETE FROM todo_item WHERE todo_id = ?");
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
