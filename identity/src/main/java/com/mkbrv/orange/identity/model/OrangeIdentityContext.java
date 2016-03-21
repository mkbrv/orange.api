package com.mkbrv.orange.identity.model;

import com.mkbrv.orange.httpclient.OrangeContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mkbrv on 17/02/16.
 */
public class OrangeIdentityContext extends OrangeContext implements Serializable {

    private static final long serialVersionUID = 42L;

    /**
     * Orange scopes requested from the user
     */
    private final List<OrangeScope> orangeScopeList = new ArrayList<>();
    /**
     * Orange prompts
     */
    private final List<OrangePrompt> promptList = new ArrayList<>();
    /**
     * State variable that can be sent to orange and will be received back
     */
    private final String state;

    public OrangeIdentityContext() {
        this.state = null;
    }

    /**
     * State sent to orange, can be null;
     *
     * @param state
     */
    public OrangeIdentityContext(final String state) {
        this.state = state;
    }

    public List<OrangeScope> getOrangeScopeList() {
        return Collections.unmodifiableList(orangeScopeList);
    }

    public OrangeIdentityContext addScope(final OrangeScope orangeScope) {
        this.orangeScopeList.add(orangeScope);
        return this;
    }

    public String getState() {
        return state;
    }

    public List<OrangePrompt> getPromptList() {
        return Collections.unmodifiableList(promptList);
    }

    public OrangeIdentityContext addPrompt(final OrangePrompt orangePrompt) {
        this.promptList.add(orangePrompt);
        return this;
    }

}
