package com.bong.cascade_orphanremoval.domain.team;

import com.bong.cascade_orphanremoval.domain.member.Member;
import com.bong.cascade_orphanremoval.domain.member.MemberRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class TeamRepositoryTest {

  @PersistenceContext
  protected EntityManager entityManager;

  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private MemberRepository memberRepository;


  @AfterEach
  void flush() {
    entityManager.flush();
  }

  private void flushAndClear() {
    entityManager.flush();
    entityManager.clear();
  }

  @Test
  void cascade_persist_insert() {
    Member member1 = new Member("member1");
    Member member2 = new Member("member2");

    Team team = new Team("team");
    team.addMember(member1);
    team.addMember(member2);

    teamRepository.save(team);
  }

  @Test
  void cascade_merge_remove() {
    saveSample();
    flushAndClear();

    Team team = teamRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException());
    team.getMembers().clear();
    System.out.println("============== END =============");
  }

  private void saveSample() {
    Member memberSample = new Member("member1");
    Team teamSample = new Team("team");
    teamSample.addMember(memberSample);
    teamRepository.save(teamSample);
    memberRepository.save(memberSample);
  }
}