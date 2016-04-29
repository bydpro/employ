package srmt.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import srmt.java.dao.OilDao;

@Service
public class OilSalesStatisticsService {
	@Autowired
	private OilDao oilDao;
}
