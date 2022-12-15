package ru.netology.manager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.netology.data.TicketData;
import ru.netology.exception.NotFoundException;
import ru.netology.repository.TicketRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class TicketManagerTest {
    @Mock
    TicketRepository repository = Mockito.mock(TicketRepository.class);
    @InjectMocks
    TicketManager manager = new TicketManager(repository);

    // подготовка тестовых данных
    TicketData ticketOne = new TicketData(1, 10000, "DME", "LED", 100);
    TicketData ticketTwo = new TicketData(2, 20000, "SVO", "LED", 200);
    TicketData ticketThree = new TicketData(3, 30000, "VKO", "RVH", 300);
    TicketData ticketFour = new TicketData(4, 40000, "SVO", "LED", 400);
    TicketData ticketFive = new TicketData(5, 50000, "SVO", "LED", 500);
    TicketData ticketSix = new TicketData(6, 60000, "VKO", "LED", 600);
    TicketData ticketSeven = new TicketData(7, 70000, "SVO", "RVH", 700);
    TicketData ticketEight = new TicketData(8, 80000, "SVO", "LED", 800);
    TicketData ticketNine = new TicketData(9, 90000, "VKO", "LED", 900);
    TicketData ticketTen = new TicketData(10, 100000, "SVO", "RVH", 1000);

    //заглушки репозитория
    TicketData[] mockEmpty = new TicketData[0];
    TicketData[] mockOneTicket = new TicketData[]{ticketOne};
    TicketData[] mockTenTicket = new TicketData[]{
            ticketOne,
            ticketTwo,
            ticketThree,
            ticketFour,
            ticketFive,
            ticketSix,
            ticketSeven,
            ticketEight,
            ticketNine,
            ticketTen};

    //тесты на исключение NotFoundException в findAll()
    @Test
    @DisplayName("Не должен находить пустой билет")
    void shouldNotFindMockEmpty() {
        doReturn(mockEmpty).when(repository).findAll();
        assertThrows(NotFoundException.class, () -> {
            manager.findAll("DME", "LED");
        });
    }

    @Test
    @DisplayName("нет совпадений с первым билетом")
    void shouldNotFindMockWithOneTicket() {
        doReturn(mockOneTicket).when(repository).findAll();
        assertThrows(NotFoundException.class, () -> {
            manager.findAll("DME", "PES");
        });
    }

    @Test
    @DisplayName("нет совпадений с десятью билетами")
    void shouldNotFindMockWithTenTicket() {
        doReturn(mockTenTicket).when(repository).findAll();
        assertThrows(NotFoundException.class, () -> {
            manager.findAll("VKO", "PES");
        });
    }

    //тесты на один результат поиска в findAll()
    @Test
    @DisplayName("поиск по аэропортам вылета и прилета найти один билет, из 1")
    void shouldFindOneResultMockWithOneTicket() {
        doReturn(mockOneTicket).when(repository).findAll();
        TicketData[] expected = new TicketData[]{ticketOne};
        assertArrayEquals(expected, manager.findAll("DME", "LED"));
    }

    @Test
    @DisplayName("поиск по аэропортам вылета и прилета найти один билет, из 10")
    void shouldFindOneResultMockWithTenTicket() {
        doReturn(mockTenTicket).when(repository).findAll();
        TicketData[] expected = new TicketData[]{ticketThree};
        assertArrayEquals(expected, manager.findAll("VKO", "RVH"));
    }

    //тесты на несколько результатов в findAll()
    @Test
    @DisplayName("поиск по аэропортам вылета и прилета, из 10 билетов, вариант 1")
    void shouldFindManyResultsMockWithTenTicketOne() {
        doReturn(mockTenTicket).when(repository).findAll();
        TicketData[] expected = new TicketData[]{
                ticketSeven,
                ticketTen};
        assertArrayEquals(expected, manager.findAll("SVO", "RVH"));
    }

    @Test
    @DisplayName("поиск по аэропортам вылета и прилета, из 10 билетов, вариант 2")
    void shouldFindManyResultsMockWithTenTicketTwo() {
        doReturn(mockTenTicket).when(repository).findAll();
        TicketData[] expected = new TicketData[]{
                ticketTwo,
                ticketFour,
                ticketFive,
                ticketEight,
                };
        assertArrayEquals(expected, manager.findAll("SVO", "LED"));
    }

    //тесты на matches()
    @Test
    @DisplayName("проверка совпадения данных, True")
    void shouldMatchesFromToTrue() {
        assertTrue(manager.matchesFromTo(ticketOne, "DME", "LED"));
    }

    @Test
    @DisplayName("вариант 1 проверка совпадения данных, False")
    void shouldMatchesFromToFalseOne() {
        assertFalse(manager.matchesFromTo(ticketOne, "SVO", "LED"));
    }

    @Test
    @DisplayName("вариант 2 проверка совпадения данных, False")
    void shouldMatchesFromToFalseTwo() {
        assertFalse(manager.matchesFromTo(ticketOne, "DME", "RVH"));
    }

    @Test
    @DisplayName("вариант 3 проверка совпадения данных, False")
    void shouldMatchesFromToFalseThree() {
        assertFalse(manager.matchesFromTo(ticketOne, "VKO", "RVH"));
    }
}