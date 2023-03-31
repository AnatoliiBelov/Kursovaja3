package belov.kursovaja_3_kurs.service;

import belov.kursovaja_3_kurs.model.Color;
import belov.kursovaja_3_kurs.model.Size;
import belov.kursovaja_3_kurs.model.Socks;

import java.util.HashMap;
import java.util.Map;

public interface SocksService {


    void addSocks(Socks socks, int value);

    Map<Socks, Integer> getSocksMap();

    Map<Socks, Integer> cottonMin(int cottonMinValue, Map<Socks, Integer> socksSatisfyingParameters);

    Map<Socks, Integer> cottonMax(int cottonMaxValue, Map<Socks, Integer> socksWithCottonOverMin);

    void issueOrDeleteSocks(Socks socks, int value);


    int getTheTotalNumberOfSocksByParameters(Color color, int size, Integer minValueCotton, int maxValueCotton);
}
