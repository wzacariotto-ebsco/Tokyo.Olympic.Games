package com.tokyoolympicgames.manager.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Entity that contains the Game information from the Olympic Game
 *
 * @author Wendler Zacariotto
 */
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Country firstCountry;
    @ManyToOne
    private Country secondCountry;
    @ManyToOne
    private Modality modality;
    @Column(name = "begin_time")
    private LocalDate beginTime;
    @Column(name = "end_time")
    private LocalDate endTime;
    @OneToOne
    private Localization local;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Country getFirstCountry() {
        return firstCountry;
    }

    public void setFirstCountry(Country firstCountry) {
        this.firstCountry = firstCountry;
    }

    public Country getSecondCountry() {
        return secondCountry;
    }

    public void setSecondCountry(Country secondCountry) {
        this.secondCountry = secondCountry;
    }

    public Modality getModality() {
        return modality;
    }

    public void setModality(Modality modality) {
        this.modality = modality;
    }

    public LocalDate getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDate beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public Localization getLocal() {
        return local;
    }

    public void setLocal(Localization local) {
        this.local = local;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        return new EqualsBuilder()
                .append(firstCountry, game.firstCountry)
                .append(secondCountry, game.secondCountry)
                .append(modality, game.modality)
                .append(beginTime, game.beginTime)
                .append(endTime, game.endTime)
                .append(local, game.local)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(firstCountry)
                .append(secondCountry)
                .append(modality)
                .append(beginTime)
                .append(endTime)
                .append(local)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "The " + modality +
                " game between " + firstCountry +
                " and " + secondCountry +
                ", will take place at " + local +
                " and begin at " + beginTime +
                " until " + endTime;
    }


}
