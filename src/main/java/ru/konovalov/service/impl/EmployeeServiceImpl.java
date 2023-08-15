package ru.konovalov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.konovalov.dao.DaoInterface;
import ru.konovalov.exception_handling.NoContentException;
import ru.konovalov.exception_handling.NoSuchElementException;
import ru.konovalov.model.Employee;
import ru.konovalov.service.ServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class EmployeeServiceImpl implements ServiceInterface<Employee> {

    private final DaoInterface<Employee> employeeDao;

    @Autowired
    public EmployeeServiceImpl(DaoInterface<Employee> employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> getAll() throws NoContentException {
        List<Employee> employeeList = new ArrayList<>(employeeDao.getAll());
        if (employeeList.isEmpty()) {
            throw new NoContentException("Database is Empty");
        }
        return employeeDao.getAll();
    }

    @Override
    public void create(Employee employee) throws NoContentException {
        if (employee == null) {
            throw new NoContentException("Employee is empty");
        } else {
            employeeDao.create(employee);
        }
    }

    @Override
    public Employee update(Employee employee) throws NoSuchElementException {
        isPresentEmployee(employee.getId());
        return employeeDao.update(employee);
    }

    @Override
    public Employee get(long id) throws NoSuchElementException {
        isPresentEmployee(id);
        return employeeDao.get(id);
    }

    @Override
    public void delete(long id) throws NoSuchElementException {
        isPresentEmployee(id);
        employeeDao.delete(id);
    }

    private void isPresentEmployee(long id) throws NoSuchElementException {
        Optional<Employee> employee = Optional.ofNullable(employeeDao.get(id));
        if (employee.isEmpty()) {
            throw new NoSuchElementException("There is no employee with ID = "
                    + id + " in Database");
        }
    }
}
