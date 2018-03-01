package com.tokyoolympicgames.manager.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

/**
 * Entity that contains the Localization of the Game in the Olympic Game
 *
 * @author Wendler Zacariotto
 */
@Entity
public class Localization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Localization country = (Localization) o;

        return new EqualsBuilder()
                .append(name, country.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
