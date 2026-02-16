package example.tecnica.controller;

import example.tecnica.dto.searchAny.SearchAnyResponse;
import example.tecnica.error.ErrorApi;
import example.tecnica.service.SearchAnyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/search")
@Tag(name = "Búsqueda Global", description = "Búsqueda unificada en tareas y usuarios")
public class SearchAnyController {

    private final SearchAnyService searchAnyService;

    @GetMapping
    @Operation(
        summary = "Buscar en todo el sistema",
        description = "Realiza una búsqueda global por texto en tareas (título, descripción) y usuarios (username, nombre). Ejemplo: ?q=proyecto"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente",
            content = @Content(schema = @Schema(implementation = SearchAnyResponse.class))),
        @ApiResponse(responseCode = "400", description = "Parámetro de búsqueda inválido o vacío",
            content = @Content(schema = @Schema(implementation = ErrorApi.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorApi.class)))
    })
    public ResponseEntity<List<SearchAnyResponse>> searchAny(
            @Parameter(description = "Término de búsqueda", example = "proyecto", required = true)
            @RequestParam String q) {
        return new ResponseEntity<>(searchAnyService.searchAny(q), HttpStatus.OK);
    }

}
