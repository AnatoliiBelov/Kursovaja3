package belov.kursovaja_3_kurs.controllers;

import belov.kursovaja_3_kurs.model.Color;
import belov.kursovaja_3_kurs.model.Socks;
import belov.kursovaja_3_kurs.service.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socks")
@Tag(name="Склад носков", description = "CRUD-операции и другие эндпоинты")
public class SocksControllers {

    private final SocksService socksService;

    public SocksControllers(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping("/add")
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
    public ResponseEntity<String> addSocks(@RequestBody Color color, int size, int cottonPart, int quantity) {
        if (ObjectUtils.isEmpty(color)||ObjectUtils.isEmpty(size)||ObjectUtils.isEmpty(cottonPart)||ObjectUtils.isEmpty(quantity)||cottonPart<0||cottonPart>100) {
            return ResponseEntity.badRequest().body("параметры запроса отсутствуют или имеют некорректный формат");
        }
        Socks socks = new Socks(color, size, cottonPart);
        socksService.addSocks(socks, quantity);
        return ResponseEntity.ok("удалось добавить приход");
    }

    @PutMapping ("/add")
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
    public ResponseEntity<String> issueSocks (@RequestBody Color color, int size, int cottonPart, int value) {
        Socks socks = new Socks(color, size, cottonPart);
            for (Socks socks1 :
                    socksService.getSocksMap().keySet()) {
                if (socks1.equals(socks) && socksService.getSocksMap().get(socks1) < value) {
                    return ResponseEntity.badRequest().body("товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат");
                }
            }

            if (ObjectUtils.isEmpty(socksService.getSocksMap())
                    || !socksService.getSocksMap().containsKey(socks) || ObjectUtils.isEmpty(socks)) {
                return ResponseEntity.badRequest().body("товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат");
            }
            socksService.issueOrDeleteSocks(socks, value);
            return ResponseEntity.ok("удалось произвести отпуск носков со склада");
        }


    @DeleteMapping("/delete")
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
    public ResponseEntity<String> deleteSocks (@RequestBody Color color, int size, int cottonPart, int value) {
        Socks socks = new Socks(color, size, cottonPart);
        for (Socks socks1 :
                socksService.getSocksMap().keySet()) {
            if (socks1.equals(socks) && socksService.getSocksMap().get(socks1) < value) {
                return ResponseEntity.badRequest().body("параметры запроса отсутствуют или имеют некорректный формат");
            }
        }

        if (ObjectUtils.isEmpty(socksService.getSocksMap())
                || !socksService.getSocksMap().containsKey(socks) || ObjectUtils.isEmpty(socks)||cottonPart<0||cottonPart>100) {
            return ResponseEntity.badRequest().body("параметры запроса отсутствуют или имеют некорректный формат");
        }
        socksService.issueOrDeleteSocks(socks, value);
        return ResponseEntity.ok("запрос выполнен, товар списан со склада");
    }

    @GetMapping("/getInfo")
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
    public ResponseEntity<?> getInfoAboutSocks(@RequestParam Color color, int size, int cottonMin, int cottonMax) {
        if (ObjectUtils.isEmpty(socksService.getSocksMap())||cottonMin<0||cottonMax>100
                ||ObjectUtils.isEmpty(color)||ObjectUtils.isEmpty(size)) {
            return ResponseEntity.ok(0);
        }
        return ResponseEntity.ok(socksService.getTheTotalNumberOfSocksByParameters(color, size, cottonMin, cottonMax));

    }
}
