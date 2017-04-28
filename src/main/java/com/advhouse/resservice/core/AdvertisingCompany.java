package com.advhouse.resservice.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Сущность рекламной кампании, по сути это контейнер (пакет) для
 * самих рекламок
 *
 * @author proweber1
 */
@Entity
@Table(name = "advertising_companies")
public class AdvertisingCompany {

    /**
     * Primary key в этой сущности
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя рекламной компании, это не уникальное поле
     */
    @NotEmpty
    @Length(min = 3)
    private String name;

    /**
     * Описание рекламной кампании, может быть пустым, не
     * обязательное поле
     */
    private String description;

    /**
     * Владелец группы, обязательное поле, это пользователь
     * которому принадлежит это рекламная кампания, он может
     * меняться, так как есть функционал передачи кампаний
     * между пользователями
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User owner;

    public AdvertisingCompany() {
        // Java Bean
    }

    /**
     * @return Primary key
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id Primary key
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Имя рекламной кампании
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Имя рекламной кампании
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Описание рекламной кампании
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description Описание рекламной кампании
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Владелец рекламной кампании
     */
    public User getOwner() {
        return owner;
    }

    /**
     * @param owner Владелец рекламной кампании
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }
}
