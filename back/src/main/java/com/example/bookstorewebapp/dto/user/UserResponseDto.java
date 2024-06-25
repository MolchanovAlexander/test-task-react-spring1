package com.example.bookstorewebapp.dto.user;

import com.example.bookstorewebapp.model.Role;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String shippingAddress;
    private Set<Role> roles = new HashSet<>();
}
