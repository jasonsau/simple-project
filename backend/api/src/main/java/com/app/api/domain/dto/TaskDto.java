package com.app.api.domain.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    @NotBlank(message = "Titulo es requerido")
    private String title;
    @NotBlank(message = "Descripccion es requerido")
    private String description;
    private Boolean completed = false;
    private LocalDateTime dateFinished;
}
