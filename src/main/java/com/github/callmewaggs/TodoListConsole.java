package com.github.callmewaggs;

import com.github.callmewaggs.domain.TodoRepository;
import com.github.callmewaggs.menu.TodoMenu;
import com.github.callmewaggs.menu.TodoMenuParameter;
import com.github.callmewaggs.processor.TodoProcessor;
import java.util.Map;

public class TodoListConsole {

  private Map<TodoMenu, TodoProcessor> todoProcessorMapping;
  private TodoRepository todoRepository;
  private IOHelper ioHelper;

  public TodoListConsole(
      Map<TodoMenu, TodoProcessor> todoProcessorMapping,
      TodoRepository todoRepository,
      IOHelper ioHelper) {
    this.todoProcessorMapping = todoProcessorMapping;
    this.todoRepository = todoRepository;
    this.ioHelper = ioHelper;
  }

  void start() {
    ioHelper.printHelloMessage();
    ioHelper.printMenuWithExample();
    //파일 읽는거,,,!
    if(ioHelper.askImporting() == true)
    	todoRepository.TodoRepositorySetting(ioHelper.importFile());//여기서 세팅하면서 id를 정비해보기? 
    
    while (true) {
      try {
        String input = ioHelper.inputCommand();
        TodoMenuParameter todoMenuParameter = TodoMenuParameter.parse(input);//id,content,menu,parentId세팅 후 객체 반환 
        TodoMenu menu = todoMenuParameter.getMenu();//메뉴 따로 저장 
        //검색결과 출력함수
        if(menu == TodoMenu.SEARCH) {
        	ioHelper.printSearchResult(todoRepository.findAll(), todoMenuParameter.getContent());
        	continue;
        }
        if (menu == TodoMenu.QUIT) {
        	if(ioHelper.askExporting() == true)
        		ioHelper.exportFile(todoRepository.findAll());
        	break;
        }
        if (menu == TodoMenu.SHOW_LIST) {
          ioHelper.printTodoList(todoRepository.findAll());
          continue;
        }
        TodoProcessor todoProcessor = todoProcessorMapping.get(menu);
        todoProcessor.run(todoMenuParameter);//각 메뉴에 알맞는 코드 실행 
        ioHelper.printTodoList(todoRepository.findAll());//현재까지 리스트 실행 
      } catch (Exception e) {
        ioHelper.printMessage(e.getMessage());
      }
    }
  }
}
