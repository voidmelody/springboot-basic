package org.prgrms.prgrmsspring.service;

import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.entity.wallet.Wallet;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.repository.user.CustomerRepository;
import org.prgrms.prgrmsspring.repository.voucher.VoucherRepository;
import org.prgrms.prgrmsspring.repository.wallet.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public WalletService(WalletRepository walletRepository, VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.walletRepository = walletRepository;
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public Wallet allocateVoucherToCustomer(UUID customerId, UUID voucherId) {
        return walletRepository.allocateVoucherToCustomer(customerId, voucherId);
    }

    public List<Voucher> findVoucherListByCustomerId(UUID customerId) {
        List<Voucher> result = new ArrayList<>();
        List<UUID> voucherIdList = walletRepository.findVoucherIdListByCustomerId(customerId);
        voucherIdList.forEach(voucherId -> voucherRepository.findById(voucherId).ifPresent(result::add));
        return result;
    }

    public void deleteVouchersByCustomerId(UUID customerId) {
        walletRepository.deleteVouchersByCustomerId(customerId);
    }

    public Customer findCustomerByVoucherId(UUID voucherId) {
        UUID customerId = walletRepository.findCustomerIdByVoucherId(voucherId)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_FOUND_CUSTOMER.getMessage()));
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_FOUND_CUSTOMER.getMessage()));
    }

}
