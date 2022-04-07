package com.chertov.coffeemachine.repository;

import com.chertov.coffeemachine.entity.CoffeeMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CoffeeMachineRepository extends JpaRepository<CoffeeMachine, Long> {

    @Query(value = "SELECT number_of_portions FROM CoffeeMachine WHERE id = :id",
           nativeQuery = true)
    int getPortionsLeft(long id);

    @Query(value = "UPDATE CoffeeMachine SET number_of_portions = " +
                   "number_of_portions + :numOfPortions " +
                   "WHERE id = :id " +
                   "RETURNING number_of_portions", nativeQuery = true)
    int fillMachineById(int numOfPortions, long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE CoffeeMachine SET number_of_portions = " +
                   "number_of_portions - 1 " +
                   "WHERE id = :id AND number_of_portions > 0", nativeQuery = true)
    void decreasePortionsLeftById(long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE CoffeeMachine SET number_of_portions = :portions " +
                   "WHERE id = :id", nativeQuery = true)
    void setPortionsLeftById(int portions, long id);
}
