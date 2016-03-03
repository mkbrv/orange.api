package com.mkbrv.orange.cloud.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mikibrv on 26/02/16.
 */
public enum OrangeFileType {

    IMAGE(new String[]{"IMAGE", "PICTURE"}),
    VIDEO(new String[]{"VIDEO"}),
    AUDIO(new String[]{"AUDIO"}),
    OTHER(new String[]{});


    private List<String> acceptedNames = new ArrayList<>();

    OrangeFileType(String[] typeNames) {
        this.acceptedNames.addAll(Arrays.asList(typeNames));
    }


    public static OrangeFileType fromString(String text) {
        if (text != null) {
            for (OrangeFileType orangeFileType : OrangeFileType.values()) {
                for (String code : orangeFileType.acceptedNames) {
                    if (text.equalsIgnoreCase(code)) {
                        return orangeFileType;
                    }
                }
            }
        }
        return OTHER;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
