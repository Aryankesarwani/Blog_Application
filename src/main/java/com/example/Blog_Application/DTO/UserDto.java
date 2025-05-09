package com.example.Blog_Application.DTO;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    int id; 
    @NotEmpty(message = "Name can not be null")
    @Size(min = 4, message ="name must be minimum of 4 characters" )
    String name;
    @Email(message = "Email is not Valid!!")
    @NotBlank(message = "Email can not be Blank")
    String email;
    @NotNull(message = "Password can't be null")
    @Size(min = 8, message = " Password must be minimum of 8 characters!!")

    String password;
    String about;
}
