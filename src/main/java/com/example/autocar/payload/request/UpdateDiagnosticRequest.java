package com.example.autocar.payload.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDiagnosticRequest {
    private int idTechnicien;
    private String descProbleme;
    private String etatDiagnostic;
    private Long estimatedPrice;
}
