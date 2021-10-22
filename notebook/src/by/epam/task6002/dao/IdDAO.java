package by.epam.task6002.dao;

public interface IdDAO {
    long readId() throws DAOException;

    void writeId(long id) throws DAOException;
}
