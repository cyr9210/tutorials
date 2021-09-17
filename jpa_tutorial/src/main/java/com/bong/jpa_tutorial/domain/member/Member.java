package com.bong.jpa_tutorial.domain.member;

import com.bong.jpa_tutorial.domain.team.Team;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
public class Member {

  @Id
  @GeneratedValue
  private Long id;

  @Setter
  @Column(name = "name", length = 50)
  private String name;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;

  public Member(String name) {
    this.name = name;
  }

  public Member(Long id) {
    this.id = id;
  }
}
