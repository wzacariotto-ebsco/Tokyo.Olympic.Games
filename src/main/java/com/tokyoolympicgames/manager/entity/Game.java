package com.tokyoolympicgames.manager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tokyoolympicgames.manager.enums.Stage;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity that contains the Game information from the Olympic Game
 *
 * @author Wendler Zacariotto
 */
@Entity
public class Game implements Serializable {

    private static final long serialVersionUID = 8672238172364303639L;

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "BEGIN_TIME")
    @NotNull(message = "The Begin data time must be specified")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime beginTime;
    @Column(name = "END_TIME")
    @NotNull(message = "The End data time must be specified")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime endTime;
    @NotNull(message = "The modality must be specified")
    private String modality;
    @NotNull(message = "The first country must be specified")
    private String firstCountry;
    @NotNull(message = "The second country must be specified")
    private String secondCountry;
    @NotNull(message = "The Local must be specified")
    private String local;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "The Stage must be specified")
    @Column(name = "STAGE")
    private Stage stage;

    public Game() {

    }

    public Game(LocalDateTime beginTime, LocalDateTime endTime, String modality, String firstCountry,
                String secondCountry, String local, Stage stage) {

        this.beginTime = beginTime;
        this.endTime = endTime;
        this.modality = modality;
        this.firstCountry = firstCountry;
        this.secondCountry = secondCountry;
        this.local = local;
        this.stage = stage;
    }

    public String getFirstCountry() {

        return firstCountry;
    }

    public void setFirstCountry(String firstCountry) {

        this.firstCountry = firstCountry;
    }

    public String getSecondCountry() {

        return secondCountry;
    }

    public void setSecondCountry(String secondCountry) {

        this.secondCountry = secondCountry;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getModality() {

        return modality;
    }

    public void setModality(String modality) {

        this.modality = modality;
    }

    public LocalDateTime getBeginTime() {

        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {

        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {

        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {

        this.endTime = endTime;
    }

    public String getLocal() {

        return local;
    }

    public void setLocal(String local) {

        this.local = local;
    }

    public Stage getStage() {

        return stage;
    }

    public void setStage(Stage stage) {

        this.stage = stage;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Game)) {
            return false;
        }

        Game game = (Game) o;

        return new EqualsBuilder().append(getBeginTime(), game.getBeginTime())
                                  .append(getEndTime(), game.getEndTime())
                                  .append(getModality(), game.getModality())
                                  .append(getFirstCountry(), game.getFirstCountry())
                                  .append(getSecondCountry(), game.getSecondCountry())
                                  .append(getLocal(), game.getLocal())
                                  .append(getStage(), game.getStage())
                                  .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 37).append(getBeginTime())
                                          .append(getEndTime())
                                          .append(getModality())
                                          .append(getFirstCountry())
                                          .append(getSecondCountry())
                                          .append(getLocal())
                                          .append(getStage())
                                          .toHashCode();
    }

    @Override
    public String toString() {

        return "The " + modality + " " + stage + " game between " + firstCountry + " and " + secondCountry
            + ", will take place at " + local + " and begin at " + beginTime + " until " + endTime;
    }

}
