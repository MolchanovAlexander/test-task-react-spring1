package com.example.bookstorewebapp.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {
    @NotBlank
    @Size(min = 4, max = 255)
    private String name;
    @NotBlank
    @Size(min = 10, max = 255)
    private String description;
}
