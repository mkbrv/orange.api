package com.mkbrv.orange.cloud.model;


import com.mkbrv.orange.cloud.model.folder.DefaultOrangeFolder;

import java.util.List;

/**
 * OrangeFolder as described in: https://developer.orange.com/apis/cloud-france/api-reference.
 * Created by mkbrv on 15/03/16.
 */
public interface OrangeFolder {

    /**
     * @return id of the folder;
     */
    String getId();

    /**
     * @return parent of the current folder;
     */
    String getParentFolderId();

    /**
     * @return name of the folder
     */
    String getName();

    /**
     * @return files in this folder
     */
    List<OrangeFile> getFiles();

    /**
     * @param orangeFile file to be added
     * @return current folder
     */
    OrangeFolder addFile(final OrangeFile orangeFile);

    /**
     * @param orangeFiles to be added
     * @return current folder
     */
    OrangeFolder addFiles(final List<OrangeFile> orangeFiles);

    /**
     * @return subfolders
     */
    List<OrangeFolder> getSubFolders();

    /**
     * @param subFolders
     * @return
     */
    OrangeFolder addSubFolders(List<OrangeFolder> subFolders);

    /**
     * @param subFolder
     * @return
     */
    OrangeFolder addSubFolder(OrangeFolder subFolder);

    /**
     * default instance with an ID
     *
     * @param id
     * @return
     */
    static OrangeFolder newInstance(final String id) {
        return new DefaultOrangeFolder(id);
    }

    /**
     * default instance
     * Acts as a copy constructor;
     *
     * @param orangeFolder original;
     * @return copy folder
     */
    static OrangeFolder newInstance(final OrangeFolder orangeFolder) {
        DefaultOrangeFolder defaultOrangeFolder = new DefaultOrangeFolder(orangeFolder.getId())
                .setName(orangeFolder.getName())
                .setParentFolderId(orangeFolder.getParentFolderId());
        orangeFolder.getSubFolders().forEach((subfolder) ->
                defaultOrangeFolder.addSubFolder(OrangeFolder.newInstance(subfolder)));
        orangeFolder.getFiles().forEach((file) -> defaultOrangeFolder.addFile(OrangeFile.newInstance(file)));
        return defaultOrangeFolder;
    }
}

