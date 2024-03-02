package com.shakratsanzhar.domain.dto;

import com.shakratsanzhar.validation.ProductNameInfo;
import com.shakratsanzhar.validation.group.CreateAction;
import com.shakratsanzhar.validation.group.UpdateAction;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductCreateEditDto {

    Integer categoryId;

    @NotEmpty(message = "Name shouldn't be empty", groups = {CreateAction.class})
    @ProductNameInfo(groups = {CreateAction.class})
    String name;

    @NotEmpty(message = "Description shouldn't be empty", groups = {CreateAction.class, UpdateAction.class})
    String description;

    @NotEmpty(message = "Author shouldn't be empty", groups = {CreateAction.class, UpdateAction.class})
    String author;

    MultipartFile image;

    @NotNull(message = "Price shouldn't be empty", groups = {CreateAction.class, UpdateAction.class})
    @Range(min = 1,message = "Price shouldn't be negative value or zero", groups = {CreateAction.class, UpdateAction.class})
    Integer price;

    @NotNull(message = "Remaining amount shouldn't be empty", groups = {CreateAction.class, UpdateAction.class})
    @Range(min = 0,message = "Remaining amount shouldn't be negative value", groups = {CreateAction.class, UpdateAction.class})
    Integer remainingAmount;

    LocalDateTime createdAt;
}
