package com.github.callmewaggs.domain;

import java.util.ArrayList;
import java.util.List;

public class TodoRepository {

  private List<Todo> todoList;

  public TodoRepository() {
    this.todoList = new ArrayList<>();
  }
  
  public void TodoRepositorySetting(List<Todo> todoList) {
	 for(Todo todo:todoList) {
		 this.todoList.add(todo);
	 }
  }

  public void add(Todo todo) {
    todoList.add(todo);
  }

  public Todo find(long id) {
    return todoList.stream()
        .filter(todo -> todo.getId() == id)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("잘못된 id가 입력되었습니다. id를 다시 확인해 주세요."));
  }

  public List<Todo> findAll() {
    return todoList;
  }

  public List<Todo> findAll(List<Long> ids) {
    List<Todo> todos = new ArrayList<>();
    for (long id : ids) {
      Todo todo = find(id);
      todos.add(todo);
//      System.out.println("=------------------------------===");
//      System.out.println(todo);
    }
    return todos;
  }

  public List<Todo> findAllChildren(long id) {
    List<Todo> children = new ArrayList<>();
    for (Todo todo : todoList) {
      if (todo.getParents().stream().anyMatch(e -> e.getId() == id)) {
        children.add(todo);
      }
    }
    return children;
  }

  public void remove(Todo todo) {
    todoList.remove(todo);
  }

  public int size() {
    return todoList.size();
  }
}
