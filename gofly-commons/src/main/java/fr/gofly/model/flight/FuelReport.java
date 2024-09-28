package fr.gofly.model.flight;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Table(name = "fuel_reports")
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FuelReport {

    @Id
    @Column(name = "fuel_report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fuel_report_fuel_on_board")
    private Double fuelOnBoard;

    @Column(name = "fuel_report_fuel_security_ten_percent")
    private Double fuelSecurityTenPercent;

    @Column(name = "fuel_report_fuel_reserve")
    private Double fuelReserve;

    @Column(name = "fuel_report_fuel_needed")
    private Double fuelNeeded;

    @OneToOne
    @JoinColumn(name = "fuel_report_flight_id")
    private Flight flight;
}
