package by.epam.task6002.service.impl;

import by.epam.task6002.dao.DAOException;
import by.epam.task6002.dao.DAOProvider;
import by.epam.task6002.dao.IdDAO;
import by.epam.task6002.service.IdService;
import by.epam.task6002.service.ServiceException;

public class IdServiceImpl implements IdService {
    private final DAOProvider daoProvider;

    public IdServiceImpl() {
        daoProvider = DAOProvider.getInstance();
    }

    @Override
    public long getNextId() throws ServiceException {
        IdDAO idDAO;
        long currentId;
        long nextId;

        try {
            idDAO = daoProvider.getIdDAO();
            currentId = idDAO.readId();
            nextId = currentId + 1;
            idDAO.writeId(nextId);

            return nextId;
        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
