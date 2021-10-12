package by.epam.task6002.controller.impl;

import by.epam.task6002.controller.Controller;
import by.epam.task6002.service.NotebookService;
import by.epam.task6002.service.ServiceException;
import by.epam.task6002.service.ServiceProvider;

public class NotebookController implements Controller {

    public NotebookController(){
       ServiceProvider serviceProvider;
       NotebookService notebookService;

       serviceProvider=ServiceProvider.getInstance();
       notebookService=serviceProvider.getNotebookService();

       try {
           notebookService.readAllNotes();
       } catch(ServiceException serviceException){
           System.exit(-1);
       }
    }

    @Override
    public String doAction(String request) {
        return null;
    }
}
