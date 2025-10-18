package dev.sudu.orderserviceoct7.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
@Entity
public class ProductImage extends BaseModel {
    @Column(unique = true, nullable = false)
    private Long productImageId;
    private String imageUrl;
    private Boolean isPrimary;
    private Long userId;
    private Boolean isActive;

    public ProductImage() {}

    public ProductImage(ProductImageBuilder productImageBuilder) {
        Assert.hasText(productImageBuilder.imageUrl, "image_url is required");
        Assert.notNull(productImageBuilder.userId, "userId cannot be null");
        if(productImageBuilder.id != null) {
            this.setId(productImageBuilder.id);
        }
        this.productImageId = productImageBuilder.productImageId;
        this.imageUrl = productImageBuilder.imageUrl;
        this.isPrimary = productImageBuilder.isPrimary;
        this.userId = productImageBuilder.userId;
        this.isActive = productImageBuilder.isActive;
    }

    public static ProductImageBuilder productImageBuilder() {
        return new ProductImageBuilder();
    }

    public static class ProductImageBuilder {
        private Long id;
        private Long productImageId;
        private String imageUrl;
        private Boolean isPrimary;
        private Long userId;
        private Boolean isActive;

        public ProductImageBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProductImageBuilder setProductImageId(Long productImageId) {
            this.productImageId = productImageId;
            return this;
        }

        public ProductImageBuilder setImage_url(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ProductImageBuilder setPrimary(Boolean primary) {
            isPrimary = primary;
            return this;
        }

        public ProductImageBuilder setUser_id(Long userId) {
            this.userId = userId;
            return this;
        }

        public ProductImageBuilder setActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public ProductImage build() {
            return new ProductImage(this);
        }
    }
}
