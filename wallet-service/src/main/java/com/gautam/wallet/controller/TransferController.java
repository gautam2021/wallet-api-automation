package com.gautam.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gautam.wallet.dto.TransferRequest;
import com.gautam.wallet.entity.Transfer;
import com.gautam.wallet.service.TransferService;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping
    public Transfer transferMoney(@RequestBody TransferRequest request) {

       return transferService.transferMoney(request);

    }
}