package com.example.autocar.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepairUpdateRequest {

    private String etatRep;
    private int coutRep;
    private int dureeRep;
}
