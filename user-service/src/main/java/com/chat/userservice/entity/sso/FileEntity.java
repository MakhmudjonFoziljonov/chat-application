package com.chat.userservice.entity.sso;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "files")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    private String fileType;

    @Column(name = "extension")
    private String extension;

    @Column(name = "modified_name")
    private String modifiedName;

    @Column(name = "path")
    private String path;

    @Column(name = "url")
    private String url;

    @Column(name = "absolute_path")
    private String absolutePath;

    @Column(name = "size")
    private Long size;

    @Column(name = "status")
    private Boolean status;
}
