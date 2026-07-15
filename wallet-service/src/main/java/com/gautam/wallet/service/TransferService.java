package com.gautam.wallet.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gautam.wallet.dto.TransferRequest;
import com.gautam.wallet.entity.Transfer;
import com.gautam.wallet.entity.Wallet;
import com.gautam.wallet.exception.InsufficientBalanceException;
import com.gautam.wallet.repository.TransferRepository;
import com.gautam.wallet.repository.WalletRepository;

@Service
public class TransferService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Transactional
    public Transfer transferMoney(TransferRequest request) {

        // Idempotency
        Optional<Transfer> existing =
                transferRepository.findByReference(request.getReference());

        if (existing.isPresent()) {
            return existing.get();
        }

        // Validation
        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (request.getSourceWalletId().equals(request.getDestinationWalletId())) {
            throw new IllegalArgumentException("Source and Destination wallet cannot be the same");
        }

        Wallet sourceWallet =
                walletRepository.findById(request.getSourceWalletId()).orElse(null);

        Wallet destinationWallet =
                walletRepository.findById(request.getDestinationWalletId()).orElse(null);

        if (sourceWallet == null || destinationWallet == null) {
            throw new IllegalArgumentException("Wallet not found");
        }

        if (sourceWallet.getBalance() < request.getAmount()) {
            throw new InsufficientBalanceException("Insufficient Balance");
        }

        sourceWallet.setBalance(
                sourceWallet.getBalance() - request.getAmount());

        destinationWallet.setBalance(
                destinationWallet.getBalance() + request.getAmount());

        walletRepository.save(sourceWallet);
        walletRepository.save(destinationWallet);

        Transfer transfer =
                new Transfer(
                        request.getSourceWalletId(),
                        request.getDestinationWalletId(),
                        request.getAmount(),
                        request.getReference(),
                        "SUCCESS");

        transferRepository.save(transfer);

        return transfer;
    }
}