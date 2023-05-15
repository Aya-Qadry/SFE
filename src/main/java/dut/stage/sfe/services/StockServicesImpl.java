package dut.stage.sfe.services;

import java.util.List;

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

    @Override
    public List<Stock> findAllStocks() {
        return repository.findAll();
    }

    @Override
    public Stock findById(int id) {
        Stock s = repository.findById(id).orElse(null) ; 
        return s;
    }

    @Override
    public void deleteByid(int id) {
        repository.deleteById(id);
    }

    @Override
    public void saveAllToStcok() {
       repository.saveAll(null);
    }
    
}
