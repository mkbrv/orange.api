package com.mkbrv.orange.cloud.request;

import com.mkbrv.orange.cloud.model.OrangeFileType;

/**
 * Optional parameters for the folder request;
 * Created by mikibrv on 26/02/16.
 */
public class OrangeFolderRequestParams {

    private String restrictedMode;

    private String showThumbnails;

    private OrangeFileType filter;

    private Boolean flat;

    private Boolean tree;

    private Integer limit;

    private Integer offset;

}
