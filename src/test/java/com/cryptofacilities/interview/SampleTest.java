package com.cryptofacilities.interview;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by CF-8 on 6/27/2017.
 */
public class SampleTest {

    @Test
    public void testBestBidPrice() {
        // create order book
        OrderBookManager orderBookManager = new OrderBookManagerImpl();

        // create order
        //Instrument - Vodafone.London
        Order buy = new Order("order1", "BTC", Side.buy, 200, 10 );

        // send order
        orderBookManager.addOrder( buy );

        // check that best price is 200
        long expectedPrice = 200;
        long actualPrice = orderBookManager.getBestPrice("BTC", Side.buy );
        assertEquals( "Best bid price is 200", expectedPrice, actualPrice );
    }
}
