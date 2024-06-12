package com.example.autocar.payload.request;


import com.example.autocar.models.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DiagnosticRequest {
    private Vehicle vehicle;
    private int idTechnicien;
    private String descProbleme;
    private String etatDiagnostic;
}
