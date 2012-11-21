package com.dc.web.controller;

import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class RequestCommand {
    private String actionKey;
    private String sidKey;
    private Collection<String> paramKeys;

    public String getActionKey() {
        return actionKey;
    }

    public void setActionKey(String actionKey) {
        this.actionKey = actionKey;
    }

    public Collection<String> getParamKeys() {
        return paramKeys;
    }

    public void setParamKeys(Collection<String> paramKeys) {
        this.paramKeys = paramKeys;
    }

    public RequestCommand() {
        super();
    }

    public RequestCommand(JSONObject json) {
        super();
        actionKey = json.getString("action");
        if (json.get("sid") != null) {
            sidKey = json.getString("sid");
        }
        if (json.get("param") != null) {
            JSONArray jsonArray = json.getJSONArray("param");
            paramKeys = JSONArray.toCollection(jsonArray);
        }
    }

    public static void main(String[] args) throws JSONException {
        String s = "{\"action\":\"Login\",\"param\":[\"Username\",\"Password\",\"MacAddr\"]}";
        JSONObject json = JSONObject.fromObject(s);

        RequestCommand c = new RequestCommand(json);
        System.out.println(ReflectionToStringBuilder.toString(c));
    }
}
