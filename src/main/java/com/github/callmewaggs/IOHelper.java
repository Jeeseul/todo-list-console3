package com.github.callmewaggs;

import com.github.callmewaggs.domain.Todo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.io.Reader;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class IOHelper {

  private Scanner scanner = new Scanner(System.in);
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public void printHelloMessage() {
    System.out.println("TODO LIST CONSOLE APPLICATION - Todo editor");
  }

  public void printMenuWithExample() {
    printMenu();
    printExample();
  }

  public void printMenu() {
    System.out.println("  MENU");
    System.out.println("  1: show TODO list");
    System.out.println("  2: create TODO");
    System.out.println("  3: update TODO");
    System.out.println("  4: remove TODO");
    System.out.println("  5: finish TODO");
    System.out.println("  6: search TODO");
    System.out.println("  0: exit\n");
  }

  public void printExample() {
    System.out.println("  EXAMPLE");
    System.out.println("  1<Enter>");
    System.out.println("  2 {content} [dependencies]<Enter>");
    System.out.println("  3 {id} {content} [dependencies]<Enter>");
    System.out.println("  4 {id}<Enter>");
    System.out.println("  5 {id}<Enter>");
    System.out.println("  6 {content to find}<Enter>");
    System.out.println("  dependencies must be written with @ (ex. @1)\n");
  }

  public String inputCommand() {
    System.out.print("$ ");
    return scanner.nextLine();
  }

  public void printTodoList(List<Todo> todoList) {
    System.out.println("| id | content | 작성일시 | 최종수정일시 | 완료처리 |");
    for (Todo todo : todoList) {
      String information =
          String.join(
              " | ",
              String.valueOf(todo.getId()),
              getContentWithDependencies(todo),
              getTimes(todo));
      System.out.println("| " + information + " |");
    }
  }
  //종료시 현재의 투두리스트 파일로 송출 
  public void exportFile(List<Todo> todoList) {
	  Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
	  String json = gson.toJson(todoList, List.class).toString();
	  Path path = Paths.get("Todo.json");
	  try {
		Files.write(path,json.getBytes());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  System.out.println(json);
  }
  //파일 가져오기 
  public List<Todo> importFile() {
	  Gson gson = new Gson();
	  Reader reader;
	try {
		reader = Files.newBufferedReader(Paths.get("Todo.json"));
		Todo[] preToDoList = gson.fromJson(reader, Todo[].class);
		List<Todo> todoList = Arrays.asList(preToDoList);
		//System.out.println(todoList);
		//System.out.println(todoList.getClass().getName());
		reader.close();
		return todoList;
	} catch (IOException e) {
		e.printStackTrace();
		return null;
	}
	
  }
  //파일 불러올건지 묻는 메소드 
  public boolean askImporting() {
	  System.out.println("이전에 저장해놨던 내용을 불러오시겠습니까?(Yes-y or Y/ No-n or N)");
	  String answer = scanner.nextLine();
	  if(answer.equals("y") || answer.equals("Y"))
		  return true;
	  else
		  return false;
  }
  
  //종료시 파일로 저장할지 안할지 
  public boolean askExporting() {
	  System.out.println("현재까지의 todo-list를 파일로 저장하시겠습니까?(Yes-y or Y/ No-n or N)");
	  String answer = scanner.nextLine();
	  if(answer.equals("y") || answer.equals("Y"))
		  return true;
	  else
		  return false;
	}
  
  //찾기 원하는 내용과 일치하는 내용이 포함된 리스트만,,,출력 
  public void printSearchResult(List<Todo> todoList, String content) {
	  int count=0;
	  for(Todo todo: todoList) {
		  //System.out.println(getContentWithDependencies(todo));
		//검색하려는 내용 있는지 확인
		  if(getContentWithDependencies(todo).contains(content)){
			  count++;
			  String information =
			          String.join(
			              " | ",
			              String.valueOf(todo.getId()),
			              getContentWithDependencies(todo),
			              getTimes(todo));
			      System.out.println("| " + information + " |");
		  }else continue;
	  }
	  if(count == 0)
		  System.out.println("일치한 내용을 찾지 못했습니다.");
  }

  private String getContentWithDependencies(Todo todo) {
    String dependencies =
        todo.getParents().stream().map(e -> "@" + e.getId()).collect(Collectors.joining(" "));
    return String.join(" ", todo.getContent(), dependencies).trim();
  }

  private String getTimes(Todo todo) {
    return String.join(
        " | ",
        todo.getCreateAt() == null ? null : todo.getCreateAt().format(formatter),
        todo.getUpdateAt() == null ? null : todo.getCreateAt().format(formatter),
        todo.getFinishAt() == null ? null : todo.getCreateAt().format(formatter));
  }

  public void printMessage(String message) {
    System.out.println(message);
  }


}
