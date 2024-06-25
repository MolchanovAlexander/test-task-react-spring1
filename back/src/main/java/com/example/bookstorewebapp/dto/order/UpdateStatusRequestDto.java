package com.example.bookstorewebapp.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateStatusRequestDto {
    @NotBlank
    @Size(min = 4, max = 24)
    private String status;
}
