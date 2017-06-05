package by.itacademy.dao;

import by.itacademy.entity.Employee;
import by.itacademy.entity.Payment;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

/**
 * @author i.sukach
 */
public final class EmployeeDao {

    private static EmployeeDao INSTANCE;

    private EmployeeDao() {
    }

    public static EmployeeDao getInstance() {
        if (INSTANCE == null) {
            synchronized (EmployeeDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EmployeeDao();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Возвращает всех сотрудников
     */

    public List<Employee> findAll(Session session) {

        return session.createQuery("select e from Employee e ", Employee.class)
                .getResultList();
    }

    /**
     * Возвращает всех сотрудников с указанным именем
     */
    public List<Employee> findAllByFirstName(Session session, String firstName) {

        return session.createQuery("select e from Employee e where e.firstName=:firstName", Employee.class)
                .setParameter("firstName", "Bill")
                .getResultList();
    }

    /**
     * Возвращает первые {limit} сотрудников, упорядоченных по дате рождения (в порядке возрастания)
     */
    public List<Employee> findLimitedEmployeesOrderedByBirthday(Session session, int limit) {
        return session.createQuery("select e from Employee as e order by e.birthday", Employee.class)
                .setMaxResults(3)

                .getResultList();
    }

    /**
     * Возвращает всех сотрудников организации с указанным названием
     */
    public List<Employee> findAllByOrganizationName(Session session, String organizationName) {
        return session.createQuery("select e from Employee e where e.organization.name=:org", Employee.class)
                .setParameter("org", organizationName)
                .getResultList();
    }

    /**
     * Возвращает все выплаты, полученные сотрудниками организации с указанными именем,
     * упорядоченные по имени сотрудника, а затем по размеру выплаты
     */
    public List<Payment> findAllPaymentsByOrganizationName(Session session, String organizationName) {
        return session.createQuery("select p from Payment p where p.receiver.organization.name=:org order by p.receiver.firstName, p.amount", Payment.class)
                .setParameter("org", organizationName)
                .getResultList();
    }

    /**
     * Возвращает среднюю зарплату сотрудника с указанными именем и фамилией
     */
    public Double findAveragePaymentAmountByFirstAndLastNames(Session session, String firstName, String lastName) {
        return session.createQuery("select avg(p.amount) from Payment p where p.receiver.firstName=:first and p.receiver.lastName=:last", Double.class)
                .setParameter("first", firstName)
                .setParameter("last", lastName)
                .getSingleResult();
    }

    /**
     * Возвращает для каждой организации: название, среднюю зарплату всех её сотрудников. Организации упорядочены по названию.
     */
    public List<Object[]> findOrganizationNamesWithAvgEmployeePaymentsOrderedByOrgName(Session session) {
        return session.createQuery("select p.receiver.organization.name, avg (p.amount)  " +
                "from Payment p group by p.receiver.organization.name order by p.receiver.organization.name", Object[].class)

                .getResultList()
                ;
    }

    /**
     * Возвращает список: сотрудник (объект Employee), средний размер выплат, но только для тех сотрудников, чей средний размер выплат
     * больше среднего размера выплат всех сотрудников
     * Упорядочить по имени сотрудника
     */
    public List<Object[]> canYouDoIt(Session session) {

        return Collections.emptyList();
    }
}
