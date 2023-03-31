package belov.kursovaja_3_kurs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor

public enum Size {
    S(23),
    M(25),
    L(27),
    XL(29),
    XXL(31),
    XXXL(33);


    private int sizeOfNumbers;

    public int getSizeOfNumbers() {
        return sizeOfNumbers;
    }
}

