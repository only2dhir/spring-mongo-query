package com.devglan.springmongoquery.repository;

import com.devglan.springmongoquery.model.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
//import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String>/*, QueryDslPredicateExecutor<Employee>*/ {

    @Query(value = "{'name' : ?0}", fields = "{'description' : 0}")
    Employee findEmployeeByName(String empName);

    List<Employee> findByNameAndBandOrderBySalary(String name, String band);

    @Query("{'name' : ?0 , 'band' : ?1}")
    List<Employee> findEmployeeByNameAndBand(String name, String band);

    /*@Query(value = "{'employees.name': ?0, 'employees.salary' : { $gt: ?1 }}", fields = "{'employees' : 0}")
    Department findDepartmentByEmployeeNameAndSalary(String empName, Double salary);*/

    @Query("{salary : {$lt : ?0, $gt : ?1}}")
    List<Employee> findEmployeeBySalaryRange(double maxSalary, double minSalary);

    @Query("{salary : {$lt : ?0, $gt : ?1}}")
    List<Employee> findEmployeeBySalaryRangeAndSort(double maxSalary, double minSalary, Sort sort);

    @Query("{ 'name' : { $regex: ?0 } }")
    List<Employee> findByRegex(String regexp);

    @Query("{name : {$ne : ?0}}")
    List<Employee> findByNameNotEqual(String countryName);

    @Query("{'name' : null}")
    List<Employee> findEmployeeByNameIsNull();

    @Query("{'name' : {$ne : null}}")
    List<Employee> findEmployeeByNameIsNotNull();



}
