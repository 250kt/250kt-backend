package fr.gofly.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tafs")
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Taf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taf_id", nullable = false)
    private Integer id;

    @Column(name = "taf_airfield_code", nullable = false)
    private String airfieldCode;

    @Column(name = "taf_published_at", nullable = false)
    private LocalDateTime publishedAt;

    @Column(name = "taf_data", nullable = false)
    private String data;
}
