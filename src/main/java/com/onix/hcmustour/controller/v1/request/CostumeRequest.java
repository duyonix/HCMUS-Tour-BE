package com.onix.hcmustour.controller.v1.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CostumeRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotBlank(message = "Picture is required")
    private String picture;

    @NotBlank(message = "Model is required")
    private String model;

    @NotNull(message = "Scope ID is required")
    private Integer scopeId;
}
