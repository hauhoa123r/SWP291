package org.project.model.dai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GeolocationDAI {
    private Double lat;
    private Double lng;
    private String address;
}
