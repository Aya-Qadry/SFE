package dut.stage.sfe.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.StockRepository;
import dut.stage.sfe.model.Stock;

@Service
public class StockServicesImpl implements StockServices{

    @Autowired
    StockRepository repository ;

    @Override
    public Stock addToStock(Stock stock) {
        return repository.save(stock);
    }
    
}
