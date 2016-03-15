package com.mkbrv.orange.cloud.model.file;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Wrapper for the metadata on files from Orange;
 * Created by mkbrv on 26/02/16.
 */
public class OrangeFileMetadata {

    /**
     * Identified keys used by orange for the metadata;
     */
    public static class Key {
        public static final String HEIGHT = "height";
        public static final String WIDTH = "width";
        public static final String SHOOTING_DATE = "shootingDate";
        public static final String HAS_COVER = "hasCover";
        public static final String DURATION = "duration";
        public static final String ALBUM_NAME = "albumName";
        public static final String TRACK_NAME = "trackName";
        public static final String ARTIST_NAME = "artistName";

        /**
         * contains all identified keys
         */
        public static final Set<String> ALL = new HashSet<String>() {{
            add(HEIGHT);
            add(WIDTH);
            add(SHOOTING_DATE);
            add(HAS_COVER);
            add(DURATION);
            add(ALBUM_NAME);
            add(TRACK_NAME);
            add(ARTIST_NAME);
        }};
    }

    /**
     * Stores internally all the metadata
     */
    private final Map<String, String> metadata = new HashMap<>();

    /**
     * @param metadata
     */
    public OrangeFileMetadata(final Map<String, String> metadata) {
        this.metadata.putAll(metadata);
    }

    /**
     *
     */
    public OrangeFileMetadata() {
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public OrangeFileMetadata addMetaData(final String key, final String value) {
        if (value != null) {
            this.metadata.put(key, value);
        }
        return this;
    }

    public String getMetaData(final String key) {
        return this.metadata.get(key);
    }

    public Boolean has(final String key) {
        return this.metadata.containsKey(key);
    }

    /**
     * Will return null if value is invalid
     *
     * @param key
     * @return
     */
    public Integer getMetaAsInteger(final String key) {
        try {
            if (has(key)) {
                return Integer.valueOf(getMetaData(key));
            }

        } catch (NumberFormatException e) {

        }
        return null;
    }

}
