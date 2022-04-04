package com.chertov.coffeemachine.repository;

import com.chertov.coffeemachine.entity.Coffee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CoffeeRepository extends CrudRepository<Coffee, Long> {

    @Query(value = "SELECT number_of_portions FROM PortionsLeft WHERE id = 1", nativeQuery = true)
    int getNumberOfPortionsLeft();

    @Modifying
    @Transactional
    @Query(value = "UPDATE PortionsLeft SET number_of_portions = " +
                   "number_of_portions + :numOfPortions " +
                   "WHERE id = 1", nativeQuery = true)
    void addCoffeeBeans(int numOfPortions);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PortionsLeft SET number_of_portions = " +
                   "number_of_portions - 1 " +
                   "WHERE id = 1 AND number_of_portions > 0", nativeQuery = true)
    void decreasePortionsLeft();
}
