package com.bong.cascade_orphanremoval.domain.member;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  @Column(name = "name", length = 50)
  private String name;

//  @Setter
//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "team_id")
//  private Team team;

  @NaturalId
  private Long uniqueId;

  public Member(String name) {
    this.name = name;
  }

  public Member(Long id) {
    this.id = id;
  }

  public Member(String name, Long uniqueId) {
    this.name = name;
//    this.team = team;
    this.uniqueId = uniqueId;
  }

}
