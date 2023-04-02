package belov.kursovaja_3_kurs.controller;

import belov.kursovaja_3_kurs.exception.CottonPartException;
import belov.kursovaja_3_kurs.exception.NoSizeException;
import belov.kursovaja_3_kurs.exception.NoSocksException;
import belov.kursovaja_3_kurs.model.Color;
import belov.kursovaja_3_kurs.model.Sock;
import belov.kursovaja_3_kurs.service.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socks")
@Tag(name="Склад носков", description = "CRUD-операции и другие эндпоинты")
public class SocksController {

    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping
    @Operation(
            summary = "Регистрирует приход товара(носков) на склад.",
            description = "Приход товара обязательно оформлять согласно схеме"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "удалось добавить приход"
    ),
            @ApiResponse(
                    responseCode = "400",
                    description = "параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "произошла ошибка, не зависящая от вызывающей стороны"
            ),
    })
    public ResponseEntity<String> addSocks(@RequestParam Color color, @RequestParam int size, @RequestParam int cottonPart, @RequestParam int quantity) {

        try {
            Sock sock = new Sock(color, size, cottonPart);
            socksService.addSocks(sock, quantity);
        } catch (NoSizeException|CottonPartException e) {
            return ResponseEntity.badRequest().body("параметры запроса отсутствуют или имеют некорректный формат");
        }

        return ResponseEntity.ok("удалось добавить приход");
    }

    @PutMapping
    @Operation(
            summary = "Регистрирует отпуск товара(носков) co склада.",
            description = "Приход товара обязательно оформлять согласно схеме"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "удалось произвести отпуск носков со склада"
    ),
            @ApiResponse(
                    responseCode = "400",
                    description = "товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "произошла ошибка, не зависящая от вызывающей стороны"
            ),
    })
    public ResponseEntity<String> issueSocks (@RequestParam Color color, @RequestParam int size, @RequestParam int cottonPart, @RequestParam int value) {
        try {Sock sock = new Sock(color, size, cottonPart);
            socksService.issueOrDeleteSocks(sock, value);
        } catch (NoSocksException | NoSizeException |CottonPartException e) {
            return ResponseEntity.badRequest().body
                    ("товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат");
        }
        return ResponseEntity.ok("удалось произвести отпуск носков со склада");
        }


    @DeleteMapping
    @Operation(
            summary = "Регистрирует списание испорченных (бракованных) носков",
            description = "Удаление товара обязательно оформлять согласно схеме"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200",
            description = "запрос выполнен, товар списан со склада"
    ),

            @ApiResponse(
                    responseCode = "400 ",
                    description = "параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500  ",
                    description = "произошла ошибка, не зависящая от вызывающей стороны"
            )
    }
    )
    public ResponseEntity<String> deleteSocks (@RequestParam Color color, @RequestParam int size, @RequestParam int cottonPart, @RequestParam int value) {

        try {
            Sock sock = new Sock(color, size, cottonPart);
            socksService.issueOrDeleteSocks(sock, value);
        } catch (NoSocksException | NoSizeException | CottonPartException e) {
            return ResponseEntity.badRequest().body
                    ("параметры запроса отсутствуют или имеют некорректный формат");
        }
        return ResponseEntity.ok("запрос выполнен, товар списан со склада");
    }

    @GetMapping
    @Operation(
            summary = "Возвращает общее количество носков на складе, соответствующих переданным в параметрах критериям запроса"
    )
    @ApiResponses(value = {@ApiResponse(
            responseCode = "200 ",
            description = "запрос выполнен, результат в теле ответа в виде строкового представления целого числа"
    ),
            @ApiResponse(
                    responseCode = "400",
                    description = "параметры запроса отсутствуют или имеют некорректный формат"

            ),
            @ApiResponse(
                    responseCode = "500 ",
                    description = "произошла ошибка, не зависящая от вызывающей стороны"

            )

    }
    )
    public ResponseEntity<?> getInfoAboutSocks(@RequestParam Color color,@RequestParam int size, @RequestParam int cottonMin, @RequestParam int cottonMax) {
        if (cottonMin<0||cottonMax>100) {
            return ResponseEntity.badRequest().body("параметры запроса отсутствуют или имеют некорректный формат");
        }
        return ResponseEntity.ok(socksService.getTheTotalNumberOfSocksByParameters(color, size, cottonMin, cottonMax));

    }
}
