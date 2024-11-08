package br.org.fiergs.cosmos.api.v1.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthorCreateDto {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AuthorCreateDto [name=" + name + "]";
    }

}
