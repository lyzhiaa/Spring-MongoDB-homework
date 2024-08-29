package co.istad.mongbdhw.feature.category;

import co.istad.mongbdhw.domain.Category;
import co.istad.mongbdhw.feature.category.dto.CategoryCreateRequest;
import co.istad.mongbdhw.feature.category.dto.CategoryResponse;
import co.istad.mongbdhw.feature.category.dto.CategoryUpdateRequest;
import co.istad.mongbdhw.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    //Create a new category
    @Override
    public void createCategory(CategoryCreateRequest categoryCreateRequest) {
        //validate category
        if (categoryRepository.existsByName(categoryCreateRequest.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This Category is already exist!!!");
        }
        Category category = categoryMapper.fromCategoryCreateRequest(categoryCreateRequest);
        category.setUuid(UUID.randomUUID().toString());
        categoryRepository.save(category);
    }

    //Find all categories
    @Override
    public List<CategoryResponse> findAllCategory() {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        List<Category> categories =categoryRepository.findAll(sortById);
        return categoryMapper.toCategoryResponseList(categories);
    }

    //Find category by id
    @Override
    public CategoryResponse findCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This category has not been found"));
        return categoryMapper.toCategoryResponse(category);
    }

    //Update category by id
    @Override
    public CategoryResponse updateCategory(String id, CategoryUpdateRequest categoryUpdateRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This category has not been found"));

        categoryMapper.fromCategoryUpdateRequest(categoryUpdateRequest, category);
        category = categoryRepository.save(category);

        return categoryMapper.toCategoryResponse(category);
    }

    //Delete category by id
    @Override
    public void deleteCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This category has not been found"));
        categoryRepository.delete(category);
    }

    //Enable category by id
    @Override
    public void enableCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This category has not been found"));
        category.setIsDeleted(false);
        categoryRepository.save(category);
    }

    //Disable category by id
    @Override
    public void disableCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "This category has not been found"));
        category.setIsDeleted(true);
        categoryRepository.save(category);
    }
}
