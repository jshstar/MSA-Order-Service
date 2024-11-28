package com.sparta.msa_exam.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.msa_exam.auth.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
