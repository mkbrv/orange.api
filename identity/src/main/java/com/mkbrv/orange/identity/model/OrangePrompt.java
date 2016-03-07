package com.mkbrv.orange.identity.model;

/**
 * You can set this parameter in case you want to force the login and/or the consent page display.
 * Forcing login display can be useful if you want to re-ensure user authentication. Forcing consent display can be useful if your T&Cs have changed and you want your end-user to revalidate them.
 * Created by mkbrv on 17/02/16.
 */
public enum OrangePrompt {

    none,
    login,
    consent

}
