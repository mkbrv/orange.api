package com.mkbrv.orange.cloud.model;

/**
 * Created by mkbrv on 20/02/16.
 */
public class OrangeFreeSpace {

    private final Long availableSpace;

    public OrangeFreeSpace(Long availableSpace) {
        this.availableSpace = availableSpace;
    }

    public Long getAvailableSpace() {
        return availableSpace;
    }

    @Override
    public String toString() {
        return "OrangeFreeSpace{" +
                "availableSpace=" + availableSpace +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrangeFreeSpace that = (OrangeFreeSpace) o;

        return availableSpace.equals(that.availableSpace);

    }

    @Override
    public int hashCode() {
        return availableSpace.hashCode();
    }
}
