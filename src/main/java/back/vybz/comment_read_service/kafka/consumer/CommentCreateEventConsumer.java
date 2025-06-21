package back.vybz.comment_read_service.kafka.consumer;

import back.vybz.comment_read_service.comment.domain.CommentRead;
import back.vybz.comment_read_service.comment.infrastructure.CommentReadRepository;
import back.vybz.comment_read_service.kafka.event.CommentCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentCreateEventConsumer {

    private final CommentReadRepository commentReadRepository;

    @KafkaListener(
            topics = "comment-create-event",
            groupId = "create-comment-read-group",
            containerFactory = "commentCreateEventKafkaListenerContainerFactory"
    )
    public void consume(CommentCreateEvent event) {
        log.info("🟢 Kafka 댓글 생성 이벤트 수신: {}", event);

        CommentRead commentRead = CommentRead.builder()
                .id(event.getId())
                .feedId(event.getFeedId())
                .feedType(event.getFeedType())
                .writerUuid(event.getWriterUuid())
                .writerType(event.getWriterType())
                .comment(event.getComment())
                .parentCommentId(event.getParentCommentId())
                .likeCount(0)
                .build();

        commentReadRepository.save(commentRead);
        log.info("✅ 댓글 Mongo 저장 완료: {}", commentRead.getId());
    }
}
