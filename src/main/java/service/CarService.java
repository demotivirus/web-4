package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

    public boolean addCar(String brand, String model, String licensePlate, Long price){
        Session session = sessionFactory.openSession();
        CarDao carDao = new CarDao(session);

//        int carsCount = carDao.getCarsFromBrand(brand).size();
//        if (carsCount >= 10) {
//            return false;
//        }

        Car car = new Car(brand, model, licensePlate, price);
        carDao.addCar(car);
        session.close();
        return true;
    }

    public List<Car> getAllCars(){
        return new CarDao(sessionFactory.openSession()).getAllCars();
    }

    public long carsCount(String brand){
        Session session = sessionFactory.openSession();
        CarDao carDao = new CarDao(session);

        return carDao.getCarsFromBrand(brand).size();
    }

    public Car byCar(String brand, String model, String licensePlate){
        Session session = sessionFactory.openSession();
        CarDao carDao = new CarDao(session);

        return carDao.byCar(brand, model, licensePlate);
    }

    public void deleteAllCars(){
        Session session = sessionFactory.openSession();
        CarDao carDao = new CarDao(session);

        carDao.deleteAllCars();
    }
}
