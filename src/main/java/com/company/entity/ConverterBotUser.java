package com.company.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConverterBotUser {
    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String language;
    private String status;
    private Boolean admin;
    private Boolean active;
}
