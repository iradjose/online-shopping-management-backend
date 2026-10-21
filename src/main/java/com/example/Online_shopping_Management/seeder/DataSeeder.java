package com.example.Online_shopping_Management.seeder;

import com.example.Online_shopping_Management.entity.Courier;
import com.example.Online_shopping_Management.entity.Customer;
import com.example.Online_shopping_Management.entity.CustomerOrder;
import com.example.Online_shopping_Management.entity.DeliveryZone;
import com.example.Online_shopping_Management.entity.Motorbike;
import com.example.Online_shopping_Management.entity.OrderStatus;
import com.example.Online_shopping_Management.entity.Review;
import com.example.Online_shopping_Management.repository.CourierRepository;
import com.example.Online_shopping_Management.repository.CustomerOrderRepository;
import com.example.Online_shopping_Management.repository.CustomerRepository;
import com.example.Online_shopping_Management.repository.DeliveryZoneRepository;
import com.example.Online_shopping_Management.repository.MotorbikeRepository;
import com.example.Online_shopping_Management.repository.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    private final DeliveryZoneRepository deliveryZoneRepository;
    private final MotorbikeRepository motorbikeRepository;
    private final CourierRepository courierRepository;
    private final CustomerRepository customerRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final ReviewRepository reviewRepository;

    public DataSeeder(DeliveryZoneRepository deliveryZoneRepository,
                      MotorbikeRepository motorbikeRepository,
                      CourierRepository courierRepository,
                      CustomerRepository customerRepository,
                      CustomerOrderRepository customerOrderRepository,
                      ReviewRepository reviewRepository) {
        this.deliveryZoneRepository = deliveryZoneRepository;
        this.motorbikeRepository = motorbikeRepository;
        this.courierRepository = courierRepository;
        this.customerRepository = customerRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void run(String... args) {
        if (deliveryZoneRepository.count() > 0) {
            return;
        }

        DeliveryZone nyarugenge = new DeliveryZone();
        nyarugenge.setName("Nyarugenge");

        DeliveryZone gasabo = new DeliveryZone();
        gasabo.setName("Gasabo");

        DeliveryZone kicukiro = new DeliveryZone();
        kicukiro.setName("Kicukiro");

        DeliveryZone rubavu = new DeliveryZone();
        rubavu.setName("Rubavu");

        deliveryZoneRepository.saveAll(Arrays.asList(nyarugenge, gasabo, kicukiro, rubavu));

        Motorbike bike1 = new Motorbike();
        bike1.setBikeNumber(1);
        bike1.setName("Honda CG 125");

        Motorbike bike2 = new Motorbike();
        bike2.setBikeNumber(2);
        bike2.setName("Yamaha YBR");

        Motorbike bike3 = new Motorbike();
        bike3.setBikeNumber(3);
        bike3.setName("Bajaj Boxer");

        Motorbike bike4 = new Motorbike();
        bike4.setBikeNumber(4);
        bike4.setName("TVS Star");

        motorbikeRepository.saveAll(Arrays.asList(bike1, bike2, bike3, bike4));

        Courier courier1 = new Courier();
        courier1.setFirstName("Jean");
        courier1.setLastName("Hakizimana");
        courier1.setPhone("0781234567");
        courier1.setMotorbike(bike1);
        courier1.getDeliveryZones().add(nyarugenge);
        courier1.getDeliveryZones().add(gasabo);

        Courier courier2 = new Courier();
        courier2.setFirstName("Eric");
        courier2.setLastName("Niyonzima");
        courier2.setPhone("0782345678");
        courier2.setMotorbike(bike2);
        courier2.getDeliveryZones().add(kicukiro);

        Courier courier3 = new Courier();
        courier3.setFirstName("Alice");
        courier3.setLastName("Uwimana");
        courier3.setPhone("0783456789");
        courier3.setMotorbike(bike3);

        courierRepository.saveAll(Arrays.asList(courier1, courier2, courier3));

        Customer customer1 = new Customer();
        customer1.setFirstName("Marie");
        customer1.setLastName("Ingabire");
        customer1.setDistrict("Gasabo");
        customer1.setCustomerCode("CUST-1042");
        customer1.setEmail("marie.ingabire@gmail.com");

        Customer customer2 = new Customer();
        customer2.setFirstName("Paul");
        customer2.setLastName("Nkurunziza");
        customer2.setDistrict("Gasabo");
        customer2.setCustomerCode("CUST-1043");
        customer2.setEmail("paul.nkurunziza@gmail.com");

        Customer customer3 = new Customer();
        customer3.setFirstName("Diane");
        customer3.setLastName("Mukamana");
        customer3.setDistrict("Kicukiro");
        customer3.setCustomerCode("CUST-1044");
        customer3.setEmail("diane.mukamana@gmail.com");

        Customer customer4 = new Customer();
        customer4.setFirstName("Patrick");
        customer4.setLastName("Habimana");
        customer4.setDistrict("Nyarugenge");
        customer4.setCustomerCode("CUST-1045");
        customer4.setEmail("patrick.habimana@gmail.com");

        Customer customer5 = new Customer();
        customer5.setFirstName("Grace");
        customer5.setLastName("Uwase");
        customer5.setDistrict("Rubavu");
        customer5.setCustomerCode("CUST-1046");
        customer5.setEmail("grace.uwase@gmail.com");

        customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3, customer4, customer5));

        CustomerOrder order1 = new CustomerOrder();
        order1.setOrderDate(LocalDate.of(2026, 10, 20));
        order1.setStatus(OrderStatus.PLACED);
        order1.setDeliveryAddress("KG 12 Ave, Kigali");
        order1.setTotalAmount(15000);
        order1.setCustomer(customer1);
        order1.setCourier(courier1);

        CustomerOrder order2 = new CustomerOrder();
        order2.setOrderDate(LocalDate.of(2026, 10, 20));
        order2.setStatus(OrderStatus.WITHDRAWN);
        order2.setDeliveryAddress("KK 5 Rd, Kigali");
        order2.setTotalAmount(8000);
        order2.setCustomer(customer2);
        order2.setCourier(courier1);

        CustomerOrder order3 = new CustomerOrder();
        order3.setOrderDate(LocalDate.of(2026, 10, 22));
        order3.setStatus(OrderStatus.SHIPPED);
        order3.setDeliveryAddress("KN 3 Ave, Kigali");
        order3.setTotalAmount(22000);
        order3.setCustomer(customer1);
        order3.setCourier(courier2);

        CustomerOrder order4 = new CustomerOrder();
        order4.setOrderDate(LocalDate.of(2026, 10, 25));
        order4.setStatus(OrderStatus.DELIVERED);
        order4.setDeliveryAddress("KG 7 St, Kigali");
        order4.setTotalAmount(30000);
        order4.setCustomer(customer1);
        order4.setCourier(courier1);

        CustomerOrder order5 = new CustomerOrder();
        order5.setOrderDate(LocalDate.of(2026, 10, 28));
        order5.setStatus(OrderStatus.DELIVERED);
        order5.setDeliveryAddress("KK 11 Ave, Kigali");
        order5.setTotalAmount(12000);
        order5.setCustomer(customer3);
        order5.setCourier(courier2);

        CustomerOrder order6 = new CustomerOrder();
        order6.setOrderDate(LocalDate.of(2026, 11, 1));
        order6.setStatus(OrderStatus.PLACED);
        order6.setDeliveryAddress("KN 8 Rd, Kigali");
        order6.setTotalAmount(18000);
        order6.setCustomer(customer2);
        order6.setCourier(courier3);

        CustomerOrder order7 = new CustomerOrder();
        order7.setOrderDate(LocalDate.of(2026, 11, 5));
        order7.setStatus(OrderStatus.PLACED);
        order7.setDeliveryAddress("KG 15 Ave, Kigali");
        order7.setTotalAmount(9500);
        order7.setCustomer(customer4);
        order7.setCourier(courier1);

        CustomerOrder order8 = new CustomerOrder();
        order8.setOrderDate(LocalDate.of(2026, 11, 8));
        order8.setStatus(OrderStatus.SHIPPED);
        order8.setDeliveryAddress("KK 3 St, Kigali");
        order8.setTotalAmount(25000);
        order8.setCustomer(customer5);
        order8.setCourier(courier2);

        CustomerOrder order9 = new CustomerOrder();
        order9.setOrderDate(LocalDate.of(2026, 11, 12));
        order9.setStatus(OrderStatus.WITHDRAWN);
        order9.setDeliveryAddress("KN 20 Ave, Kigali");
        order9.setTotalAmount(7000);
        order9.setCustomer(customer3);
        order9.setCourier(courier3);

        CustomerOrder order10 = new CustomerOrder();
        order10.setOrderDate(LocalDate.of(2026, 11, 15));
        order10.setStatus(OrderStatus.PLACED);
        order10.setDeliveryAddress("KG 22 Rd, Kigali");
        order10.setTotalAmount(16500);
        order10.setCustomer(customer1);
        order10.setCourier(courier3);

        customerOrderRepository.saveAll(Arrays.asList(
                order1, order2, order3, order4, order5,
                order6, order7, order8, order9, order10));

        Review review1 = new Review();
        review1.setProductName("Wireless Earbuds");
        review1.setComment("Great sound quality and fast delivery");
        review1.setRating(5);
        review1.setReviewDate(LocalDate.of(2026, 10, 26));
        review1.setCustomerOrder(order4);

        Review review2 = new Review();
        review2.setProductName("Phone Case");
        review2.setComment("Good quality, fits perfectly");
        review2.setRating(4);
        review2.setReviewDate(LocalDate.of(2026, 10, 27));
        review2.setCustomerOrder(order4);

        Review review3 = new Review();
        review3.setProductName("USB Cable");
        review3.setComment("Works fine, arrived quickly");
        review3.setRating(4);
        review3.setReviewDate(LocalDate.of(2026, 10, 29));
        review3.setCustomerOrder(order5);

        reviewRepository.saveAll(Arrays.asList(review1, review2, review3));
    }
}
