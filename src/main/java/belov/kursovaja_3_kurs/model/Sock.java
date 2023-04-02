package belov.kursovaja_3_kurs.model;

import belov.kursovaja_3_kurs.exception.CottonPartException;
import belov.kursovaja_3_kurs.exception.NoSizeException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sock  {
    private Color color;
    private Size size;
    private int cottonPart;

    public Sock(Color color, int sizeInNumbers, int cottonPart) {
        int count = 0;
        for (Size size:
             Size.values()) {
            if (sizeInNumbers==size.getSizeOfNumbers() ) {
                this.size =size ;
            }else
            {count++;}
        }
        if (count == Size.values().length) {
            throw new NoSizeException();
        }


        this.color = color;

if (cottonPart>100||cottonPart<0)
{
    throw new CottonPartException();
}
        this.cottonPart = cottonPart;
    }
}
