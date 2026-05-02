package com.study.service;

import com.study.client.AuthClient;
import com.study.converter.PersonMapper;
import com.study.dto.PersonRs;
import com.study.entity.Person;
import com.study.exception.NotFoundCrmException;
import com.study.repository.PersonRepository;
import com.study.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static com.study.TestData.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AuthClient authClient;

    @Spy
    private final PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    @DisplayName("Успешное получение Person по ID")
    void getPersonByIdTest() {
        when(personRepository.findById(PERSON_ID)).thenReturn(Optional.of(getPerson()));

        personService.getPersonById(PERSON_ID);

        verify(personRepository, times(1)).findById(PERSON_ID);
    }

    @Test
    @DisplayName("Ошибка, Person не найден")
    void getPersonByIdNotFoundTest() {
        when(personRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundCrmException.class, () -> personService.getPersonById(PERSON_ID));
    }

    @Test
    @DisplayName("Сохранение Person: проверка генерации токена и связей")
    void savePersonTest() {
        when(authClient.getToken(anyString())).thenReturn("token");
        when(personRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        PersonRs result = personService.save(getPersonRq());

        assertNotNull(result);
        verify(authClient, times(1)).getToken(anyString());
        verify(personRepository, times(1)).save(any(Person.class));

    }

    @Test
    @DisplayName("Обновление данных Person")
    void updatePersonTest() {
        Person existingPerson = getPerson();
        when(personRepository.findById(PERSON_ID)).thenReturn(Optional.of(existingPerson));
        when(personRepository.save(any(Person.class))).thenReturn(existingPerson);

        PersonRs result = personService.updatePerson(PERSON_ID, getPersonRq());

        assertNotNull(result);
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    @DisplayName("Выброс NotFoundCrmException при обновлении")
    void updatePersonNotFoundTest() {
        when(personRepository.findById(any())).thenReturn(Optional.empty());

        NotFoundCrmException exception = assertThrows(NotFoundCrmException.class,
                () -> personService.updatePerson(PERSON_ID, getPersonRq()));

        assertTrue(exception.getMessage().contains("отсутствует в базе данных"));
    }

    @Test
    @DisplayName("Проверка существования по ФИО и документу")
    void existsPersonTest() {
        when(personRepository.existsByFullNameAndIdentityDocuments(any(), any(), any(), any(), any()))
                .thenReturn(true);

        boolean exists = personService.getPersonByFullNameAndDocument(
                "Ivan",
                "Ivanovich",
                "Ivanov",
                "PASSPORT_RU",
                "1234");

        assertTrue(exists);
    }

    @Test
    @DisplayName("Получение списка с пагинацией")
    void getPersonList_Success() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Person> page = new PageImpl<>(List.of(getPerson()));
        when(personRepository.findAllByVisibleTrue(pageable)).thenReturn(page);

        Page<PersonRs> result = personService.getPersonList(pageable);

        assertEquals(1, result.getTotalElements());
        verify(personRepository, times(1)).findAllByVisibleTrue(pageable);
    }
}
