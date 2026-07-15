package com.gautam.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gautam.wallet.entity.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

}