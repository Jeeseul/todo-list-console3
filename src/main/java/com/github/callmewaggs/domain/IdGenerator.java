package com.github.callmewaggs.domain;

public class IdGenerator {

  private long id;
  private TodoRepository todoRepository = new TodoRepository();

  public IdGenerator() {
    this.id = todoRepository.size()+1;
  }

  public long generate() {
    return id++;
  }
}
