package com.bong.jpa_tutorial.domain.team;

import static org.assertj.core.api.Assertions.assertThat;

import com.bong.jpa_tutorial.domain.member.Member;
import com.bong.jpa_tutorial.domain.member.MemberRepository;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.quickperf.spring.sql.QuickPerfSqlConfig;
import org.quickperf.sql.annotation.ExpectInsert;
import org.quickperf.sql.annotation.ExpectSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(QuickPerfSqlConfig.class)
@DataJpaTest
public class TeamRepositoryTest {

  @PersistenceContext
  protected EntityManager entityManager;

  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private MemberRepository memberRepository;

  void flush() {
    entityManager.flush();
  }

  private void flushAndClear() {
    entityManager.flush();
    entityManager.clear();
  }

  @ExpectInsert(3)
  @Test
  public void cascade_persist_insert() {
    Member member1 = new Member("member1");
    Member member2 = new Member("member2");

    Team team = new Team("team");
    team.addMember(member1);
    team.addMember(member2);

    teamRepository.save(team);
    flushAndClear();
  }

  @ExpectSelect(1)
  @Test
  public void getTeam() {
    Optional<Team> byId = teamRepository.findById(1L);
    assertThat(byId).isEmpty();
  }

}