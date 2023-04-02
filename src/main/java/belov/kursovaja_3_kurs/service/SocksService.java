package belov.kursovaja_3_kurs.service;

import belov.kursovaja_3_kurs.exception.NoSocksException;
import belov.kursovaja_3_kurs.model.Color;
import belov.kursovaja_3_kurs.model.Sock;

public interface SocksService {


    void addSocks(Sock sock, int value);


    void issueOrDeleteSocks(Sock sock, int value) throws NoSocksException;


    int getTheTotalNumberOfSocksByParameters(Color color, int size, Integer minValueCotton, int maxValueCotton);
}
