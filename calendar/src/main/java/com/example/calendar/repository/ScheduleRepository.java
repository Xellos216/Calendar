package com.example.calendar.repository;


import com.example.calendar.dto.SchedulePageItemDto;
import com.example.calendar.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(
            value = """
        select new com.example.calendar.dto.SchedulePageItemDto(
            s.id,
            s.title,
            s.contents,
            u.id,
            u.username,
            s.createdAt,
            s.modifiedAt,
            count(c.id)
        )
        from Schedule s
          join s.user u
          left join Comment c on c.schedule = s
        group by s.id, s.title, s.contents, u.id, u.username, s.createdAt, s.modifiedAt
        """,
            countQuery = """
        select count(distinct s.id)
        from Schedule s
        """
    )

    Page<SchedulePageItemDto> findPageWithCommentCount(Pageable pageable);

    Page<Schedule> findAllByUser_Id(Long userId, Pageable pageable);
    long countByUser_Id(Long userId);
}
