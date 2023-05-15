package dut.stage.sfe.services;

import java.util.List;

import dut.stage.sfe.model.Stock;

public interface StockServices {
    Stock addToStock(Stock stock);
    List<Stock> findAllStocks();
    Stock findById(int id);
    void deleteByid(int id);
    void saveAllToStcok();
}
