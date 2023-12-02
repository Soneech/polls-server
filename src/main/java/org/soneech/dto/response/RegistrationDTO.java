package org.soneech.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно содержать от 2 до 100 символов")
    private String name;

    @NotBlank(message = "Email не может быть пустым")
    @Email
    @Size(min = 6, max = 100, message = "Email должен содержать от 6 до 100 символов")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
