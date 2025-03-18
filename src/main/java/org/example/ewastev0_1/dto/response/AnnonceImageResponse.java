package org.example.ewastev0_1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnonceImageResponse {
    private Integer id;
    private String name;
    private String type;
    private String url;
}
