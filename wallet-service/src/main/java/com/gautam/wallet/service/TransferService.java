package com.gautam.wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gautam.wallet.dto.TransferRequest;
import com.gautam.wallet.entity.Transfer;
import com.gautam.wallet.entity.Wallet;
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

	    Wallet sourceWallet =
	            walletRepository.findById(request.getSourceWalletId()).orElse(null);

	    Wallet destinationWallet =
	            walletRepository.findById(request.getDestinationWalletId()).orElse(null);

	    if (sourceWallet == null || destinationWallet == null) {
	        throw new RuntimeException("Wallet not found");
	    }

	    if (sourceWallet.getBalance() < request.getAmount()) {
	        throw new RuntimeException("Insufficient Balance");
	    }

	    sourceWallet.setBalance(
	            sourceWallet.getBalance() - request.getAmount());

	    destinationWallet.setBalance(
	            destinationWallet.getBalance() + request.getAmount());

	    walletRepository.save(sourceWallet);

	    walletRepository.save(destinationWallet);

	    Transfer transfer = new Transfer(
		        request.getSourceWalletId(),
		        request.getDestinationWalletId(),
		        request.getAmount(),
		        request.getReference(),
		        "SUCCESS");

	    transferRepository.save(transfer);

	    return transfer;
	}
}