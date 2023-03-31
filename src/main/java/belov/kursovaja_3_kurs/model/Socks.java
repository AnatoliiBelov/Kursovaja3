package belov.kursovaja_3_kurs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Socks {
    private Color color;
    private Size size;
    private int cottonPart;

    public Socks(Color color, int sizeInNumbers, int cottonPart) {
        for (Size size:
             Size.values()) {
            if (sizeInNumbers==size.getSizeOfNumbers() ) {
                this.size =size ;
            }
        }
        this.color = color;


        this.cottonPart = cottonPart;
    }
}
