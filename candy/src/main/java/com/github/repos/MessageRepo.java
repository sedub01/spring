package com.github.repos;

import com.github.models.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// Репозиторий - это интерфейс, использующий JPA Entity для взаимодействия с Spring Data
// Интерфейс CrudRepository обеспечивает основные операции по поиску, сохранению, удалению данных
public interface MessageRepo extends CrudRepository<Message, Integer> {
    List<Message> findByTag(String tag);
}
