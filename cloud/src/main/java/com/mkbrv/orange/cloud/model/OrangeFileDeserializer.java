package com.mkbrv.orange.cloud.model;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by mikibrv on 26/02/16.
 */
public class OrangeFileDeserializer implements JsonDeserializer<OrangeFile> {

    private final Logger LOG = LoggerFactory.getLogger(OrangeFileDeserializer.class);

    public static class Params {
        public static final String NAME = "name";
        public static final String ID = "id";
        public static final String LAST_UPDATED_DATE = "lastUpdateDate";
        public static final String CREATION_DATE = "creationDate";
        public static final String METADATA = "metadata";
        public static final String HEIGHT = "height";
        public static final String WIDTH = "width";
        public static final String SHOOTING_DATE = "shootingDate";
        public static final String TYPE = "type";
        public static final String SIZE = "size";


    }

    @Override
    public OrangeFile deserialize(final JsonElement jsonElement,
                                  final Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        OrangeFile orangeFile = new OrangeFile();
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        orangeFile.setId(jsonObject.get(Params.ID).getAsString());
        orangeFile.setName(jsonObject.get(Params.NAME).getAsString());
        orangeFile.setSize(jsonObject.get(Params.SIZE).getAsLong());

        orangeFile.setLastUpdateDate(this.parseDate(jsonObject.get(Params.LAST_UPDATED_DATE),
                jsonDeserializationContext));
        orangeFile.setCreationDate(this.parseDate(jsonObject.get(Params.CREATION_DATE),
                jsonDeserializationContext));
        orangeFile.setType(this.getOrangeFileType(jsonObject.get(Params.TYPE).getAsString()));
        orangeFile.setMetadata(this.getMetaData(jsonObject, jsonDeserializationContext));

        return orangeFile;
    }

    private OrangeFileType getOrangeFileType(final String type) {
        return OrangeFileType.fromString(type);
    }

    /**
     * @param jsonObject
     * @return
     */
    private OrangeFileMetadata getMetaData(final JsonObject jsonObject,
                                           final JsonDeserializationContext jsonDeserializationContext) {
        OrangeFileMetadata orangeFileMetadata = new OrangeFileMetadata();
        JsonObject metadataJson = jsonObject.get(Params.METADATA).getAsJsonObject();
        orangeFileMetadata.setHeight(metadataJson.get(Params.HEIGHT).getAsInt())
                .setWidth(metadataJson.get(Params.WIDTH).getAsInt())
                .setShootingDate(this.parseDate(metadataJson.get(Params.SHOOTING_DATE), jsonDeserializationContext));
        return orangeFileMetadata;
    }


    /**
     * @param date
     * @return
     */
    private Date parseDate(final JsonElement date, final JsonDeserializationContext jsonDeserializationContext) {
        return jsonDeserializationContext.deserialize(date, Date.class);
    }
}
