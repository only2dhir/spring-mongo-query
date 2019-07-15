package com.devglan.springmongoquery.repository;

import com.devglan.springmongoquery.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmpRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Employee> findAll() {
        return mongoTemplate.findAll(Employee.class);
    }

    public Employee save(Employee employee) {
        mongoTemplate.save(employee);
        return employee;
    }

    public Employee update(Employee employee){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(employee.getId()));
        Update update = new Update();
        update.set("name", employee.getName());
        update.set("description", employee.getDescription());
        update.set("salary", employee.getSalary());
        return mongoTemplate.findAndModify(query, update, Employee.class);
    }

    public void deleteById(String empId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(empId));
        mongoTemplate.remove(query, Employee.class);
    }

    public List<Employee> findEmployeeBySalaryRange(double minSalary, double maxSalary) {
        Query query = new Query();
        query.addCriteria(Criteria.where("salary").lt(maxSalary).gt(minSalary));
        return mongoTemplate.find(query, Employee.class);
    }

    public Page findEmployeeByPage() {
        Pageable pageRequest = PageRequest.of(0, 3, Sort.Direction.DESC, "salary");
        Query query = new Query();
        query.with(pageRequest);
        List<Employee> list = mongoTemplate.find(query, Employee.class);
        return PageableExecutionUtils.getPage(
                list,
                pageRequest,
                () -> mongoTemplate.count(query, Employee.class));
    }


}
