package com.github.callmewaggs.menu;

import java.util.Arrays;
import java.util.Optional;

public enum TodoMenu {
  QUIT("0"),

  SHOW_LIST("1"),

  CREATE("2"),

  UPDATE("3"),

  REMOVE("4"),

  FINISH("5"),
	
  SEARCH("6");

  private String menuNumber;

  TodoMenu(String menuNumber) {
    this.menuNumber = menuNumber;
  }

  public static TodoMenu fromMenuNumber(String menuNumber) {
    Optional<TodoMenu> todoMenu =
        Arrays.stream(TodoMenu.values()).filter(e -> e.menuNumber.equals(menuNumber)).findAny();
    if (!todoMenu.isPresent()) {
      throw new IllegalArgumentException(menuNumber + " is wrong menu. try again.");
    }
    return todoMenu.get();
  }
}
