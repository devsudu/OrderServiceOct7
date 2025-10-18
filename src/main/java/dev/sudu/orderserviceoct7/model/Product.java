package dev.sudu.orderserviceoct7.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.util.List;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
    private Long productId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    private Double mrp;
    private Double sellingPrice;
    // Product   ProductImage ==> 1:M
    //    1          M
    //    M          1
    @ManyToMany
    private List<ProductImage> images;
    // Product   Category  ==> M:1
    //    1         1
    //    M         1
    @ManyToOne
    private Category category;
    private Long userId;
    private Boolean isActive;

    public Product() {}

    public Product(ProductBuilder productBuilder) {
        Assert.notNull(productBuilder.productId, "productId must not be null");
        Assert.hasText(productBuilder.name, "Product name cannot be empty");
        Assert.hasText(productBuilder.description, "Product description cannot be empty");
        Assert.notNull(productBuilder.mrp, "Product mrp cannot be null");
        Assert.notNull(productBuilder.sellingPrice, "Product sellingPrice cannot be null");
//        Assert.notEmpty(productBuilder.images, "Product images cannot be empty");
        Assert.notNull(productBuilder.category, "Product category cannot be null");
        Assert.notNull(productBuilder.userId, "userId cannot be null");
        if(productBuilder.sellingPrice > productBuilder.mrp){
            throw new IllegalArgumentException("Selling price cannot be greater than mrp");
        }

        if(productBuilder.id != null) {
            this.setId(productBuilder.id);
        }
        this.productId = productBuilder.productId;
        this.name = productBuilder.name;
        this.description = productBuilder.description;
        this.mrp = productBuilder.mrp;
        this.sellingPrice = productBuilder.sellingPrice;
        this.images = productBuilder.images;
        this.category = productBuilder.category;
        this.userId = productBuilder.userId;
        this.isActive = productBuilder.isActive;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private Long id;
        private Long productId;
        private String name;
        private String description;
        private Double mrp;
        private Double sellingPrice;
        private List<ProductImage> images;
        private Category category;
        private Long userId;
        private Boolean isActive;

        public ProductBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder mrp(Double mrp) {
            this.mrp = mrp;
            return this;
        }

        public ProductBuilder sellingPrice(Double sellingPrice) {
            this.sellingPrice = sellingPrice;
            return this;
        }

        public ProductBuilder images(List<ProductImage> images) {
            this.images = images;
            return this;
        }

        public ProductBuilder category(Category category) {
            this.category = category;
            return this;
        }

        public ProductBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public ProductBuilder isActive(Boolean active) {
            this.isActive = active;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
