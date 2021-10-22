package by.epam.task6002.service;

import by.epam.task6002.service.impl.IdServiceImpl;
import by.epam.task6002.service.impl.NotebookServiceImpl;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();

    private final IdService idService;
    private final NotebookService notebookService;

    private ServiceProvider() {
        idService = new IdServiceImpl();
        notebookService = new NotebookServiceImpl();
    }

    public static ServiceProvider getInstance() {
        return instance;
    }

    public IdService getIdService() {
        return idService;
    }

    public NotebookService getNotebookService() {
        return notebookService;
    }
}
