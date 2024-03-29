package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBUtil {
	private static final String presistence_unit_name = "daily_report_system";
	private static EntityManagerFactory emf;

	public static EntityManager createEntityManager() {
		return getEntityManagerFactory().createEntityManager();
	}

	private static EntityManagerFactory getEntityManagerFactory() {
		if(emf == null) {
			emf =Persistence.createEntityManagerFactory(presistence_unit_name);

		}

		return emf;
	}

}
