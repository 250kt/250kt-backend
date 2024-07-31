package fr.gofly.model;

public enum PilotAvatar {
    PILOT_MAN("pilot_man"),
    PILOT_WOMAN("pilot_woman");

    final String avatar;

    PilotAvatar(String avatar){
        this.avatar = avatar;
    }

    public String getAvatar(){
        return avatar;
    }
}
