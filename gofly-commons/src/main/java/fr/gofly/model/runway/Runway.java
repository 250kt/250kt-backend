package fr.gofly.model.runway;

import fr.gofly.model.airfield.Airfield;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="runways")
@RequiredArgsConstructor
@XmlRootElement(name = "Rwy")
@XmlAccessorType(XmlAccessType.PROPERTY)
@Getter
@AllArgsConstructor
public class Runway {

    @Id
    @Column(name = "runway_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "airfield_id")
    @XmlElement(name = "Ad")
    private Airfield airfield;

    @Column(name = "runway_type")
    @Enumerated(EnumType.STRING)
    private RunwayType type;

    @Column(name = "runway_main")
    private boolean main;

    @XmlElement(name = "Revetement")
    @Transient
    private String revetment;

    @Column(name = "runway_orientation")
    @XmlElement(name = "OrientationGeo")
    private Float orientation;

    public void setType(RunwayType type) {
        this.type = type;
    }

    @XmlElement(name = "Principale")
    public String getMainString(){
        return main ? "oui" : "non";
    }

    public void setMainString(String value){
        main = "oui".equalsIgnoreCase(value);
    }
}
