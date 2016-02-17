package com.mkbrv.orange.identity;

import com.mkbrv.orange.client.OrangeContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikibrv on 17/02/16.
 */
public class OrangeIdentityContext {

    private OrangeContext orangeContext;

    private List<OrangeScope> orangeScopeList = new ArrayList<>();

    private String state;

    private OrangePrompt prompt;

    public OrangeIdentityContext(final OrangeContext orangeContext) {
        this.orangeContext = orangeContext;
    }

    public OrangeContext getOrangeContext() {
        return orangeContext;
    }

    public OrangeIdentityContext setOrangeContext(OrangeContext orangeContext) {
        this.orangeContext = orangeContext;
        return this;
    }

    public List<OrangeScope> getOrangeScopeList() {
        return orangeScopeList;
    }

    public OrangeIdentityContext addScope(final OrangeScope orangeScope) {
        this.orangeScopeList.add(orangeScope);
        return this;
    }

    public String getState() {
        return state;
    }

    public OrangeIdentityContext setState(final String state) {
        this.state = state;
        return this;
    }

    public OrangePrompt getPrompt() {
        if (this.prompt == null) {
            prompt = OrangePrompt.none;
        }
        return prompt;
    }

    public OrangeIdentityContext setPrompt(final OrangePrompt prompt) {
        this.prompt = prompt;
        return this;
    }

}
