package EShopping.EShopping.dataAccess;

import EShopping.EShopping.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
