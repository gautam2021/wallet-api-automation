package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import base.BaseTest;
import io.restassured.response.Response;
import models.TransferRequest;
import utils.DatabaseUtils;
import utils.WalletClient;

public class WalletTransferTest extends BaseTest {

    WalletClient walletClient = new WalletClient();
    DatabaseUtils db = new DatabaseUtils();

    @BeforeEach
    public void resetBalances() {

        db.updateWalletBalance(1L, 10000);
        db.updateWalletBalance(2L, 5000);
    }

    @Test
    public void verifyMoneyTransfer() {

        double sourceBalanceBefore = db.getWalletBalance(1L);
        double destinationBalanceBefore = db.getWalletBalance(2L);

        String reference = "TXN" + System.currentTimeMillis();

        TransferRequest request =
                new TransferRequest(
                        1L,
                        2L,
                        1000.0,
                        reference);

        Response response = walletClient.transferMoney(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("SUCCESS",
                response.jsonPath().getString("status"));

        double sourceBalanceAfter = db.getWalletBalance(1L);
        double destinationBalanceAfter = db.getWalletBalance(2L);

        assertEquals(sourceBalanceBefore - 1000, sourceBalanceAfter);
        assertEquals(destinationBalanceBefore + 1000, destinationBalanceAfter);

        assertEquals("SUCCESS",
                db.getTransferStatus(reference));

        System.out.println(response.asPrettyString());
    }

    @Test
    public void verifyTransferFailsForInsufficientBalance() {

        String reference = "TXN" + System.currentTimeMillis();

        TransferRequest request =
                new TransferRequest(
                        1L,
                        2L,
                        100000.0,
                        reference);

        Response response = walletClient.transferMoney(request);

        assertEquals(400, response.getStatusCode());
        assertTrue(response.asString().contains("Insufficient"));

        System.out.println(response.asPrettyString());
    }

    @Test
    public void verifyNegativeAmount() {

        String reference = "TXN" + System.currentTimeMillis();

        TransferRequest request =
                new TransferRequest(
                        1L,
                        2L,
                        -500.0,
                        reference);

        Response response = walletClient.transferMoney(request);

        assertEquals(400, response.getStatusCode());
        assertTrue(response.asString().contains("Amount"));

        System.out.println(response.asPrettyString());
    }

    @Test
    public void verifySameWallet() {

        String reference = "TXN" + System.currentTimeMillis();

        TransferRequest request =
                new TransferRequest(
                        1L,
                        1L,
                        100.0,
                        reference);

        Response response = walletClient.transferMoney(request);

        assertEquals(400, response.getStatusCode());
        assertTrue(response.asString().contains("Source"));

        System.out.println(response.asPrettyString());
    }

    @Test
    public void verifyDuplicateTransfer() {

        double sourceBefore = db.getWalletBalance(1L);
        double destinationBefore = db.getWalletBalance(2L);

        String reference = "TXN" + System.currentTimeMillis();

        TransferRequest request =
                new TransferRequest(
                        1L,
                        2L,
                        500.0,
                        reference);

        Response firstResponse = walletClient.transferMoney(request);
        Response secondResponse = walletClient.transferMoney(request);

        assertEquals(200, firstResponse.getStatusCode());
        assertEquals(200, secondResponse.getStatusCode());

        double sourceAfter = db.getWalletBalance(1L);
        double destinationAfter = db.getWalletBalance(2L);

        // Money should move only once
        assertEquals(sourceBefore - 500, sourceAfter);
        assertEquals(destinationBefore + 500, destinationAfter);

        assertEquals("SUCCESS",
                db.getTransferStatus(reference));

        System.out.println(firstResponse.asPrettyString());
        System.out.println(secondResponse.asPrettyString());
    }

    @Test
    public void verifyConcurrentTransfer() throws Exception {

        Runnable transferTask = () -> {

            String reference = "TXN" + System.nanoTime();

            TransferRequest request =
                    new TransferRequest(
                            1L,
                            2L,
                            7000.0,
                            reference);

            walletClient.transferMoney(request);
        };

        Thread thread1 = new Thread(transferTask);
        Thread thread2 = new Thread(transferTask);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        double sourceBalance = db.getWalletBalance(1L);
        double destinationBalance = db.getWalletBalance(2L);

        // Source balance should never be negative
        assertTrue(sourceBalance >= 0);

        // Destination should not receive more than one successful transfer
        assertTrue(destinationBalance <= 12000);

        System.out.println("Source Balance      : " + sourceBalance);
        System.out.println("Destination Balance : " + destinationBalance);
    }
    
    @Test
    public void verifyWalletNotFound() {

        String reference = "TXN" + System.currentTimeMillis();

        TransferRequest request =
                new TransferRequest(
                        999L,
                        2L,
                        100.0,
                        reference);

        Response response = walletClient.transferMoney(request);

        assertEquals(400, response.getStatusCode());
        assertTrue(response.asString().contains("Wallet not found"));

        System.out.println(response.asPrettyString());
    }
}