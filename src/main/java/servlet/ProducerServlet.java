package servlet;

import model.Car;
import service.CarService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProducerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CarService carService = CarService.getInstance();

        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String licensePlate = request.getParameter("licensePlate");
        Long price = Long.valueOf(request.getParameter("price"));

        if (carService.carsCount(brand) <= 10){
            carService.addCar(brand, model, licensePlate, price);
            response.setStatus(200);
        } else {
            response.setStatus(403);
        }
    }
}