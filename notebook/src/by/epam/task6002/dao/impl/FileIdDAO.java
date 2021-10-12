package by.epam.task6002.dao.impl;

import by.epam.task6002.dao.DAOException;
import by.epam.task6002.dao.IdDAO;

import java.io.*;

public class FileIdDAO implements IdDAO {
    private final String idFileName = getClass().getResource("/by/epam/task6002/resource/currentId.txt")
            .toString()
            .substring(6);

    @Override
    public long readId() throws DAOException {
        String content;
        String idString;
        long id;

        try (BufferedReader reader = new BufferedReader(new FileReader(idFileName))) {
            content = reader.readLine();
        } catch (IOException ioException) {
            throw new DAOException(ioException);
        }

        if (content.startsWith("{\"id\":\"") && content.endsWith("\"}")) {
            idString = content.substring(7, content.length() - 2);
            id = Long.parseLong(idString);
        } else {
            throw new DAOException("Incorrect file content. File: " + idFileName);
        }

        return id;
    }

    @Override
    public void writeId(long id) throws DAOException {
        String idString;

        idString = "{\"id\":\"" + id + "\"}";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(idFileName))) {
            writer.write(idString);
            writer.flush();
        } catch (IOException ioException) {
            throw new DAOException(ioException);
        }
    }
}
