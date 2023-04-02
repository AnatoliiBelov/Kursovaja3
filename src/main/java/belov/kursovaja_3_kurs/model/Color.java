package belov.kursovaja_3_kurs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Color {
    Black("Черный"),
    White("Белый"),
    Brown("Коричневый"),
    Grey("Серый"),
    Pink("Розовый"),
    Blue("Синий"),
    Yellow("Жёлтый"),
    Red("Красный");



    private final String colorForRussian;

}
