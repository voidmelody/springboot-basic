package org.prgrms.prgrmsspring.controller.web;

import org.prgrms.prgrmsspring.domain.VoucherType;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class VoucherWebController implements ApplicationWebController {

    private final VoucherService voucherService;

    public VoucherWebController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/add")
    public String create(@RequestParam Long amount, @RequestParam String type) {
        VoucherType voucherType = VoucherType.from(type);
        Voucher voucher = voucherType.constructVoucher(UUID.randomUUID(), amount);
        voucherService.create(voucher);
        return "redirect:/";
    }

    public Voucher update(Voucher updateVoucher) {
        return voucherService.update(updateVoucher);
    }

    public void delete(UUID deleteVoucherId) {
        voucherService.delete(deleteVoucherId);
    }

    @PostMapping("/list/delete")
    public String deleteVouchers(@RequestParam(value = "selectedVoucherIds") List<UUID> voucherIdList) {
        voucherIdList.forEach(this::delete);
        return "redirect:/list/all";
    }

    @GetMapping("/list/all")
    public String findAll(Model model) {
        List<Voucher> all = voucherService.findAll();
        model.addAttribute("vouchers", all);
        return "list-all";
    }

    @GetMapping("/voucher/{voucherId}")
    public String findById(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher";
    }

    public List<Voucher> findBetweenDate(LocalDateTime begin, LocalDateTime end) {
        return voucherService.findBetweenDate(begin, end);
    }

    public List<Voucher> findByVoucherType(int modeNum) {
        VoucherType voucherType = VoucherType.from(modeNum);
        return voucherService.findByVoucherType(voucherType);
    }
}
