package fr.gofly.model.obstacle;

import jakarta.xml.bind.annotation.XmlEnumValue;

public enum ObstacleBeaconing {

    @XmlEnumValue("jour")
    DAY,
    @XmlEnumValue("nuit")
    NIGHT,
    @XmlEnumValue("jour et nuit")
    DAY_AND_NIGHT,
    @XmlEnumValue("non balisé")
    NOT_BEACONING,
}
