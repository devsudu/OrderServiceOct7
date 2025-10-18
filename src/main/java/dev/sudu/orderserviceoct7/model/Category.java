package dev.sudu.orderserviceoct7.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
@Entity
public class Category extends BaseModel {
    @Column(nullable = false)
    private Long categoryId;
    private String name;
    private Long userId;
    private Boolean isActive;

    public Category() {}

    public Category(CategoryBuilder categoryBuilder) {
        Assert.hasText(categoryBuilder.name, " Name cannot be empty");
        Assert.notNull(categoryBuilder.userId, "userId cannot be null");
        if(categoryBuilder.id != null) {
            this.setId(categoryBuilder.id);
        }
        this.categoryId = categoryBuilder.categoryId;
        this.name = categoryBuilder.name;
        this.userId = categoryBuilder.userId;
        this.isActive = categoryBuilder.isActive;
    }

    public static CategoryBuilder builder() {
        return new CategoryBuilder();
    }

    public static class CategoryBuilder {
        private Long id;
        private Long categoryId;
        private String name;
        private Long userId;
        private Boolean isActive;

        public CategoryBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CategoryBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public CategoryBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CategoryBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public CategoryBuilder setActive(Boolean active) {
            this.isActive = active;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
