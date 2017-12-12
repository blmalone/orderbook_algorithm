package com.cryptofacilities.interview;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class OrderBookManagerTest {

    @Test
    public void testAddOrderWithZeroQuantityOrder() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        Order zeroQuantityOrder = new Order("uniqueID", instrument, Side.buy, priceLevel, 0);
        orderBookManager.addOrder(zeroQuantityOrder);
        assertNull(orderBookManager.getOrdersAtLevel(instrument, Side.buy, priceLevel));
    }

    @Test
    public void testAddOrderWithNonZeroQuantityOrder() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        Order nonZeroQuantityOrder = new Order("uniqueID", instrument, Side.buy, priceLevel, 10);
        orderBookManager.addOrder(nonZeroQuantityOrder);
        assertTrue(orderBookManager.getOrdersAtLevel(instrument, Side.buy, priceLevel).size() == 1);
    }

    @Test
    public void testAddOrderWithNonExistentInstrument() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "ETH";
        long priceLevel = 100;
        Order order = new Order("uniqueID", instrument, Side.buy, priceLevel, 10);
        orderBookManager.addOrder(order);
        assertNull(orderBookManager.getOrdersAtLevel(instrument, Side.buy, priceLevel));
    }

    @Test
    public void testAddOrderAndGetAllOrdersAtLevelInOrder() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        Order order = new Order("uniqueID", instrument, Side.sell, priceLevel, 10);
        Order order1 = new Order("uniqueID1", instrument, Side.sell, priceLevel, 10);
        Order order2 = new Order("uniqueID2", instrument, Side.sell, priceLevel, 10);
        Order order3 = new Order("uniqueID3", instrument, Side.sell, priceLevel, 10);
        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        final List<Order> ordersAtLevel = orderBookManager.getOrdersAtLevel(instrument, Side.sell, priceLevel);
        assertTrue(ordersAtLevel.get(0).equals(order));
        assertTrue(ordersAtLevel.get(1).equals(order1));
        assertTrue(ordersAtLevel.get(2).equals(order2));
        assertTrue(ordersAtLevel.get(3).equals(order3));
    }

    @Test
    public void testAddOrderWithNullOrder() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        orderBookManager.addOrder(null);
        assertTrue(orderBookManager.getBestPrice(instrument, Side.buy) == 0);
        assertTrue(orderBookManager.getBestPrice(instrument, Side.sell) == 0);
    }

    @Test
    public void testDeleteOrderSuccess() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        String id = "uniqueID";
        String id1 = "uniqueID1";
        Order order = new Order(id, instrument, Side.buy, priceLevel, 10);
        Order order1 = new Order(id1, instrument, Side.sell, priceLevel, 10);
        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        assertTrue(orderBookManager.getOrderNumAtLevel(instrument, Side.buy, priceLevel) == 1);
        assertTrue(orderBookManager.getOrderNumAtLevel(instrument, Side.sell, priceLevel) == 1);
        orderBookManager.deleteOrder(id);
        orderBookManager.deleteOrder(id1);
        assertTrue(orderBookManager.getOrderNumAtLevel(instrument, Side.buy, priceLevel) == 0);
        assertTrue(orderBookManager.getOrderNumAtLevel(instrument, Side.sell, priceLevel) == 0);
    }

    @Test
    public void testModifyOrderWithIncreaseInQuantitySuccess() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        Order order = new Order("uniqueID", instrument, Side.buy, priceLevel, 10);
        Order order1 = new Order("uniqueID1", instrument, Side.buy, priceLevel, 10);
        Order order2 = new Order("uniqueID2", instrument, Side.buy, priceLevel, 10);
        Order order3 = new Order("uniqueID3", instrument, Side.buy, priceLevel, 10);
        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        orderBookManager.modifyOrder("uniqueID", 11);

        final List<Order> ordersAtLevelAfter = orderBookManager.getOrdersAtLevel(instrument, Side.buy, priceLevel);

        assertTrue(ordersAtLevelAfter.get(ordersAtLevelAfter.size()-1).getOrderId().equals("uniqueID"));

    }

    @Test
    public void testModifyOrderWithDecreaseInQuantitySuccess() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        Order order = new Order("uniqueID", instrument, Side.buy, priceLevel, 10);
        Order order1 = new Order("uniqueID1", instrument, Side.buy, priceLevel, 10);
        Order order2 = new Order("uniqueID2", instrument, Side.buy, priceLevel, 10);
        Order order3 = new Order("uniqueID3", instrument, Side.buy, priceLevel, 10);
        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        orderBookManager.modifyOrder("uniqueID", 9);

        final List<Order> ordersAtLevelAfter = orderBookManager.getOrdersAtLevel(instrument, Side.buy, priceLevel);

        assertTrue(ordersAtLevelAfter.get(0).getOrderId().equals("uniqueID"));

    }

    @Test
    public void testGetAllOrdersAtSideInOrderSuccess() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        long priceLevel1 = 101;
        Order order = new Order("uniqueID", instrument, Side.buy, priceLevel, 10);
        Order order1 = new Order("uniqueID1", instrument, Side.buy, priceLevel, 10);
        Order order2 = new Order("uniqueID2", instrument, Side.buy, priceLevel, 10);
        Order order3 = new Order("uniqueID3", instrument, Side.buy, priceLevel, 10);

        Order order4 = new Order("uniqueID4", instrument, Side.buy, priceLevel1, 10);
        Order order5 = new Order("uniqueID5", instrument, Side.buy, priceLevel1, 10);
        Order order6 = new Order("uniqueID6", instrument, Side.buy, priceLevel1, 10);
        Order order7 = new Order("uniqueID7", instrument, Side.buy, priceLevel1, 10);

        Order order8 = new Order("uniqueID8", instrument, Side.sell, priceLevel, 10);

        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        orderBookManager.addOrder(order4);
        orderBookManager.addOrder(order5);
        orderBookManager.addOrder(order6);
        orderBookManager.addOrder(order7);

        orderBookManager.addOrder(order8);

        final List<Order> ordersAtSide = orderBookManager.getOrdersAtSide(instrument, Side.buy);
        final List<Order> ordersAtSide1 = orderBookManager.getOrdersAtSide(instrument, Side.sell);

        assertTrue(ordersAtSide.size() == 8);
        assertTrue(ordersAtSide.get(0).getOrderId().equals("uniqueID"));
        assertTrue(ordersAtSide.get(7).getOrderId().equals("uniqueID7"));
        assertTrue(ordersAtSide1.size() == 1);
    }

    @Test
    public void testThatQuantityAtLevelHasIncreasedAfterModify() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        Order order = new Order("uniqueID", instrument, Side.sell, priceLevel, 10);
        Order order1 = new Order("uniqueID1", instrument, Side.sell, priceLevel, 10);
        Order order2 = new Order("uniqueID2", instrument, Side.sell, priceLevel, 10);
        Order order3 = new Order("uniqueID3", instrument, Side.sell, priceLevel, 10);

        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        long oldLevelQuantity = orderBookManager.getTotalQuantityAtLevel(instrument, Side.sell, priceLevel);

        orderBookManager.modifyOrder("uniqueID2", 20);

        long newLevelQuantity = orderBookManager.getTotalQuantityAtLevel(instrument, Side.sell, priceLevel);

        assertTrue((oldLevelQuantity + 10) == newLevelQuantity);
    }

    @Test
    public void testThatQuantityAtLevelHasDecreasedAfterModify() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        Order order = new Order("uniqueID", instrument, Side.buy, priceLevel, 10);
        Order order1 = new Order("uniqueID1", instrument, Side.buy, priceLevel, 10);
        Order order2 = new Order("uniqueID2", instrument, Side.buy, priceLevel, 10);
        Order order3 = new Order("uniqueID3", instrument, Side.buy, priceLevel, 10);

        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        long oldLevelQuantity = orderBookManager.getTotalQuantityAtLevel(instrument, Side.buy, priceLevel);

        orderBookManager.modifyOrder("uniqueID2", 5);

        long newLevelQuantity = orderBookManager.getTotalQuantityAtLevel(instrument, Side.buy, priceLevel);

        assertTrue((oldLevelQuantity - 5) == newLevelQuantity);
    }

    @Test
    public void testThatVolumeAtLevelHasDecreasedAfterModify() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        Order order = new Order("uniqueID", instrument, Side.sell, priceLevel, 10);
        Order order1 = new Order("uniqueID1", instrument, Side.sell, priceLevel, 10);
        Order order2 = new Order("uniqueID2", instrument, Side.sell, priceLevel, 10);
        Order order3 = new Order("uniqueID3", instrument, Side.sell, priceLevel, 10);

        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        long oldLevelVolume = orderBookManager.getTotalVolumeAtLevel(instrument, Side.sell, priceLevel);

        orderBookManager.modifyOrder("uniqueID3", 5); // (100*10)-(100*5) = 500

        long newLevelVolume = orderBookManager.getTotalVolumeAtLevel(instrument, Side.sell, priceLevel);

        assertTrue((oldLevelVolume - 500) == newLevelVolume);
    }

    @Test
    public void testThatVolumeAtLevelHasIncreasedAfterModify() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        Order order = new Order("uniqueID", instrument, Side.buy, priceLevel, 10);
        Order order1 = new Order("uniqueID1", instrument, Side.buy, priceLevel, 10);
        Order order2 = new Order("uniqueID2", instrument, Side.buy, priceLevel, 10);
        Order order3 = new Order("uniqueID3", instrument, Side.buy, priceLevel, 10);

        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        long oldLevelVolume = orderBookManager.getTotalVolumeAtLevel(instrument, Side.buy, priceLevel);

        orderBookManager.modifyOrder("uniqueID3", 15); // (100*15)-(100*10) = 500

        long newLevelVolume = orderBookManager.getTotalVolumeAtLevel(instrument, Side.buy, priceLevel);

        assertTrue((oldLevelVolume + 500) == newLevelVolume);
    }

    @Test
    public void testThatVolumeAtSideHasIncreasedAfterAddOrder() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        long priceLevel1 = 101;
        Order order = new Order("uniqueID", instrument, Side.buy, priceLevel, 10);
        Order order1 = new Order("uniqueID1", instrument, Side.buy, priceLevel, 10);
        Order order2 = new Order("uniqueID2", instrument, Side.buy, priceLevel, 10);
        Order order3 = new Order("uniqueID3", instrument, Side.buy, priceLevel, 10);

        Order order4 = new Order("uniqueID4", instrument, Side.buy, priceLevel1, 10);
        Order order5 = new Order("uniqueID5", instrument, Side.buy, priceLevel1, 10);
        Order order6 = new Order("uniqueID6", instrument, Side.buy, priceLevel1, 10);
        Order order7 = new Order("uniqueID7", instrument, Side.buy, priceLevel1, 10);

        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        orderBookManager.addOrder(order4);
        orderBookManager.addOrder(order5);
        orderBookManager.addOrder(order6);
        orderBookManager.addOrder(order7);

        long oldLevelVolume = orderBookManager.getTotalVolumeAtSide(instrument, Side.buy);

        orderBookManager.modifyOrder("uniqueID3", 15); // (100*15)-(100*10) = 500

        long newLevelVolume = orderBookManager.getTotalVolumeAtSide(instrument, Side.buy);

        assertTrue((oldLevelVolume + 500) == newLevelVolume);
    }

    @Test
    public void testThatVolumeAtSideHasDecreasedAfterAddOrder() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        long priceLevel1 = 101;
        Order order = new Order("uniqueID", instrument, Side.sell, priceLevel, 10);
        Order order1 = new Order("uniqueID1", instrument, Side.sell, priceLevel, 10);
        Order order2 = new Order("uniqueID2", instrument, Side.sell, priceLevel, 10);
        Order order3 = new Order("uniqueID3", instrument, Side.sell, priceLevel, 10);

        Order order4 = new Order("uniqueID4", instrument, Side.sell, priceLevel1, 10);
        Order order5 = new Order("uniqueID5", instrument, Side.sell, priceLevel1, 10);
        Order order6 = new Order("uniqueID6", instrument, Side.sell, priceLevel1, 10);
        Order order7 = new Order("uniqueID7", instrument, Side.sell, priceLevel1, 10);

        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        orderBookManager.addOrder(order4);
        orderBookManager.addOrder(order5);
        orderBookManager.addOrder(order6);
        orderBookManager.addOrder(order7);

        long oldLevelVolume = orderBookManager.getTotalVolumeAtSide(instrument, Side.sell);

        orderBookManager.modifyOrder("uniqueID7", 5); // (101*10)-(101*5) = 505

        long newLevelVolume = orderBookManager.getTotalVolumeAtSide(instrument, Side.sell);

        assertTrue((oldLevelVolume - 505) == newLevelVolume);
    }

    @Test
    public void testThatTotalQuantityAtSideHasIncreasedAfterAddOrder() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        long priceLevel1 = 101;
        Order order = new Order("uniqueID", instrument, Side.buy, priceLevel, 20);
        Order order1 = new Order("uniqueID1", instrument, Side.buy, priceLevel, 20);
        Order order2 = new Order("uniqueID2", instrument, Side.buy, priceLevel, 20);
        Order order3 = new Order("uniqueID3", instrument, Side.buy, priceLevel, 20);

        Order order4 = new Order("uniqueID4", instrument, Side.buy, priceLevel1, 5);
        Order order5 = new Order("uniqueID5", instrument, Side.buy, priceLevel1, 5);
        Order order6 = new Order("uniqueID6", instrument, Side.buy, priceLevel1, 5);
        Order order7 = new Order("uniqueID7", instrument, Side.buy, priceLevel1, 5);

        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        orderBookManager.addOrder(order4);
        orderBookManager.addOrder(order5);
        orderBookManager.addOrder(order6);
        orderBookManager.addOrder(order7);

        long oldTotalQuantity = orderBookManager.getTotalQuantityAtSide(instrument, Side.buy);
        assertTrue((oldTotalQuantity == 100));

        orderBookManager.addOrder(new Order("uniqueID8", instrument, Side.buy, 102, 100));

        long newTotalQuantity = orderBookManager.getTotalQuantityAtSide(instrument, Side.buy);

        assertTrue(newTotalQuantity == 200);
    }

    @Test
    public void testThatTotalQuantityAtSideHasDecreasedAfterRemoveOrder() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        long priceLevel1 = 101;
        Order order = new Order("uniqueID", instrument, Side.sell, priceLevel, 20);
        Order order1 = new Order("uniqueID1", instrument, Side.sell, priceLevel, 20);
        Order order2 = new Order("uniqueID2", instrument, Side.sell, priceLevel, 20);
        Order order3 = new Order("uniqueID3", instrument, Side.sell, priceLevel, 20);

        Order order4 = new Order("uniqueID4", instrument, Side.sell, priceLevel1, 5);
        Order order5 = new Order("uniqueID5", instrument, Side.sell, priceLevel1, 5);
        Order order6 = new Order("uniqueID6", instrument, Side.sell, priceLevel1, 5);
        Order order7 = new Order("uniqueID7", instrument, Side.sell, priceLevel1, 5);

        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        orderBookManager.addOrder(order4);
        orderBookManager.addOrder(order5);
        orderBookManager.addOrder(order6);
        orderBookManager.addOrder(order7);

        long oldTotalQuantity = orderBookManager.getTotalQuantityAtSide(instrument, Side.sell);
        assertTrue((oldTotalQuantity == 100));

        orderBookManager.deleteOrder(order1.getOrderId());

        long newTotalQuantity = orderBookManager.getTotalQuantityAtSide(instrument, Side.sell);

        assertTrue(newTotalQuantity == 80);
    }

    @Test
    public void testThatOrderNumberAtSideHasIncreasedAfterAddOrder() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        long priceLevel1 = 101;
        Order order = new Order("uniqueID", instrument, Side.sell, priceLevel, 20);
        Order order1 = new Order("uniqueID1", instrument, Side.sell, priceLevel, 20);
        Order order2 = new Order("uniqueID2", instrument, Side.sell, priceLevel, 20);
        Order order3 = new Order("uniqueID3", instrument, Side.sell, priceLevel, 20);

        Order order4 = new Order("uniqueID4", instrument, Side.sell, priceLevel1, 5);
        Order order5 = new Order("uniqueID5", instrument, Side.sell, priceLevel1, 5);
        Order order6 = new Order("uniqueID6", instrument, Side.sell, priceLevel1, 5);
        Order order7 = new Order("uniqueID7", instrument, Side.sell, priceLevel1, 5);

        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        orderBookManager.addOrder(order4);
        orderBookManager.addOrder(order5);
        orderBookManager.addOrder(order6);
        orderBookManager.addOrder(order7);

        long oldNumberOfOrders = orderBookManager.getOrderNumAtSide(instrument, Side.sell);
        assertTrue((oldNumberOfOrders == 8));

        orderBookManager.addOrder(new Order("uniqueID8", instrument, Side.sell, 103, 5));

        long newNumberOfOrders = orderBookManager.getOrderNumAtSide(instrument, Side.sell);

        assertTrue(newNumberOfOrders == 9);
    }

    @Test
    public void testThatOrderNumberAtSideHasDecreasedAfterRemoveOrder() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        long priceLevel1 = 101;
        Order order = new Order("uniqueID", instrument, Side.buy, priceLevel, 20);
        Order order1 = new Order("uniqueID1", instrument, Side.buy, priceLevel, 20);
        Order order2 = new Order("uniqueID2", instrument, Side.buy, priceLevel, 20);
        Order order3 = new Order("uniqueID3", instrument, Side.buy, priceLevel, 20);

        Order order4 = new Order("uniqueID4", instrument, Side.buy, priceLevel1, 5);
        Order order5 = new Order("uniqueID5", instrument, Side.buy, priceLevel1, 5);
        Order order6 = new Order("uniqueID6", instrument, Side.buy, priceLevel1, 5);
        Order order7 = new Order("uniqueID7", instrument, Side.buy, priceLevel1, 5);

        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        orderBookManager.addOrder(order4);
        orderBookManager.addOrder(order5);
        orderBookManager.addOrder(order6);
        orderBookManager.addOrder(order7);

        long oldNumberOfOrders = orderBookManager.getOrderNumAtSide(instrument, Side.buy);
        assertTrue((oldNumberOfOrders == 8));

        orderBookManager.deleteOrder(order.getOrderId());

        long newNumberOfOrders = orderBookManager.getOrderNumAtSide(instrument, Side.buy);

        assertTrue(newNumberOfOrders == 7);
    }

    @Test
    public void testGetBestPriceForBid() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        long priceLevel1 = 101;
        long priceLevel2 = 102;
        long priceLevel3 = 103;

        Order order = new Order("uniqueID", instrument, Side.buy, priceLevel, 20);
        Order order1 = new Order("uniqueID1", instrument, Side.buy, priceLevel1, 20);
        Order order2 = new Order("uniqueID2", instrument, Side.buy, priceLevel2, 20);
        Order order3 = new Order("uniqueID3", instrument, Side.buy, priceLevel3, 20);

        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        long bestPrice = orderBookManager.getBestPrice(instrument, Side.buy);
        assertTrue(bestPrice == priceLevel3);
    }

    @Test
    public void testGetBestPriceForAsk() {
        OrderBookManagerImpl orderBookManager = new OrderBookManagerImpl();
        String instrument = "BTC";
        long priceLevel = 100;
        long priceLevel1 = 101;
        long priceLevel2 = 102;
        long priceLevel3 = 103;

        Order order = new Order("uniqueID", instrument, Side.sell, priceLevel, 20);
        Order order1 = new Order("uniqueID1", instrument, Side.sell, priceLevel1, 20);
        Order order2 = new Order("uniqueID2", instrument, Side.sell, priceLevel2, 20);
        Order order3 = new Order("uniqueID3", instrument, Side.sell, priceLevel3, 20);

        orderBookManager.addOrder(order);
        orderBookManager.addOrder(order1);
        orderBookManager.addOrder(order2);
        orderBookManager.addOrder(order3);

        long bestPrice = orderBookManager.getBestPrice(instrument, Side.sell);
        assertTrue(bestPrice == priceLevel);
    }


}
