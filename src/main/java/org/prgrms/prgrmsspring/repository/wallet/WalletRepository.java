package org.prgrms.prgrmsspring.repository.wallet;

import org.prgrms.prgrmsspring.entity.wallet.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    Wallet allocateVoucherToCustomer(UUID customerId, UUID voucherId);

    Optional<List<UUID>> findVoucherIdListByCustomerId(UUID customerId);

    void deleteVouchersByCustomerId(UUID customerId);

    Optional<UUID> findCustomerIdByVoucherId(UUID voucherId);
}
