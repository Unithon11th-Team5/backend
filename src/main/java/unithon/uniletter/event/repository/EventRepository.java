package unithon.uniletter.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import unithon.uniletter.common.error.NotFoundException;
import unithon.uniletter.event.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("""
            select event
            from Event event
            join fetch Member member
            on member.id = event.memberId
            where member.nickname= :nickname
            AND event.plannedAt >= :today
            order by event.plannedAt ASC
            """)
    List<Event> findEventsAfterToday(final String nickname, final LocalDate today);

    default Event getById(final UUID id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("[%s] event id not found", id)));
    }
}
