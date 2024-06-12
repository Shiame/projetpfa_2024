package com.example.autocar.payload.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfoRequest {
    private Long idClient;

    @NotBlank
    private String NumTel;
    @NotBlank
    private String adresse;

    private int idVehicle;
}
