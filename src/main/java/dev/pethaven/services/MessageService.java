package dev.pethaven.services;

import dev.pethaven.dto.MessageDTO;
import dev.pethaven.entity.Chat;
import dev.pethaven.entity.Message;
import dev.pethaven.entity.Pet;
import dev.pethaven.exception.InvalidChatException;
import dev.pethaven.mappers.MessageMapper;
import dev.pethaven.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    OrganizationService organizationService;
    @Autowired
    UserService userService;
    @Autowired
    PetService petService;
    @Autowired
    ChatService chatService;

    public Page<MessageDTO> getMessagesByChat(Long chatId, int page, int size, String principalName) {
        if (!chatService.isParticipant(chatId, principalName)) {
            throw new InvalidChatException("The messages of this chat are not available");
        }
        return messageRepository.findAllByChatId(chatId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"))).map(messageMapper::toDto);
    }

    @Transactional
    public void addMessage(@Valid MessageDTO messageDTO, String username) {
        if (!chatService.isParticipant(messageDTO.getChatId(), username)) {
            throw new InvalidChatException("The messages of this chat are not available");
        }
        Chat chat = chatService.findById(messageDTO.getChatId());
        Message newMessage = new Message(
                messageDTO.getMessage(),
                LocalDateTime.parse(messageDTO.getDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
                chat.getId()
        );
        if (messageDTO.getUserUsername() != null) {
            newMessage.setUserId(chat.getUserId());
        } else {
            newMessage.setOrganizationId(chat.getOrganizationId());
        }
        chat.setDateLastMessage(newMessage.getDate());
        newMessage.setChat(chat);
        messageRepository.save(newMessage);
    }

    public void addRequestMessage(@NotNull(message = "Pet's id can't be null") Long petId,
                                  @NotNull(message = "Chat can't be null") Chat chat) {

        Pet pet = petService.findById(petId);
        Message message = new Message(
                "Заявка на питомца с кличкой " + pet.getName() + ". Ссылка на питомца: http://localhost:4200/home/" + pet.getId(),
                LocalDateTime.now(),
                chat.getId()
        );
        message.setUserId(chat.getUserId());
        chat.setDateLastMessage(message.getDate());
        message.setChat(chat);
        messageRepository.save(message);
    }
}
