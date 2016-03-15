package com.mkbrv.orange.cloud.model.file;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mkbrv.orange.cloud.model.OrangeFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Deserializer for an Orange File;
 * Created by mkbrv on 26/02/16.
 */
public class OrangeFileDeserializer implements JsonDeserializer<OrangeFile> {

    private final Logger LOG = LoggerFactory.getLogger(OrangeFileDeserializer.class);

    public static class Params {
        public static final String NAME = "name";
        public static final String ID = "id";
        public static final String LAST_UPDATED_DATE = "lastUpdateDate";
        public static final String CREATION_DATE = "creationDate";
        public static final String METADATA = "metadata";
        public static final String TYPE = "type";
        public static final String SIZE = "size";
        public static final String THUMB_URL = "thumbUrl";
        public static final String PREVIEW_URL = "previewUrl";
        public static final String DOWNLOAD_URL = "downloadUrl";
    }

    @Override
    public OrangeFile deserialize(final JsonElement jsonElement,
                                  final Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        DefaultOrangeFile orangeFile = new DefaultOrangeFile(jsonObject.get(Params.ID).getAsString());
        orangeFile.setName(jsonObject.get(Params.NAME).getAsString());
        orangeFile.setSize(jsonObject.get(Params.SIZE).getAsLong());

        orangeFile.setLastUpdateDate(this.parseDate(jsonObject.get(Params.LAST_UPDATED_DATE),
                jsonDeserializationContext));
        orangeFile.setCreationDate(this.parseDate(jsonObject.get(Params.CREATION_DATE),
                jsonDeserializationContext));
        orangeFile.setType(this.getOrangeFileType(jsonObject.get(Params.TYPE).getAsString()));
        orangeFile.setMetadata(this.getMetaData(jsonObject));
        orangeFile.setDownloadUrl(this.notNull(Params.DOWNLOAD_URL, jsonObject));

        this.addThumbnailAndPreviewIfAvailable(orangeFile, jsonObject);
        return orangeFile;
    }

    /**
     * @param orangeFile
     * @param jsonObject
     */
    private void addThumbnailAndPreviewIfAvailable(final DefaultOrangeFile orangeFile, final JsonObject jsonObject) {
        orangeFile.setThumbUrl(
                notNull(Params.THUMB_URL, jsonObject));
        orangeFile.setPreviewUrl(
                notNull(Params.PREVIEW_URL, jsonObject));
    }

    private OrangeFileType getOrangeFileType(final String type) {
        return OrangeFileType.fromString(type);
    }

    /**
     * @param jsonObject
     * @return
     */
    private OrangeFileMetadata getMetaData(final JsonObject jsonObject) {
        OrangeFileMetadata orangeFileMetadata = new OrangeFileMetadata();
        JsonObject metadataJson = jsonObject.get(Params.METADATA).getAsJsonObject();
        OrangeFileMetadata.Key.ALL.forEach((key) -> this.addMetaData(key, metadataJson, orangeFileMetadata));
        return orangeFileMetadata;
    }

    /**
     * Prevents NPE from the json deserializer
     *
     * @param key
     * @param jsonObject
     * @return
     */
    private String notNull(final String key, final JsonObject jsonObject) {
        if (jsonObject.has(key)) {
            return jsonObject.get(key).getAsString();
        }
        return null;
    }


    /**
     * @param key
     * @param jsonObject
     * @param orangeFileMetadata
     */
    private void addMetaData(final String key, final JsonObject jsonObject, final OrangeFileMetadata orangeFileMetadata) {
        if (jsonObject.has(key)) {
            orangeFileMetadata.addMetaData(key, jsonObject.get(key).getAsString());
        }
    }


    /**
     * @param date
     * @return
     */
    private Date parseDate(final JsonElement date, final JsonDeserializationContext jsonDeserializationContext) {
        if (date == null) {
            return null;
        }
        return jsonDeserializationContext.deserialize(date, Date.class);
    }
}
