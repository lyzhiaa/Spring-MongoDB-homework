package co.istad.mongbdhw.base;

import co.istad.mongbdhw.feature.course.dto.FilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FilterByRequestBody {

    // Parses the value based on the type of the field.
    public static <T> Object parseValue(Class<T> entityClass, String column, String value) {
        try {
            Field field = entityClass.getDeclaredField(column);
            return parseBasedOnType(field.getType(), value);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Invalid column: " + column, e);
        }
    }

    private static Object parseBasedOnType(Class<?> type, String value) {
        if (type == String.class) return value;
        if (type == Integer.class || type == int.class) return Integer.parseInt(value);
        if (type == Double.class || type == double.class) return Double.parseDouble(value);
        if (type == Boolean.class || type == boolean.class) return Boolean.parseBoolean(value);
        if (type == LocalDate.class) return LocalDate.parse(value);
        if (type == LocalTime.class) return LocalTime.parse(value);
        if (type == LocalDateTime.class) return LocalDateTime.parse(value);

        throw new IllegalArgumentException("Unsupported type: " + type.getSimpleName());
    }

    //Parses filter criteria from a string for query parameters.
    public static List<Criteria> parseFilterCriteria(String filter) {
        List<Criteria> criteriaList = new ArrayList<>();
        String[] conditions = filter.split(",");

        for (String condition : conditions) {
            String[] parts = condition.split("\\|");
            if (parts.length == 3) {
                String field = parts[0];
                String operator = parts[1];
                String value = parts[2];

                switch (operator.toLowerCase()) {
                    case "eq":
                        criteriaList.add(Criteria.where(field).is(value));
                        break;
                    case "ne":
                        criteriaList.add(Criteria.where(field).ne(value));
                        break;
                    case "gt":
                        criteriaList.add(Criteria.where(field).gt(value));
                        break;
                    case "lt":
                        criteriaList.add(Criteria.where(field).lt(value));
                        break;
                    case "gte":
                        criteriaList.add(Criteria.where(field).gte(value));
                        break;
                    case "lte":
                        criteriaList.add(Criteria.where(field).lte(value));
                        break;
                    case "in":
                        criteriaList.add(Criteria.where(field).in(value.split(";")));
                        break;
                    case "nin":
                        criteriaList.add(Criteria.where(field).nin(value.split(";")));
                        break;
                    case "regex":
                        criteriaList.add(Criteria.where(field).regex(value, "i"));
                        break;
                    case "exists":
                        criteriaList.add(Criteria.where(field).exists(Boolean.parseBoolean(value)));
                        break;
                    default:
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid operator: " + operator);
                }
            }
        }
        return criteriaList;
    }

    //Builds a MongoDB query from a FilterDto object.
    public static <T> Query buildQuery(FilterDto filterDto, Class<T> entityClass) {
        if (filterDto == null || filterDto.getSpecsDto() == null || filterDto.getSpecsDto().isEmpty()) {
            return new Query();
        }

        List<Criteria> criteriaList = filterDto.getSpecsDto().stream()
                .map(spec -> createCriteria(spec, entityClass))
                .toList();

        Criteria criteria = new Criteria();
        if (filterDto.getGlobalOperator() == FilterDto.GlobalOperator.AND) {
            criteria.andOperator(criteriaList.toArray(new Criteria[0]));
        } else {
            criteria.orOperator(criteriaList.toArray(new Criteria[0]));
        }

        return new Query().addCriteria(criteria);
    }

    private static <T> Criteria createCriteria(FilterDto.SpecsDto specs, Class<T> entityClass) {
        Object parsedValue = parseValue(entityClass, specs.getColumn(), specs.getValue());
        Criteria criteria = Criteria.where(specs.getColumn());

        switch (specs.getOperation()) {
            case EQUAL:
                return criteria.is(parsedValue);
            case LIKE:
                return criteria.regex(".*" + parsedValue + ".*", "i");
            case IN:
                return criteria.in(parsedValue);
            case GREATER_THAN:
                return criteria.gt(parsedValue);
            case LESS_THAN:
                return criteria.lt(parsedValue);
            case BETWEEN:
                return criteria.gte(parseValue(entityClass, specs.getColumn(), specs.getValues().get(0)))
                        .lte(parseValue(entityClass, specs.getColumn(), specs.getValues().get(1)));
            case EXISTS:
                return criteria.exists(Boolean.parseBoolean(specs.getValue()));
            case NE:
                return criteria.ne(parsedValue);
            case SIZE:
                return criteria.size(Integer.parseInt(specs.getValue()));
            case ELEMENT_MATCH:
                return criteria.elemMatch(Criteria.where(specs.getSubField()).is(parsedValue));
            default:
                throw new IllegalArgumentException("Unsupported operation: " + specs.getOperation());
        }
    }
}

