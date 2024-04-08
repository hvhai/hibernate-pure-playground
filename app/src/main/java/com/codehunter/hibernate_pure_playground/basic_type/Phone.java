package com.codehunter.hibernate_pure_playground.basic_type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity(name = "Phone")
public class Phone {
    @Id
    private Long id;
    @Column(name = "phone_number")
    private String number;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "phone_type")
    private PhoneType type;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "phone_color")
    private PhoneColorType color;

    public enum PhoneType {
        LAND_LINE, MOBILE, SATELLITE;
    }

    public enum PhoneColorType {
        RED, BLACK, WHITE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    public PhoneColorType getColor() {
        return color;
    }

    public void setColor(PhoneColorType color) {
        this.color = color;
    }
}
