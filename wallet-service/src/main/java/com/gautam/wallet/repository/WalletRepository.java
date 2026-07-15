package com.gautam.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gautam.wallet.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}