package com.mkbrv.orange.identity.model;

import com.google.gson.*;
import com.mkbrv.orange.client.OrangeJsonParser;
import com.mkbrv.orange.client.security.OrangeAccessToken;
import com.mkbrv.orange.client.security.OrangeRefreshToken;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by mikibrv on 19/02/16.
 */
public class OrangeAccessTokenDeserializer implements
        JsonDeserializer<OrangeAccessToken> {

    final GsonBuilder builder = new GsonBuilder();

    public static class Params {
        public static final String TOKEN_TYPE = "token_type";
        public static final String ACCESS_TOKEN = "access_token";
        public static final String EXPIRES_IN = "expires_in";
        public static final String REFRESH_TOKEN = "refresh_token";

    }


    @Override
    public OrangeAccessToken deserialize(JsonElement jsonElement,
                                         Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        OrangeAccessToken orangeAccessToken = new OrangeAccessToken(jsonObject.get(Params.ACCESS_TOKEN).getAsString())
                .setTokenType(jsonObject.get(Params.TOKEN_TYPE).getAsString());

        OrangeRefreshToken orangeRefreshToken = new OrangeRefreshToken(jsonObject.get(Params.REFRESH_TOKEN).getAsString())
                .setCreatedDate(new Date());
        orangeAccessToken.setRefreshToken(orangeRefreshToken);
        orangeAccessToken.setExpirationTime(this.computeExpirationTime(jsonObject.get(Params.EXPIRES_IN).getAsLong()));
        return orangeAccessToken;
    }

    /**
     * Creates a new date object with the expiration time;
     *
     * @param providedExpiresIn seconds
     * @return Date of expiration
     */
    private Date computeExpirationTime(long providedExpiresIn) {
        return new Date(System.currentTimeMillis() + providedExpiresIn * 1000L);
    }

}


