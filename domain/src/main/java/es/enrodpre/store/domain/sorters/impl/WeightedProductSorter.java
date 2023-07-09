package es.enrodpre.store.domain.sorters.impl;

import es.enrodpre.store.domain.exceptions.ReflectionException;
import es.enrodpre.store.domain.model.Product;
import es.enrodpre.store.domain.sorters.ProductSorter;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author enrodpre
 */
public class WeightedProductSorter implements ProductSorter {

    /**
     * The algorithm used comprises these steps:
     *      1 - Sum up the fields implied for each criteria. In the given use case, it would be the sum of every stock of sizes.
     *          criteria_value_to_be_weighted = sum(field1, field2..fieldN)
     *      2 - Calculate the weighted value for a criteria with the given formula: 
     *          criteria_weighted_value = criteria_value_to_be_weighted * weigh
     *      3 - Sum up every criteria for the same product
     *          product_weighed_value = (criteria_weighted_value1 + criteria_weighted_value2..criteria_weighted_valueN)
     *      4 - Sort the list with the given value product_weighed_value
     * 
     * @param list      list to be sorted
     * @param weights   Map that links every criteria with its weight
     * @return          sorted list
     */
    @Override
    public List<Product> sort(List<Product> list, Map<List<Field>, Double> weights) {
        return list.stream().sorted(new Comparator<Product>() {

            /**
             * Calculates the value for ordering of each criteria for each product.
             * @param product
             * @param fields    class fields tHat store the value used to compare. if the list has more than one field, it sums them up
             * @param weight    the value that will be use to weigh the implied criteria
             * @return          the weighted value of one criteria
             */
            private double calculateCriteriaOrderingValue(Product product, List<Field> fields, Double weight) {
                return weight * fields.stream()
                        .mapToInt(f -> {
                            try {
                                f.setAccessible(true);
                                return f.getInt(product);
                            } catch (IllegalAccessException ex) {
                                throw new ReflectionException(ex);
                            } finally {
                                f.setAccessible(false);
                            }
                        })
                        .sum();

            }
            
            /**
             * Takes every criteria and sums them up
             * @param product       
             * @param weights       Map that links every criteria with its weight
             * @return              The product weighted value used for sorting
             */
            private Double getWeightedOrderingValue(Product product, Map<List<Field>, Double> weights) {
                return weights.entrySet()
                        .stream()
                        .mapToDouble(entry -> calculateCriteriaOrderingValue(product, entry.getKey(), entry.getValue()))
                        .sum();

            }

            @Override
            public int compare(final Product p1, final Product p2) {
                Double weightedOrderingValueProduct1 = getWeightedOrderingValue(p1, weights);
                Double weightedOrderingValueProduct2 = getWeightedOrderingValue(p2, weights);

                return weightedOrderingValueProduct1.compareTo(weightedOrderingValueProduct2);
            }

        }).collect(Collectors.toList());
    }

}
