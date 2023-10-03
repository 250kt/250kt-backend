package fr.gofly.model;

public enum Role {
    ADMIN(0),
    BUDDING_PILOT(100),
    EXPERIENCED_PILOT(200),
    ACE_OF_THE_SKIES(300);

    int order;

    Role(int order){
        this.order = order;
    }
}
