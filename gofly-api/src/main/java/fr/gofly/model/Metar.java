package fr.gofly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "metars")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Metar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "metar_id", nullable = false)
    private Integer id;

    @Column(name = "metar_airfield_code", nullable = false)
    private String airfieldCode;

    @Column(name = "metar_published_at", nullable = false)
    private LocalDateTime publishedAt;

    @Column(name = "metar_data", nullable = false)
    private String data;
}
