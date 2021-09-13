package com.karankumar.booksapi.model.cover;

public enum ImageFileType {
    JPG(1),
    PNG(2);

    private final Integer id;

    ImageFileType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public ImageFileType valueOf(Integer id) {
        for (ImageFileType value : values()) {
            if (value.id.equals(id)) {
                return value;
            }
        }
        return null;
    }
}
