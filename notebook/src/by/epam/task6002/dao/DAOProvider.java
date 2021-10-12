package by.epam.task6002.dao;

import by.epam.task6002.dao.impl.FileIdDAO;
import by.epam.task6002.dao.impl.FileNotebookDAO;

public class DAOProvider {
    private final DAOProvider instance=new DAOProvider();

    private final IdDAO idDAO;
    private final NotebookDAO notebookDAO;

    private DAOProvider(){
        idDAO= new FileIdDAO();
        notebookDAO=new FileNotebookDAO();
    }

    public static DAOProvider getInstance() {
        return instance;
    }

    public IdDAO getIdDAO() {
        return idDAO;
    }

    public NotebookDAO getNotebookDAO() {
        return notebookDAO;
    }
}
