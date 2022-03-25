package com.example.eindwerkJava2.model;

import javax.persistence.*;
@Entity
@Table(name="Images")
public class ImageBlob {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long imageId;

        @Column(name = "image_name")
        private String imageName;

        @Lob
        @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
        private byte[] imageLob;

        public long getImageId() {
                return imageId;
        }

        public String getImageName() {
                return imageName;
        }

        public byte[] getImageLob() {
                return imageLob;
        }
}
