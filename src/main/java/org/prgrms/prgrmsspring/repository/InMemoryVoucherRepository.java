package org.prgrms.prgrmsspring.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.prgrmsspring.entity.Voucher;

public class InMemoryVoucherRepository implements VoucherRepository{
    private static final Map<UUID, Voucher> store = new ConcurrentHashMap<>();
    @Override
    public Voucher insert(Voucher voucher) {
        store.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(store.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
       return store.values().stream().toList();
    }
}
