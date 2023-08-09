package fr.gofly.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class YesNoToBooleanAdapter extends XmlAdapter<String, String> {
    @Override
    public String unmarshal(String value) {
        return "oui".equalsIgnoreCase(value) ? "oui" : "non";
    }

    @Override
    public String marshal(String value) {
        return value;
    }
}