package dev.pethaven.repositories;

import dev.pethaven.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    Page<Message> findAllByChatId(Long chatId, Pageable pageable);
}
