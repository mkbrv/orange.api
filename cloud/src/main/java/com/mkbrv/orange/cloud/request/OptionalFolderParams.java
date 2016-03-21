package com.mkbrv.orange.cloud.request;

import com.mkbrv.orange.cloud.model.file.OrangeFileType;

/**
 * Optional parameters for the folder request;
 * Created by mkbrv on 26/02/16.
 */
public class OptionalFolderParams {

    /**
     * Set this as empty string to work;
     * Optional parameter. If present, the application folder will be considered as the root folder, even in full mode.
     */
    private String restrictedMode;

    /**
     * Set this as empty string to work;
     * Optional parameter. If present, the response will contain the thumbnail/preview/download urls for every listed file.
     */
    private String showThumbnails;

    /**
     * Optional parameter. If present, restricts the result to a specified universe. Valid values are [image|video|audio|other].
     */
    private OrangeFileType filter;

    /**
     * Optional parameter. 'false' by default. If set to 'true', the folder will be browsed recursively and the full content will be returned.
     */
    private Boolean flat;

    /**
     * Optional parameter. 'false' by default. If set to 'true', only subfolders will be returned.
     */
    private Boolean tree;

    /**
     * Works only for subfolders (at this moment 03.2016).
     * Attention, impacts the number of files returned
     * Optional pagination parameter. Specifies the maximum number of elements to be listed. Unlimited by default (set to 0).
     */
    private Integer limit;

    /**
     * Works only for subfolders (at this moment 03.2016)
     * Attention, impacts the number of files returned
     * Optional pagination parameter. Specifies the offset of the first element to be listed. Default value is 0.
     */
    private Integer offset;

    public String getRestrictedMode() {
        return restrictedMode;
    }

    public OptionalFolderParams setRestrictedMode(String restrictedMode) {
        this.restrictedMode = restrictedMode;
        return this;
    }

    public String getShowThumbnails() {
        return showThumbnails;
    }

    public OptionalFolderParams setShowThumbnails(String showThumbnails) {
        this.showThumbnails = showThumbnails;
        return this;
    }

    public OrangeFileType getFilter() {
        return filter;
    }

    public OptionalFolderParams setFilter(OrangeFileType filter) {
        this.filter = filter;
        return this;
    }

    public Boolean getFlat() {
        return flat;
    }

    public OptionalFolderParams setFlat(Boolean flat) {
        this.flat = flat;
        return this;
    }

    public Boolean getTree() {
        return tree;
    }

    public OptionalFolderParams setTree(Boolean tree) {
        this.tree = tree;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public OptionalFolderParams setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    public OptionalFolderParams setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }
}
