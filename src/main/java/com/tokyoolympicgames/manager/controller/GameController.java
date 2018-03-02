package com.tokyoolympicgames.manager.controller;

import com.tokyoolympicgames.manager.entity.Game;
import com.tokyoolympicgames.manager.service.GameService;
import com.tokyoolympicgames.manager.util.ValidatorHelper;
import com.tokyoolympicgames.manager.validator.GameChainValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {

        this.gameService = gameService;
    }

    /**
     * Method to persist Games following some conditions
     *
     * @param game Json input for Game
     * @return
     */
    @PostMapping
    @ApiOperation(value = "Persist Game based on the rules.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "ACCEPT", value = "ACCEPT", defaultValue = "application/json", dataType = "string", paramType = "header") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Game.class),
        @ApiResponse(code = 400, message = "BAD_REQUEST"),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR") })
    public ResponseEntity<Object> persistGame(@RequestBody Game game) {

        List<String> errors = new ArrayList<>();

        GameChainValidator gameChainValidator = ValidatorHelper.getGameChainFullValidator(this.gameService);

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

    /**
     * Method to retrieve games based on a optional filter for modality and ordered by time.
     *
     * @param modality filter optional for modality
     * @return
     */
    @GetMapping
    @ApiOperation(value = "Get Game by id.")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = String.class),
        @ApiResponse(code = 400, message = "BAD_REQUEST"), @ApiResponse(code = 404, message = "NOT_FOUND"),
        @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR") })
    public ResponseEntity<Object> getGameTimeDesc(
        @ApiParam(name = "modality", value = "Sport Modality") @RequestParam(required = false, name = "modality")
            String modality) {

        Optional<String> modalityOpt = Optional.ofNullable(modality);

        if (modalityOpt.isPresent()) {
            return new ResponseEntity<>(this.gameService.findByModalityOrderByBeginTime(modalityOpt.get()),
                HttpStatus.OK);
        }

        return new ResponseEntity<>(this.gameService.findAllByOrderByBeginTime(), HttpStatus.OK);

    }
}
