package com.tokyoolympicgames.manager.controller;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.entity.Modality;
import com.tokyoolympicgames.manager.service.interfaces.GameService;
import com.tokyoolympicgames.manager.validator.GameDurationValidatorImpl;
import com.tokyoolympicgames.manager.validator.GameLocalModalityTimeValidatorImpl;
import com.tokyoolympicgames.manager.validator.GameSameLocalValidatorImpl;
import com.tokyoolympicgames.manager.validator.GameStartEndTimeValidatorImpl;
import com.tokyoolympicgames.manager.validator.interfaces.GameChainValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        @ApiImplicitParam(name = "ACCEPT", value = "ACCEPT", defaultValue = "application/json", dataType = "string", paramType = "header") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Game.class),
        @ApiResponse(code = 400, message = "BAD_REQUEST"),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR") })
    public ResponseEntity<?> persistGame(@RequestBody Game game) {

        List<String> errors = new ArrayList<>();
        GameChainValidator gameChainValidator = getGameChainValidator();

        while (gameChainValidator.hasValidator()) {
            try {
                gameChainValidator.doValidate(game);
            } catch (Exception e) {
                errors.add("Invalid: " + e.getMessage());
            }
        }
        if (errors.isEmpty()) {

            return new ResponseEntity<>(this.gameService.save(game), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @ApiOperation(value = "Get Game by id.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "ACCEPT", value = "ACCEPT", defaultValue = "application/json", dataType = "string", paramType = "header") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Game.class),
        @ApiResponse(code = 400, message = "BAD_REQUEST"), @ApiResponse(code = 404, message = "NOT_FOUND"),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR") })
    public ResponseEntity<?> getGameById(@PathVariable(required = false) Optional<Modality> modalityOpt) {

        if(modalityOpt.isPresent()){
            return new ResponseEntity<>(this.gameService.findByModalityOrderByBeginTime(modalityOpt.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>(this.gameService.findAllByOrderByBeginTime(), HttpStatus.OK);

    }

    private GameChainValidator getGameChainValidator() {

        GameChainValidator gameChainValidator = new GameChainValidator();
        gameChainValidator.addValidator(new GameDurationValidatorImpl());
        gameChainValidator.addValidator(new GameStartEndTimeValidatorImpl());
        gameChainValidator.addValidator(new GameLocalModalityTimeValidatorImpl(this.gameService));
        gameChainValidator.addValidator(new GameSameLocalValidatorImpl(this.gameService));
        return gameChainValidator;
    }
}
