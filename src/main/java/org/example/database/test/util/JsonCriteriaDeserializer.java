package org.example.database.test.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.example.database.test.dto.criteria.*;

import java.io.IOException;

public class JsonCriteriaDeserializer extends StdDeserializer<Criteria> {
    public JsonCriteriaDeserializer() {
        super(Criteria.class);
    }

    @Override
    public Criteria deserialize(JsonParser p, DeserializationContext context) throws IOException {
        TreeNode node = p.getCodec().readTree(p);
        TreeNode lastName = node.get("lastName");
        if (lastName != null) {
            return node.traverse(p.getCodec()).readValueAs(LastNameCriteria.class);
        }

        TreeNode productName = node.get("productName");
        if (productName != null) {
            return node.traverse(p.getCodec()).readValueAs(ProductNameAndMinTimesCriteria.class);
        }

        TreeNode minExpenses = node.get("minExpenses");
        if (minExpenses != null) {
            return node.traverse(p.getCodec()).readValueAs(MinAndMaxExpensesCriteria.class);
        }

        TreeNode badCustomers = node.get("badCustomers");
        if (badCustomers != null) {
            return node.traverse(p.getCodec()).readValueAs(BadCustomersCriteria.class);
        }

        throw new JsonParseException(p, "Unrecognized criteria");
    }
}
