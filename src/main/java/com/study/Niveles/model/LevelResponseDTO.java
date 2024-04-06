package com.study.Niveles.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LevelResponseDTO {
    private Long levelId;
    private String name;
    private String imageUrl;
    private double xpToNextLevel;
}
