package fr.gofly.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

/**
 * An XML adapter that converts between "oui" and "non" strings and retains their original form.
 * This adapter is used for mapping boolean values represented as "oui" or "non" in XML.
 */
public class YesNoToBooleanAdapter extends XmlAdapter<String, String> {
    /**
     * Converts a "oui" or "non" string to its equivalent representation.
     *
     * @param value The input "oui" or "non" string.
     * @return The equivalent string representation ("oui" or "non").
     */
    @Override
    public String unmarshal(String value) {
        return "oui".equalsIgnoreCase(value) ? "oui" : "non";
    }

    /**
     * Converts a string to its original form (no change is applied).
     *
     * @param value The input string.
     * @return The same input string.
     */
    @Override
    public String marshal(String value) {
        return value;
    }
}