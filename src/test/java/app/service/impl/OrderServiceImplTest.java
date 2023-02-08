package app.service.impl;

import app.exceptions.service.ServiceNotFoundException;
import app.repository.OrderRep;
import app.repository.entity.course.Course;
import app.repository.entity.order.Order;
import app.repository.entity.user.PersonalInfo;
import app.repository.entity.user.User;
import app.service.OrderService;
import app.service.converters.EntityDtoMapper;
import app.service.dto.order.OrderDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderServiceImplTest {
    private static OrderService orderService;

    private static final Long INVALID_ORDER_ID = Long.MIN_VALUE;
    private static final Long VALID_ORDER_ID = 1L;


    @BeforeAll
    static void beforeAll() {
        OrderRep orderRep = Mockito.mock(OrderRep.class);

        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setId(1L);
        personalInfo.setDeleted(false);
        personalInfo.setFirstName("testName");
        personalInfo.setLastName("testLastname");
        personalInfo.setPatronymic("testPatronymic");
        personalInfo.setEmail("test@gmail.com");
        personalInfo.setDayOfBirth(LocalDate.of(2000, 1, 1));

        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setPassword("password");
        user.setPersonalInfo(personalInfo);
        user.setRole(User.Role.USER);
        user.setEnabled(true);
        user.setDeleted(false);

        Course course = new Course();
        course.setId(1L);
        course.setCourseInfo("Test info");
        course.setCost(BigDecimal.ONE);
        course.setName("Test name");
        course.setDurationDays(1);
        course.setDiscount(BigDecimal.ZERO);
        course.setDeleted(false);

        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setCourse(course);
        order.setStatus(Order.Status.PAYED);
        order.setTotalCost(BigDecimal.ONE);

        Mockito.when(orderRep.save(order)).thenReturn(order);
        Mockito.when(orderRep.findById(VALID_ORDER_ID)).thenReturn(Optional.of(order));

        EntityDtoMapper mapper = new EntityDtoMapper(new ModelMapper());
        orderService = new OrderServiceImpl(orderRep, mapper);
    }

    @Test
    void createOrderNullInExceptionOut() {
        assertThrows(ServiceNotFoundException.class, () -> orderService.create(null));
    }

    @Test
    void createOrderWithInvalidID() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(INVALID_ORDER_ID);
        assertThrows(ServiceNotFoundException.class, () -> orderService.create(orderDto));
    }

    @Test
    void getOrderNotFound() {
        assertThrows(ServiceNotFoundException.class, () -> orderService.get(INVALID_ORDER_ID));
    }

    @Test
    void updateOrderWithInvalidID() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(INVALID_ORDER_ID);
        assertThrows(ServiceNotFoundException.class, () -> orderService.update(orderDto));
    }

    @Test
    void updateOrderNullInExceptionOut() {
        assertThrows(ServiceNotFoundException.class, () -> orderService.update(null));
    }
}