package belov.kursovaja_3_kurs.service.impl;

import belov.kursovaja_3_kurs.model.Color;
import belov.kursovaja_3_kurs.model.Socks;
import belov.kursovaja_3_kurs.service.SocksService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service

public class SocksServiceImpl implements SocksService {
    private Map<Socks, Integer> socksMap = new HashMap<>();
    @Override
    public void addSocks(Socks socks, int value) {
        if (socksMap.containsKey(socks)) {
            int newValue = socksMap.get(socks) + value;
            socksMap.put(socks, newValue);
        } else {
            socksMap.put(socks, value);
        }
    }
@Override
    public Map<Socks, Integer> getSocksMap() {
        return socksMap;
    }

    @Override
    public Map<Socks, Integer> cottonMin(int cottonMinValue,  Map<Socks, Integer> socksSatisfyingParameters) {
        Map<Socks, Integer> socksWithCottonOverMin = new HashMap<>();
        for (Socks socks :
                socksSatisfyingParameters.keySet()) {
            if (socks.getCottonPart() >= cottonMinValue) {
                socksWithCottonOverMin.put(socks, socksSatisfyingParameters.get(socks));
            }
        }
        return socksWithCottonOverMin;

    }
    @Override
    public Map<Socks, Integer> cottonMax(int cottonMaxValue, Map<Socks, Integer> socksWithCottonOverMin) {
        Map<Socks, Integer> socksWithCottonLessMax = new HashMap<>();
        for (Socks socks :
                socksWithCottonOverMin.keySet()) {
            if (socks.getCottonPart() <= cottonMaxValue) {
                socksWithCottonLessMax.put(socks, socksWithCottonOverMin.get(socks));
            }
        }
        return socksWithCottonLessMax;

    }
    @Override
    public void issueOrDeleteSocks(Socks socks, int value) {
        if (socksMap.containsKey(socks)) {
            int newValue = socksMap.get(socks) - value;
            socksMap.put(socks, newValue);
        } else {
            socksMap.put(socks, value);
        }
    }
@Override
    public int getTheTotalNumberOfSocksByParameters(Color color, int size, Integer minValueCotton, int maxValueCotton) {
        Map<Socks, Integer> socks1 = new HashMap<>();
        for (Socks socks:
                socksMap.keySet()) {
            if (socks.getColor().equals(color) && socks.getSize().getSizeOfNumbers()==size) {
                socks1.put(socks, socksMap.get(socks));
            }}
            int totalNumberOfSocksByParameters = 0;
            for (Integer value : cottonMax(maxValueCotton, cottonMin(minValueCotton, socks1)).values()) {
                totalNumberOfSocksByParameters = totalNumberOfSocksByParameters + value;
            }
        return totalNumberOfSocksByParameters;


    }
}
