package unithon.uniletter.event.dto;

import unithon.uniletter.event.Event;

import java.time.LocalDate;
import java.util.List;

public record EventResponse(String id, String content, String type, LocalDate plannedAt) {

    public static List<EventResponse> createList(final List<Event> event) {
        return event.stream()
                .map(EventResponse::from)
                .toList();
    }

    public static EventResponse from(final Event event) {
        return new EventResponse(event.getId().toString(), event.getContent(), event.getType().getKoreanName(), event.getPlannedAt());
    }
}
