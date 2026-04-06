package com.study.galleryreservation.repository;

import com.study.galleryreservation.domain.session.SnsUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SnsUserRepository extends JpaRepository<SnsUser, Long> {
    Optional<SnsUser> findByProviderAndProviderId(String provider, String providerId);
}
