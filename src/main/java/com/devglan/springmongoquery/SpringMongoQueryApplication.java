package com.devglan.springmongoquery;

import com.devglan.springmongoquery.model.Employee;
import com.devglan.springmongoquery.model.QEmployee;
import com.devglan.springmongoquery.repository.EmpRepository;
import com.devglan.springmongoquery.repository.EmployeeRepository;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

@SpringBootApplication
public class SpringMongoQueryApplication {

	public static Logger logger = LoggerFactory.getLogger(SpringMongoQueryApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringMongoQueryApplication.class, args);
	}

	@Bean
	CommandLineRunner init(EmployeeRepository employeeRepository, EmpRepository empRepository){
		return args -> {
            //employeeRepository.deleteAll();
			List<Employee> employees = Stream.of(1, 2, 3, 4).map(count -> {
				Employee employee = new Employee();
				employee.setName("Emp " + count);
				employee.setDescription("Emp " + 1 + " Description");
				employee.setBand("E " + count);
				employee.setPosition("POS " + count);
				employee.setSalary(4563 + (count * 10 + 45 + count));
				return employee;
			}).collect(Collectors.toList());

			logger.info("Going to save employee");
			employeeRepository.save(employees.get(0));

			//PageRequest firstPageRequest = PageRequest.of(0, 3);
			//PageRequest secondPageRequest = PageRequest.of(0, 3, Sort.by("salary"));
			//PageRequest thirdPageRequest = PageRequest.of(0, 3, Sort.Direction.DESC, "salary");
			//logger.info(employeeRepository.findAll(firstPageRequest).getContent().toString());
			//logger.info(employeeRepository.findAll(secondPageRequest).getContent().toString());
			//logger.info(employeeRepository.findAll(thirdPageRequest).getContent().toString());

            /*Example<Employee> employeeExample = Example.of(new Employee("Emp 1"));
            employeeRepository.findAll(employeeExample);
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("name", startsWith().ignoreCase());
            employeeRepository.findAll(Example.of(new Employee("Emp 1"), matcher));*/

            //employeeRepository.findByNameAndBandOrderBySalary("Emp 1", "E 1");

            //employeeRepository.findEmployeeByName("Emp 1");

            //employeeRepository.findEmployeeByNameAndBand("Emp 1", "E 1");

            //employeeRepository.findEmployeeBySalaryRange(89877755, 123);

            //employeeRepository.findEmployeeBySalaryRangeAndSort(89877755, 123, Sort.by(Sort.Direction.DESC, "salary"));

            //employeeRepository.findByRegex("^Em");

            logger.info(empRepository.findEmployeeByPage().getContent().toString());

            /*QEmployee qEmployee = new QEmployee("employee");
			Predicate predicate = qEmployee.name.eq("Emp 1");
			employeeRepository.findAll(predicate);

			employeeRepository.findAll(qEmployee.salary.between(1234, 787654));

			employeeRepository.findAll(qEmployee.name.contains("p"),
					new PageRequest(0, 2, Sort.Direction.ASC, "salary"));*/
		};
	}

}
