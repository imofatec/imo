package com.imo.backend.contexts.common;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;

public class MongoDB {
  public static void validateObjectId(String id) {
    if (!ObjectId.isValid(id)) {
      throw new BadRequestException("Id inválido");
    }
  }

  public static MatchOperation buildMatchOperation(
      Map<String, Object> fieldsToSearch, MatchType matchType, CombineWith combineWith) {
    List<Criteria> criteriaList =
        fieldsToSearch.entrySet().stream()
            .map(entry -> buildCriteria(entry.getKey(), entry.getValue(), matchType))
            .toList();

    Criteria combinedCriteria =
        switch (combineWith) {
          case OR -> new Criteria().orOperator(criteriaList.toArray(new Criteria[0]));
          case AND -> new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
        };

    return Aggregation.match(combinedCriteria);
  }

  public static Criteria buildCriteria(String key, Object value, MatchType matchType) {
    if (!(value instanceof String stringValue)) {
      return Criteria.where(key).is(value);
    }

    return switch (matchType) {
      case PERFECT -> Criteria.where(key).is(stringValue);
      case STARTS_WITH -> Criteria.where(key).regex("^" + Pattern.quote(stringValue), "i");
      case CONTAINS -> Criteria.where(key).regex(".*" + Pattern.quote(stringValue) + ".*", "i");
    };
  }
}
