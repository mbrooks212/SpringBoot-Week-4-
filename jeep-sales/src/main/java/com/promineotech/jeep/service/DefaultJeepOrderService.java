package com.promineotech.jeep.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promineo.jeep.dao.JeepOrderDao;
import com.promineotech.jeep.entity.Color;
import com.promineotech.jeep.entity.Customer;
import com.promineotech.jeep.entity.Engine;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.Option;
import com.promineotech.jeep.entity.Order;
import com.promineotech.jeep.entity.OrderRequest;
import com.promineotech.jeep.entity.Tire;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultJeepOrderService implements JeepOrderService {



	@Autowired
	private JeepOrderDao jeepOrderDao;
	
	@Transactional
	@Override
	public Order createOrder(OrderRequest orderRequest) {
		log.info("Order={}", orderRequest);
		Customer customer = getCustomer(orderRequest);
		Jeep jeep = getModel(orderRequest);
		Color color = getColor(orderRequest);
		Engine engine = getEngine(orderRequest);
		Tire tire = getTire(orderRequest);
		List<Option> options = getOption(orderRequest);
		
		BigDecimal price = jeep.getBasePrice().add(color.getPrice())
				.add(engine.getPrice()).add(tire.getPrice());
		
		for(Option option : options) {
			price = price.add(option.getPrice());
		}
		return jeepOrderDao.saveOrder(customer, jeep, color, engine, tire, price, options);
	}

	private List<Option> getOption(OrderRequest orderRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	private Tire getTire(OrderRequest orderRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	private Engine getEngine(OrderRequest orderRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	private Color getColor(OrderRequest orderRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	private Jeep getModel(OrderRequest orderRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	private Customer getCustomer(OrderRequest orderRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
