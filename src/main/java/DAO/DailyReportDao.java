package DAO;

import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

    public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }


    public void deleteAllReports() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete DailyReport").executeUpdate();
        transaction.commit();
        session.close();
        System.out.println("All daily reports delete");
    }

    public void updateReport() {
        Transaction transaction = session.beginTransaction();
        session.save(new DailyReport(CarDao.getEarnings(), CarDao.getSoldCars()));

        clearReport();

        transaction.commit();
        session.close();
        System.out.println("UPDATE REPORT");
    }

    public void clearReport(){
        CarDao.setEarnings(0);
        CarDao.setSoldCars(0);
    }

    public DailyReport getLastReport() {
        DailyReport dailyReport = new DailyReport();
        Transaction transaction = session.beginTransaction();
        List<DailyReport> list = session.createQuery("from DailyReport order by id desc").list();
        transaction.commit();
        session.close();

        if (list.isEmpty()) {
            dailyReport.setEarnings(0L);
            dailyReport.setSoldCars(0L);
            return dailyReport;
        }
        return dailyReport = list.get(0);
    }
}
