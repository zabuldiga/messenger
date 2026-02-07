package org.example.messenger.repository;

import org.example.messenger.MessageStatus;
import org.example.messenger.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("SELECT m FROM Message m WHERE " +
            "(m.senderId = :userId1 AND m.receiverId = :userId2) OR " +
            "(m.senderId = :userId2 AND m.receiverId = :userId1) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findChatHistory(@Param("userId1") Long userId1,
                                  @Param("userId2") Long userId2);

    @Query("SELECT m FROM Message m WHERE m.receiverId = :receiverId AND m.status = :status")
    List<Message> findUnreadMessages(@Param("receiverId") Long receiverId,
                                     @Param("status") MessageStatus status);

    @Query("SELECT m FROM Message m WHERE m.receiverId =: userId ORDER BY m.timestamp DESC")
    List<Message> findIncomingMessage(@Param("userId") Long userId);


}
