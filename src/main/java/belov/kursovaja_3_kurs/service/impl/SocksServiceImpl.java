package belov.kursovaja_3_kurs.service.impl;

import belov.kursovaja_3_kurs.exception.NoSocksException;
import belov.kursovaja_3_kurs.model.Color;
import belov.kursovaja_3_kurs.model.Sock;
import belov.kursovaja_3_kurs.service.SocksService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service

public class SocksServiceImpl implements SocksService {
    private Map<Sock, Integer> socksMap = new HashMap<>();
    @Override
    public void addSocks(Sock sock, int value) {
        if (socksMap.containsKey(sock)) {
            int newValue = socksMap.get(sock) + value;
            socksMap.put(sock, newValue);
        } else {
            socksMap.put(sock, value);
        }
    }

    private Map<Sock, Integer> getSocksMap() {
        return socksMap;
    }


    @Override
    public void issueOrDeleteSocks(Sock sock, int value) throws NoSocksException {
        if (socksMap.containsKey(sock)) {
            int newValue = socksMap.get(sock) - value;
            if (newValue < 0) {
                throw new NoSocksException();
            }
            socksMap.put(sock, newValue);
        } else { throw new NoSocksException();}
    }
@Override
    public int getTheTotalNumberOfSocksByParameters(Color color, int size, Integer minValueCotton, int maxValueCotton) {
    int totalNumberOfSocksByParameters = 0;
        for (Sock sock :
                socksMap.keySet()) {
            if (sock.getColor().equals(color) && sock.getSize().getSizeOfNumbers()==size&& sock.getCottonPart()>=minValueCotton&&
            sock.getCottonPart()<=maxValueCotton) {
                totalNumberOfSocksByParameters = totalNumberOfSocksByParameters + socksMap.get(sock);
            }}

        return totalNumberOfSocksByParameters;


    }
}
