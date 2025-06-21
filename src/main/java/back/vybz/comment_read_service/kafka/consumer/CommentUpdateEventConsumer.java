package back.vybz.comment_read_service.kafka.consumer;

import back.vybz.comment_read_service.comment.infrastructure.CommentReadRepository;
import back.vybz.comment_read_service.kafka.event.CommentUpdateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentUpdateEventConsumer {

    private final MongoTemplate mongoTemplate;

    @KafkaListener(
            topics = "comment-update-event",
            groupId = "update-comment-read-group",
            containerFactory = "commentUpdateEventKafkaListenerContainerFactory"
    )
    public void consume(CommentUpdateEvent event) {
        log.info("✏️ Kafka 댓글 수정 이벤트 수신: {}", event);

        Query query = new Query(where("_id").is(event.getId()));
        Update update = new Update()
                .set("comment", event.getComment())
                .set("updatedAt", event.getUpdatedAt());

        mongoTemplate.updateFirst(query, update, "comment_read");
        log.info("✅ 댓글 수정 완료: {}", event.getId());
    }
}

