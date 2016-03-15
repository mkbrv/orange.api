package com.mkbrv.orange.cloud.model;

import java.util.List;

/**
 * Created by mkbrv on 15/03/16.
 */
public interface OrangeFolder {

    String getId();

    String getParentFolderId();

    String getName();

    List<OrangeFile> getFiles();

    OrangeFolder addFile(final OrangeFile orangeFile);

    OrangeFolder addFiles(final List<OrangeFile> orangeFiles);

    List<OrangeFolder> getSubFolders();

    OrangeFolder addSubFolders(List<OrangeFolder> subFolders);

    OrangeFolder addSubFolder(OrangeFolder subFolder);
}

