package co.istad.mongbdhw.feature.category;

import co.istad.mongbdhw.feature.category.dto.CategoryCreateRequest;
import co.istad.mongbdhw.feature.category.dto.CategoryResponse;
import co.istad.mongbdhw.feature.category.dto.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {
    //Create category
    void createCategory(CategoryCreateRequest categoryCreateRequest);

    //Find all category
    List<CategoryResponse> findAllCategory();

    //Find category by id
    CategoryResponse findCategoryById(String id);

    //Update category by id
    CategoryResponse updateCategory(String id, CategoryUpdateRequest categoryUpdateRequest);

    //Delete category by id
    void deleteCategory(String id);

    //Enable category by id
    void enableCategory(String id);

    //Disable category by id
    void disableCategory(String id);

}
