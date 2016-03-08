package com.mkbrv.orange.identity.model;

/**
 * The API scope your application needs. It tells the Authorization API what kind of permissions to asks for when displaying the consent form to the end-user.
 * Created by mkbrv on 17/02/16.
 */
public class OrangeScope {

    /**
     *
     */
    public static final OrangeScope offline_access = new OrangeScope("offline_access");
    /**
     * Requests an ID token and retrieves User identifier for SSO.
     */
    public static final OrangeScope openid = new OrangeScope("openid");
    /**
     * Grants read and write access to user's Cloud. Access is limited to the application's folder.
     */
    public static final OrangeScope profile = new OrangeScope("profile");
    /**
     * Grants read access to user's Cloud. Access is extended to the whole Cloud.
     */
    public static final OrangeScope cloudfullread = new OrangeScope("cloudfullread");
    /**
     * Grants write access to user's Cloud. This privilege must be used with cloudfullread to be active. Access is extended to the whole Cloud. This scope is restricted. To get further information contact Orange.
     */
    public static final OrangeScope cloudfullwrite = new OrangeScope("cloudfullwrite");

    private String value;

    public OrangeScope(final String value) {
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

        OrangeScope that = (OrangeScope) o;

        return value.equals(that.value);

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
