package com.promineotech.jeep.controller;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.JeepSaleApplication;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.Order;
@SpringBootTest(classes = JeepSaleApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:flyway/migrations/v1.0__Jeep_Schema.sql","classpath:flyway/migrations/v1.1__Jeep_Data.sql"},
		config = @SqlConfig(encoding = "utf-8"))
class CreateOrderTest {
	@LocalServerPort
	private int serverPort;
	@Autowired
	private TestRestTemplate restTemplate;
	@Test 
	void testCreatOderReturnsSuccess201() {
		String body = createOrderBody();
		String uri = String.format("http://localhost:%d/orders", serverPort);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> bodyEntity = new HttpEntity<>(body, headers);
		ResponseEntity<Order> response = restTemplate.exchange(uri,
			    HttpMethod.POST, bodyEntity, Order.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isNotNull();
		Order order = response.getBody();
		assertThat(order.getCustomer().getCustomerId()).isEqualTo("MAYNARD_TORBJORG");
		assertThat(order.getModel().getModelId()).isEqualTo(JeepModel.CHEROKEE);
		assertThat(order.getModel().getTrimLevel()).isEqualTo("Altitude");
		assertThat(order.getModel().getNumDoors()).isEqualTo(4);
		assertThat(order.getColor().getColorId()).isEqualTo("EXT_DIAMOND_BLACK");
		assertThat(order.getEngine().getEngineId()).isEqualTo("2_0_TURBO");
		assertThat(order.getTire().getTireId()).isEqualTo("235_CONTINENTAL");
		assertThat(order.getOptions()).hasSize(3); }
	private String createOrderBody(){
		return  "{\n"
			+" \"customer\":\"MAYNARD_TORBJORG\",\n"
			+" \"model\":\"CHEROKEE\",\n"
			+"  \"trim\":\"Altitude\",\n"
			+"  \"doors\":4,\n"
			+"  \"color\":\"EXT_DIAMOND_BLACK\",\n"
			+"  \"engine\":\"2_0_TURBO\",\n"
			+"  \"tire\":\"235_CONTINENTAL\",\n"
			+"  \"options\":[\n"
			+"    \"DOOR_QUAD_4\",\n"
			+"    \"EXT_MOPAR_KEYLESS\",\n" +"    \"EXT_MOPAR_CAMERA\",\n" +" ]\n" +" }";
	}
		}