package EShopping.EShopping.service;

import EShopping.EShopping.dataAccess.BillRepository;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }
}
