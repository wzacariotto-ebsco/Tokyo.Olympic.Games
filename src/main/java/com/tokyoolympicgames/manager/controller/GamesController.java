package com.tokyoolympicgames.manager.controller;

import com.tokyoolympicgames.manager.entity.Country;
import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.service.interfaces.GameService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * Rest controller for managing Game
 *
 * @author Wendler Zacariotto
 */
@RestController
@RequestMapping("/games")
@Api("v1 - Olympic Game")
public class GamesController {

    private GameService gameService;

    @Autowired
    public GamesController(GameService gameService) {

        this.gameService = gameService;
    }

    @PostMapping
    @ApiOperation(value = "Persist Game based on the rules.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ACCEPT", value = "ACCEPT", defaultValue = "application/json", required = false, dataType = "string", paramType = "header")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Package.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST"), @ApiResponse(code = 404, message = "NOT_FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR")})
    public ResponseEntity<?> persistGame() {
        Game game = new Game();
        game.setBeginTime(LocalDate.now());
        return new ResponseEntity<>(this.gameService.save(game), HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "Get Game by id.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ACCEPT", value = "ACCEPT", defaultValue = "application/json", required = false, dataType = "string", paramType = "header")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Package.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST"), @ApiResponse(code = 404, message = "NOT_FOUND"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR")})
    public ResponseEntity<?> getGameById(String gameId) {
        return new ResponseEntity<>(this.gameService.findById(gameId), HttpStatus.OK);
    }
}
