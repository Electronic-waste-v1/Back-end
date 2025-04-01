package org.example.ewastev0_1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private int id;
    private int userId;
    private String message;
    private LocalDate dateEnvoi;
    private boolean estLu;
}