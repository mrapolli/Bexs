package br.com.bexs.controller;

import br.com.bexs.service.RouteService;
import br.com.bexs.vo.RouteRequest;
import br.com.bexs.vo.RouteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;


@Slf4j
@RequestMapping(value = "/calc")
@RestController
@Tag(name = "BEXS", description = "Routes services ")
public class RouteController {

    @Autowired
    private RouteService routeService;


    @Operation(summary = "GetRoute", description = "Find best route")
    @ApiResponses(value = {
            @ApiResponse(description = "Return best route ", responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = RouteResponse.class)))),
    })

    @GetMapping("/route")
    public ResponseEntity route(@RequestParam String origDest) {
        return ok(routeService.getRoute(origDest));

    }

    @Operation(summary = "PutRoute", description = "Put new route")
    @ApiResponses(value = {
            @ApiResponse(description = "Put OK ", responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))),
    })
    @PostMapping("/route")
    public ResponseEntity putRoute(@Valid @RequestBody RouteRequest data) {
         return ok(routeService.persistRoute(data.getRotaOrigem(), data.getRotaDestino(), data.getCost()));

    }


}
