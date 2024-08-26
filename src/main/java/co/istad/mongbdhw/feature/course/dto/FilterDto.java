package co.istad.mongbdhw.feature.course.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class FilterDto {
    private List<SpecsDto> specsDto;
    private GlobalOperator globalOperator;

    @Getter
    @Setter
    public static class SpecsDto {
        private String column;
        private String value;
        private List<String> values;
        private String subField;
        private Operation operation;

        public enum Operation {
            EQUAL, LIKE, IN, GREATER_THAN, LESS_THAN, BETWEEN, EXISTS, NE, SIZE, ELEMENT_MATCH
        }
    }

    public enum GlobalOperator {
        AND, OR
    }
}
