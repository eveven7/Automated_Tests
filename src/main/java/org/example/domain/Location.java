package org.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
@JsonIgnoreProperties(ignoreUnknown = true)

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    String name;
    String url;
}
