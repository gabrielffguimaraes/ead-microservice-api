package com.ead.authuser.filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserFilter {
    private String fullName;
    private String email;
    private String cpf;

    public boolean isEmpty() {
        return fullName == null && email == null && cpf == null;
    }
}
