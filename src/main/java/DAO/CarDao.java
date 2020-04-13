package DAO;

import model.Car;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.List;

public class CarDao {
    private Session session;

    private static long earnings;
    private static long soldCars;

    public CarDao(Session session) {
        this.session = session;
    }

    public void addCar(Car car) {
        Transaction transaction = session.beginTransaction();
        session.save(car);
        transaction.commit();
    }

    public List<Car> getAllCars(){
        Transaction transaction = session.beginTransaction();
        List<Car> list = session.createQuery("FROM Car").list();
        transaction.commit();
        session.close();
        return list;
    }

    public List<Car> getCarsFromBrand(String brand) {
        Transaction transaction = session.beginTransaction();
        List<Car> list = session.createQuery("FROM Car WHERE brand = :brandParam")
                .setParameter("brandParam", brand).list();
        transaction.commit();
        session.close();
        System.out.println("Dao get Car success");
        return list;
    }

    public Car byCar(String brand, String model, String licensePlate){
        Transaction transaction = session.beginTransaction();
        Car car = (Car) session.createQuery("from Car where brand = :brand and model = :model and licensePlate = :licensePlate")
                .setParameter("brand", brand)
                .setParameter("model", model)
                .setParameter("licensePlate", licensePlate)
                .uniqueResult();

        session.delete(car);

        earnings += car.getPrice();
        soldCars++;

        System.out.println("Car earn by " + earnings);
        System.out.println("Car sold " + soldCars);

        CarDao.setEarnings(earnings);
        CarDao.setSoldCars(soldCars);

        transaction.commit();
        session.close();
        System.out.println("Dao by car success");
        return car;
    }

    public void deleteCar(Car car){
        Transaction transaction = session.beginTransaction();
        session.delete(car);
        transaction.commit();
        session.close();
    }

    public void deleteAllCars(){
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from Car").executeUpdate();
        transaction.commit();
        session.close();
        System.out.println("All cars delete");
    }

    public static long getEarnings() {
        return earnings;
    }

    public static void setEarnings(long earnings) {
        CarDao.earnings = earnings;
    }

    public static long getSoldCars() {
        return soldCars;
    }

    public static void setSoldCars(long soldCars) {
        CarDao.soldCars = soldCars;
    }
}
