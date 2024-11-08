package br.org.fiergs.cosmos.api.v1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AuthorUpdateDto {

    @NotBlank
    private String name;

    @NotNull
    private Long version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "AuthorCreateDto [name=" + name + ", version=" + version + "]";
    }

}
