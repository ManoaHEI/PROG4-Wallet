package com.prog4.wallet.service;

import com.prog4.wallet.models.Transfer;
import com.prog4.wallet.repository.AccountRepository;
import com.prog4.wallet.repository.TransferRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

@AllArgsConstructor
@Service
public class TransferService {

    private TransferRepository transferRepository;
    private AccountRepository accountRepository;

    public static String generateUniqueRef() {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final int refLength = 8;

        SecureRandom random = new SecureRandom();
        StringBuilder ref = new StringBuilder(refLength);

        for (int i = 0; i < refLength; i++) {
            int randomIndex = random.nextInt(characters.length());
            ref.append(characters.charAt(randomIndex));
        }
        return ref.toString();
    }

    @Transactional
    public Transfer receiveTransfer(long id, Transfer transfer) {
        try {
            transferRepository.doTransfer(transfer);

            BigDecimal transferAmount = transfer.getTransferAmount();
            BigDecimal accountBalance = accountRepository.getAccountBalance();

            BigDecimal newAccountBalance = transferAmount + accountBalance;

            accountRepository.setAccountBalance(newAccountBalance);

            return transfer;

        } catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public String sendTransfer(Transfer transfer) {
        try {
            String uniqueRef = generateUniqueRef();

            transferRepository.doTransfer(transfer);
            transferRepository.updateTransferRef(uniqueRef);

            long accountId = transferRepository.getTransferAccountId(uniqueRef);
            long receiverId = transferRepository.getTransferReceiverId(uniqueRef);

            BigDecimal senderBalance = accountRepository.getAccountBalance(accountId);
            BigDecimal receiverBalance = accountRepository.getAccountBalance(receiverId);
            BigDecimal transferAmount = transferRepository.getTransferAmount(uniqueRef);

            Timestamp effectiveDate = transferRepository.getEffectiveDate(uniqueRef);

            Date currentDate = new Date();
            Timestamp currentTimestamp = new Timestamp(currentDate.getTime());

            if (
                    currentTimestamp.after(effectiveDate) &&
                    transferRepository.isCanceled(uniqueRef) == false
            ) {
                if (senderBalance >= transferAmount && transferAmount != 0) {
                    senderBalance  = senderBalance - transferAmount;
                    receiverBalance = receiverBalance + transferAmount;

                    accountRepository.setAccountBalance(accountId, senderBalance);
                    accountRepository.setAccountBalance(receiverId, receiverBalance);

                    return "Transfert from " + accountId + " to " + receiverId + " done!";
                } else if (senderBalance < transferAmount && transferAmount != 0) {
                    return "Insufficient balance";
                } else {
                    return "Invalid amount";
                }
            } else {
                return "Transfer amount is not valid !";
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public BigDecimal getTransferAmount(long accountId) {
        try {
            return accountRepository.getAccountBalance(accountId);
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void setTransferCanceledStatus(boolean status, String transferRef) {
        try {
            transferRepository.updateTransferStatus(transferRef, status);
        }  catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}