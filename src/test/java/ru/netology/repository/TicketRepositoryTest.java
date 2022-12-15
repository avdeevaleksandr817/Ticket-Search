package ru.netology.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.TicketData;
import ru.netology.exception.AlreadyExistsException;
import ru.netology.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TicketRepositoryTest {
    TicketRepository repositoryEmpty = new TicketRepository();
    TicketRepository repositoryWithOneTicket = new TicketRepository();
    TicketRepository repositoryWithTenTicket = new TicketRepository();

    // тестовые данные
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

    @BeforeEach
    void setup() {
        repositoryWithOneTicket.addTicket(ticketOne);
        repositoryWithTenTicket.addTicket(ticketOne);
        repositoryWithTenTicket.addTicket(ticketTwo);
        repositoryWithTenTicket.addTicket(ticketThree);
        repositoryWithTenTicket.addTicket(ticketFour);
        repositoryWithTenTicket.addTicket(ticketFive);
        repositoryWithTenTicket.addTicket(ticketSix);
        repositoryWithTenTicket.addTicket(ticketSeven);
        repositoryWithTenTicket.addTicket(ticketEight);
        repositoryWithTenTicket.addTicket(ticketNine);
        repositoryWithTenTicket.addTicket(ticketTen);
    }

    @Test
    @DisplayName("добавление билета")
    void shouldAddTicket() {
        TicketData[] expected = new TicketData[]{
                ticketOne,
                ticketTwo,
                ticketThree};
        repositoryEmpty.addTicket(ticketOne);
        repositoryEmpty.addTicket(ticketTwo);
        repositoryEmpty.addTicket(ticketThree);
        assertArrayEquals(expected, repositoryEmpty.findAll());
    }

    @Test
    @DisplayName("добавить исключение для билетов")
    void shouldAddTicketException() {
        assertThrows(AlreadyExistsException.class, () -> {
            repositoryWithTenTicket.addTicket(ticketOne);
        });
    }

    @Test
    @DisplayName("удаление по идентификатору")
    void shouldRemoveById() {
        TicketData[] expected = new TicketData[]{
                ticketOne,
                ticketTwo,
                ticketThree,
                ticketFour,
                ticketFive,
                ticketNine,
                ticketTen};
        repositoryWithTenTicket.removeById(6);
        repositoryWithTenTicket.removeById(7);
        repositoryWithTenTicket.removeById(8);
        assertArrayEquals(expected, repositoryWithTenTicket.findAll());
    }

    @Test
    @DisplayName("исключения при удаление по идентификатору")
    void shouldRemoveByIdException() {
        assertThrows(NotFoundException.class, () -> {
            repositoryWithTenTicket.removeById(11);
        });
    }

    @Test
    @DisplayName("найти по идентификатору действительный")
    void shouldFindByIdPass() {
        assertEquals(ticketEight, repositoryWithTenTicket.findById(8));
    }

    @Test
    @DisplayName("поиск по идентификатору не существующий билет")
    void shouldFindByIdNull() {
        assertNull(repositoryWithTenTicket.findById(12));
    }
}