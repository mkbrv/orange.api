package com.mkbrv.orange.identity.model;

/**
 * You can set this parameter in case you want to force the login and/or the consent page display.
 * Forcing login display can be useful if you want to re-ensure user authentication. Forcing consent display can be useful if your T&Cs have changed and you want your end-user to revalidate them.
 * Created by mkbrv on 17/02/16.
 */
public class OrangePrompt {

    public static final OrangePrompt none = new OrangePrompt("none");
    public static final OrangePrompt login = new OrangePrompt("login");
    public static final OrangePrompt consent = new OrangePrompt("consent");

    private final String value;

    public OrangePrompt(String value) {
        assert (value != null);
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrangePrompt that = (OrangePrompt) o;

        return value.equals(that.value);

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
