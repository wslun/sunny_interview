package com.gigaspaces;

import com.gigaspaces.model.Customer;
import com.gigaspaces.model.Order;
import com.j_spaces.core.IJSpace;
import com.j_spaces.core.client.SQLQuery;

import org.junit.Before;
import org.junit.Test;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import java.util.Date;
import java.util.ArrayList;
/**
 * Created by kobi on 12/10/14.
 */
public class NonEQJoin {

    public static Logger logger = Logger.getLogger("NonEQJoin");
    public static final String url = "/./mySpace";

    static IJSpace space = null;
    static GigaSpace gigaSpace = null;

    @Before
    public void before() throws Exception {
        space = new UrlSpaceConfigurer(url).space();
        gigaSpace = new GigaSpaceConfigurer(space).gigaSpace();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

//        +---------+------------+----------------+------------+-----------+------------+-----------------+
//        | ORD_NUM | ORD_AMOUNT | ADVANCE_AMOUNT | ORD_DATE   | CUST_CODE | AGENT_CODE | ORD_DESCRIPTION |
//        +---------+------------+----------------+------------+-----------+------------+-----------------+
//        |  200100 |    1000.00 |         600.00 | 2008-01-08 | C00015    | A003       |                 |
//        |  200110 |    3000.00 |         500.00 | 2008-04-15 | C00019    | A010       |                 |
//        |  200107 |    4500.00 |         900.00 | 2008-08-30 | C00007    | A010       |                 |
//        |  200112 |    2000.00 |         400.00 | 2008-05-30 | C00016    | A007       |                 |
//        |  200113 |    4000.00 |         600.00 | 2008-06-10 | C00022    | A002       |                 |
//        |  200102 |    2000.00 |         300.00 | 2008-05-25 | C00012    | A012       |                 |
//        |  200114 |    3500.00 |        2000.00 | 2008-08-15 | C00002    | A008       |                 |
//        |  200122 |    2500.00 |         400.00 | 2008-09-16 | C00003    | A004       |                 |
//        |  200118 |     500.00 |         100.00 | 2008-07-20 | C00023    | A006       |                 |
//        |  200119 |    4000.00 |         700.00 | 2008-09-16 | C00007    | A010       |                 |
//        |  200121 |    1500.00 |         600.00 | 2008-09-23 | C00008    | A004       |                 |
//        |  200130 |    2500.00 |         400.00 | 2008-07-30 | C00025    | A011       |                 |
//        |  200134 |    4200.00 |        1800.00 | 2008-09-25 | C00004    | A005       |                 |
//        |  200115 |    2000.00 |        1200.00 | 2008-02-08 | C00013    | A013       |                 |
//        |  200108 |    4000.00 |         600.00 | 2008-02-15 | C00008    | A004       |                 |
//        |  200103 |    1500.00 |         700.00 | 2008-05-15 | C00021    | A005       |                 |
//        |  200105 |    2500.00 |         500.00 | 2008-07-18 | C00025    | A011       |                 |
//        |  200109 |    3500.00 |         800.00 | 2008-07-30 | C00011    | A010       |                 |
//        |  200101 |    3000.00 |        1000.00 | 2008-07-15 | C00001    | A008       |                 |
//        |  200111 |    1000.00 |         300.00 | 2008-07-10 | C00020    | A008       |                 |
//        +---------+------------+----------------+------------+-----------+------------+-----------------+

        Order[] orders = getOrders(formatter);

        gigaSpace.writeMultiple(orders);

//        +-----------+-------------+-------------+--------------+--------------+-------+-------------+-------------+-------------+---------------+--------------+------------+
//        |CUST_CODE  | CUST_NAME   | CUST_CITY   | WORKING_AREA | CUST_COUNTRY | GRADE | OPENING_AMT | RECEIVE_AMT | PAYMENT_AMT |OUTSTANDING_AMT| PHONE_NO     | AGENT_CODE |
//        +-----------+-------------+-------------+--------------+--------------+-------+-------------+-------------+-------------+---------------+--------------+------------+
//        | C00013    | Holmes      | London      | London       | UK           |     2 |     6000.00 |     5000.00 |     7000.00 |       4000.00 | BBBBBBB      | A003       |
//        | C00001    | Micheal     | New York    | New York     | USA          |     2 |     3000.00 |     5000.00 |     2000.00 |       6000.00 | CCCCCCC      | A008       |
//        | C00020    | Albert      | New York    | New York     | USA          |     3 |     5000.00 |     7000.00 |     6000.00 |       6000.00 | BBBBSBB      | A008       |
//        | C00025    | Ravindran   | Bangalore   | Bangalore    | India        |     2 |     5000.00 |     7000.00 |     4000.00 |       8000.00 | AVAVAVA      | A011       |
//        | C00024    | Cook        | London      | London       | UK           |     2 |     4000.00 |     9000.00 |     7000.00 |       6000.00 | FSDDSDF      | A006       |
//        | C00015    | Stuart      | London      | London       | UK           |     1 |     6000.00 |     8000.00 |     3000.00 |      11000.00 | GFSGERS      | A003       |
//        | C00002    | Bolt        | New York    | New York     | USA          |     3 |     5000.00 |     7000.00 |     9000.00 |       3000.00 | DDNRDRH      | A008       |
//        | C00018    | Fleming     | Brisban     | Brisban      | Australia    |     2 |     7000.00 |     7000.00 |     9000.00 |       5000.00 | NHBGVFC      | A005       |
//        | C00021    | Jacks       | Brisban     | Brisban      | Australia    |     1 |     7000.00 |     7000.00 |     7000.00 |       7000.00 | WERTGDF      | A005       |
//        | C00019    | Yearannaidu | Chennai     | Chennai      | India        |     1 |     8000.00 |     7000.00 |     7000.00 |       8000.00 | ZZZZBFV      | A010       |
//        | C00005    | Sasikant    | Mumbai      | Mumbai       | India        |     1 |     7000.00 |    11000.00 |     7000.00 |      11000.00 | 147-25896312 | A002       |
//        | C00007    | Ramanathan  | Chennai     | Chennai      | India        |     1 |     7000.00 |    11000.00 |     9000.00 |       9000.00 | GHRDWSD      | A010       |
//        | C00022    | Avinash     | Mumbai      | Mumbai       | India        |     2 |     7000.00 |    11000.00 |     9000.00 |       9000.00 | 113-12345678 | A002       |
//        | C00004    | Winston     | Brisban     | Brisban      | Australia    |     1 |     5000.00 |     8000.00 |     7000.00 |       6000.00 | AAAAAAA      | A005       |
//        | C00023    | Karl        | London      | London       | UK           |     0 |     4000.00 |     6000.00 |     7000.00 |       3000.00 | AAAABAA      | A006       |
//        | C00006    | Shilton     | Torento     | Torento      | Canada       |     1 |    10000.00 |     7000.00 |     6000.00 |      11000.00 | DDDDDDD      | A004       |
//        | C00010    | Charles     | Hampshair   | Hampshair    | UK           |     3 |     6000.00 |     4000.00 |     5000.00 |       5000.00 | MMMMMMM      | A009       |
//        | C00017    | Srinivas    | Bangalore   | Bangalore    | India        |     2 |     8000.00 |     4000.00 |     3000.00 |       9000.00 | AAAAAAB      | A007       |
//        | C00012    | Steven      | San Jose    | San Jose     | USA          |     1 |     5000.00 |     7000.00 |     9000.00 |       3000.00 | KRFYGJK      | A012       |
//        | C00008    | Karolina    | Torento     | Torento      | Canada       |     1 |     7000.00 |     7000.00 |     9000.00 |       5000.00 | HJKORED      | A004       |
//        +-----------+-------------+-------------+--------------+--------------+-------+-------------+-------------+-------------+---------------+--------------+------------+

        Customer[] customers = getCustomers();

        gigaSpace.writeMultiple(customers);
    }

    @Test
    public void test(){
//TODO write the following SQL query eith XAP SQL QUERY - http://docs.gigaspaces.com/xap100net/query-sql.html
//        SELECT a.ord_num,a.ord_amount,b.cust_name,b.working_area
//        FROM orders a,customer b
//        WHERE a.ord_amount
//        BETWEEN b.opening_amt AND b.opening_amt;
        space = new UrlSpaceConfigurer(url).space();
        gigaSpace = new GigaSpaceConfigurer(space).gigaSpace();

        SQLQuery<Customer> qCustomer = new SQLQuery<Customer>(Customer.class, "");
        Customer[] cResults =  gigaSpace.readMultiple(qCustomer);
        SQLQuery<Order> qOrder = new SQLQuery<Order>(Order.class, "ORDER BY orderAmount");
        Order[] oResults =  gigaSpace.readMultiple(qOrder);
        
        ArrayList<EQJoinResult> results = new ArrayList<EQJoinResult>();
        for(int i=0; i<cResults.length; i++)
        {
            int m_openingAmt = ((Integer)(cResults[i].getOpeningAmt())).intValue();
            for(int j=0; j < oResults.length; j++)
            {
                int m_orderAmount = ((Integer)(oResults[j].getOrderAmount())).intValue();
	            if( m_openingAmt >= m_orderAmount && m_openingAmt <= m_orderAmount) // emulate Between
	            {
	            	EQJoinResult m_result = new EQJoinResult();
	            	m_result.setOrderNum(oResults[j].getOrderNum());
	            	m_result.setOrderAmount(oResults[j].getOrderAmount());
	            	m_result.setCustomerName(cResults[i].getCustomerName());
	            	m_result.setWorkingArea(cResults[i].getWorkingArea());
	            	results.add(m_result);
	            }
	            if( m_openingAmt < m_orderAmount )
	            {
	            	break;
	            }
            }
        }

        for (int i = 0; i < results.size(); i++)
        {
      	   EQJoinResult item = results.get(i);
      	   System.out.println(item.getOrderNum() + "," + item.getOrderAmount() + "," + item.getCustomerName() + "," + item.getWorkingArea());
        }    
    }

    private static Customer[] getCustomers() {
        Customer[] customers = new Customer[20];

        Customer customer = new Customer();
        customer.setCustomerCode("c00013");
        customer.setCustomerName("Holmes");
        customer.setCustomerCity("London");
        customer.setWorkingArea("London");
        customer.setCustomerCountry("UK");
        customer.setGrade(2);
        customer.setOpeningAmt(6000);
        customer.setReceiveAmt(5000);
        customer.setPaymentAmt(7000);
        customer.setOutstandingAmt(4000);
        customer.setPhoneNo("BBBBBBB");
        customer.setAgentCode("A003");
        customers[0] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00001");
        customer.setCustomerName("Michael");
        customer.setCustomerCity("New York");
        customer.setWorkingArea("New York");
        customer.setCustomerCountry("USA");
        customer.setGrade(2);
        customer.setOpeningAmt(3000);
        customer.setReceiveAmt(5000);
        customer.setPaymentAmt(2000);
        customer.setOutstandingAmt(6000);
        customer.setPhoneNo("CCCCCCC");
        customer.setAgentCode("A008");
        customers[1] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00020");
        customer.setCustomerName("Albert");
        customer.setCustomerCity("New York");
        customer.setWorkingArea("New York");
        customer.setCustomerCountry("USA");
        customer.setGrade(3);
        customer.setOpeningAmt(5000);
        customer.setReceiveAmt(7000);
        customer.setPaymentAmt(6000);
        customer.setOutstandingAmt(6000);
        customer.setPhoneNo("BBBBSBB");
        customer.setAgentCode("A008");
        customers[2] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00025");
        customer.setCustomerName("Ravindran");
        customer.setCustomerCity("Bangalore");
        customer.setWorkingArea("Bangalore");
        customer.setCustomerCountry("India");
        customer.setGrade(2);
        customer.setOpeningAmt(5000);
        customer.setReceiveAmt(7000);
        customer.setPaymentAmt(4000);
        customer.setOutstandingAmt(8000);
        customer.setPhoneNo("AVAVAVA");
        customer.setAgentCode("A011");
        customers[3] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00024");
        customer.setCustomerName("Cook");
        customer.setCustomerCity("London");
        customer.setWorkingArea("London");
        customer.setCustomerCountry("UK");
        customer.setGrade(2);
        customer.setOpeningAmt(4000);
        customer.setReceiveAmt(9000);
        customer.setPaymentAmt(7000);
        customer.setOutstandingAmt(6000);
        customer.setPhoneNo("FSDDSDF");
        customer.setAgentCode("A006");
        customers[4] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00015");
        customer.setCustomerName("Stuart");
        customer.setCustomerCity("London");
        customer.setWorkingArea("London");
        customer.setCustomerCountry("UK");
        customer.setGrade(1);
        customer.setOpeningAmt(6000);
        customer.setReceiveAmt(8000);
        customer.setPaymentAmt(3000);
        customer.setOutstandingAmt(11000);
        customer.setPhoneNo("GFSGERS");
        customer.setAgentCode("A003");
        customers[5] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00002");
        customer.setCustomerName("Bolt");
        customer.setCustomerCity("New York");
        customer.setWorkingArea("New York");
        customer.setCustomerCountry("USA");
        customer.setGrade(3);
        customer.setOpeningAmt(5000);
        customer.setReceiveAmt(7000);
        customer.setPaymentAmt(9000);
        customer.setOutstandingAmt(3000);
        customer.setPhoneNo("DDNRDRH");
        customer.setAgentCode("A008");
        customers[6] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00018");
        customer.setCustomerName("Fleming");
        customer.setCustomerCity("Brisban");
        customer.setWorkingArea("Brisban");
        customer.setCustomerCountry("Australia");
        customer.setGrade(2);
        customer.setOpeningAmt(7000);
        customer.setReceiveAmt(7000);
        customer.setPaymentAmt(9000);
        customer.setOutstandingAmt(5000);
        customer.setPhoneNo("NHBGVFC");
        customer.setAgentCode("A005");
        customers[7] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00021");
        customer.setCustomerName("Jacks");
        customer.setCustomerCity("Brisban");
        customer.setWorkingArea("Brisban");
        customer.setCustomerCountry("Australia");
        customer.setGrade(1);
        customer.setOpeningAmt(7000);
        customer.setReceiveAmt(7000);
        customer.setPaymentAmt(7000);
        customer.setOutstandingAmt(7000);
        customer.setPhoneNo("WERTGDF");
        customer.setAgentCode("A005");
        customers[8] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00019");
        customer.setCustomerName("Yearannaidu");
        customer.setCustomerCity("Chennai");
        customer.setWorkingArea("Chennai");
        customer.setCustomerCountry("India");
        customer.setGrade(1);
        customer.setOpeningAmt(8000);
        customer.setReceiveAmt(7000);
        customer.setPaymentAmt(7000);
        customer.setOutstandingAmt(8000);
        customer.setPhoneNo("ZZZZBFV");
        customer.setAgentCode("A010");
        customers[9] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00005");
        customer.setCustomerName("Sasikant");
        customer.setCustomerCity("Mumbai");
        customer.setWorkingArea("Mumbai");
        customer.setCustomerCountry("India");
        customer.setGrade(1);
        customer.setOpeningAmt(7000);
        customer.setReceiveAmt(11000);
        customer.setPaymentAmt(7000);
        customer.setOutstandingAmt(11000);
        customer.setPhoneNo("147-25896312");
        customer.setAgentCode("A002");
        customers[10] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00007");
        customer.setCustomerName("Ramanathan");
        customer.setCustomerCity("Chennai");
        customer.setWorkingArea("Chennai");
        customer.setCustomerCountry("India");
        customer.setGrade(1);
        customer.setOpeningAmt(7000);
        customer.setReceiveAmt(11000);
        customer.setPaymentAmt(9000);
        customer.setOutstandingAmt(9000);
        customer.setPhoneNo("GHRDWSD");
        customer.setAgentCode("A010");
        customers[11] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00022");
        customer.setCustomerName("Avinash");
        customer.setCustomerCity("Mumbai");
        customer.setWorkingArea("Mumbai");
        customer.setCustomerCountry("India");
        customer.setGrade(2);
        customer.setOpeningAmt(7000);
        customer.setReceiveAmt(11000);
        customer.setPaymentAmt(9000);
        customer.setOutstandingAmt(9000);
        customer.setPhoneNo("113-12345678");
        customer.setAgentCode("A002");
        customers[12] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00004");
        customer.setCustomerName("Winston");
        customer.setCustomerCity("Brisban");
        customer.setWorkingArea("Brisban");
        customer.setCustomerCountry("Australia");
        customer.setGrade(1);
        customer.setOpeningAmt(5000);
        customer.setReceiveAmt(8000);
        customer.setPaymentAmt(7000);
        customer.setOutstandingAmt(6000);
        customer.setPhoneNo("AAAAAAA");
        customer.setAgentCode("A005");
        customers[13] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00023");
        customer.setCustomerName("Karl");
        customer.setCustomerCity("London");
        customer.setWorkingArea("London");
        customer.setCustomerCountry("UK");
        customer.setGrade(0);
        customer.setOpeningAmt(4000);
        customer.setReceiveAmt(6000);
        customer.setPaymentAmt(7000);
        customer.setOutstandingAmt(3000);
        customer.setPhoneNo("AAAABAA");
        customer.setAgentCode("A006");
        customers[14] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00006");
        customer.setCustomerName("Shilton");
        customer.setCustomerCity("Torento");
        customer.setWorkingArea("Torento");
        customer.setCustomerCountry("Canada");
        customer.setGrade(1);
        customer.setOpeningAmt(10000);
        customer.setReceiveAmt(7000);
        customer.setPaymentAmt(6000);
        customer.setOutstandingAmt(11000);
        customer.setPhoneNo("DDDDDDD");
        customer.setAgentCode("A004");
        customers[15] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00010");
        customer.setCustomerName("Charles");
        customer.setCustomerCity("Hampshair");
        customer.setWorkingArea("Hampshair");
        customer.setCustomerCountry("UK");
        customer.setGrade(3);
        customer.setOpeningAmt(6000);
        customer.setReceiveAmt(4000);
        customer.setPaymentAmt(5000);
        customer.setOutstandingAmt(5000);
        customer.setPhoneNo("MMMMMMM");
        customer.setAgentCode("A009");
        customers[16] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00017");
        customer.setCustomerName("Srinivas");
        customer.setCustomerCity("Bangalore");
        customer.setWorkingArea("Bangalore");
        customer.setCustomerCountry("India");
        customer.setGrade(2);
        customer.setOpeningAmt(8000);
        customer.setReceiveAmt(4000);
        customer.setPaymentAmt(3000);
        customer.setOutstandingAmt(9000);
        customer.setPhoneNo("AAAAAAB");
        customer.setAgentCode("A007");
        customers[17] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00012");
        customer.setCustomerName("Steven");
        customer.setCustomerCity("San Jose");
        customer.setWorkingArea("San Jose");
        customer.setCustomerCountry("USA");
        customer.setGrade(1);
        customer.setOpeningAmt(5000);
        customer.setReceiveAmt(7000);
        customer.setPaymentAmt(9000);
        customer.setOutstandingAmt(3000);
        customer.setPhoneNo("KRFYGJK");
        customer.setAgentCode("A012");
        customers[18] = customer;

        customer = new Customer();
        customer.setCustomerCode("c00008");
        customer.setCustomerName("Karolina");
        customer.setCustomerCity("Torento");
        customer.setWorkingArea("Torento");
        customer.setCustomerCountry("Canada");
        customer.setGrade(1);
        customer.setOpeningAmt(7000);
        customer.setReceiveAmt(7000);
        customer.setPaymentAmt(9000);
        customer.setOutstandingAmt(5000);
        customer.setPhoneNo("HJKORED");
        customer.setAgentCode("A004");
        customers[19] = customer;
        return customers;
    }

    private static Order[] getOrders(SimpleDateFormat formatter) throws ParseException {
        Order[] orders = new Order[20];

        Order order = new Order();
        order.setOrderNum(200100);
        order.setOrderAmount(1000);
        order.setAdvanceAmount(600);
        order.setOrderDate(formatter.parse("2008-01-08"));
        order.setCustomerCode("C00015");
        order.setAgentCode("A003");
        orders[0] = order;

        order = new Order();
        order.setOrderNum(200110);
        order.setOrderAmount(3000);
        order.setAdvanceAmount(500);
        order.setOrderDate(formatter.parse("2008-04-15"));
        order.setCustomerCode("C00019");
        order.setAgentCode("A010");
        orders[1] = order;

        order = new Order();
        order.setOrderNum(200107);
        order.setOrderAmount(4500);
        order.setAdvanceAmount(900);
        order.setOrderDate(formatter.parse("2008-08-30"));
        order.setCustomerCode("C00007");
        order.setAgentCode("A010");
        orders[2] = order;

        order = new Order();
        order.setOrderNum(200112);
        order.setOrderAmount(2000);
        order.setAdvanceAmount(400);
        order.setOrderDate(formatter.parse("2008-05-30"));
        order.setCustomerCode("C00016");
        order.setAgentCode("A007");
        orders[3] = order;

        order = new Order();
        order.setOrderNum(200113);
        order.setOrderAmount(4000);
        order.setAdvanceAmount(600);
        order.setOrderDate(formatter.parse("2008-06-10"));
        order.setCustomerCode("C00022");
        order.setAgentCode("A002");
        orders[4] = order;

        order = new Order();
        order.setOrderNum(200102);
        order.setOrderAmount(2000);
        order.setAdvanceAmount(300);
        order.setOrderDate(formatter.parse("2008-05-25"));
        order.setCustomerCode("C00012");
        order.setAgentCode("A012");
        orders[5] = order;

        order = new Order();
        order.setOrderNum(200114);
        order.setOrderAmount(3500);
        order.setAdvanceAmount(2000);
        order.setOrderDate(formatter.parse("2008-08-15"));
        order.setCustomerCode("C00002");
        order.setAgentCode("A008");
        orders[6] = order;

        order = new Order();
        order.setOrderNum(200122);
        order.setOrderAmount(2500);
        order.setAdvanceAmount(400);
        order.setOrderDate(formatter.parse("2008-09-16"));
        order.setCustomerCode("C00003");
        order.setAgentCode("A004");
        orders[7] = order;

        order = new Order();
        order.setOrderNum(200118);
        order.setOrderAmount(500);
        order.setAdvanceAmount(100);
        order.setOrderDate(formatter.parse("2008-07-20"));
        order.setCustomerCode("C00023");
        order.setAgentCode("A006");
        orders[8] = order;

        order = new Order();
        order.setOrderNum(200119);
        order.setOrderAmount(4000);
        order.setAdvanceAmount(700);
        order.setOrderDate(formatter.parse("2008-09-16"));
        order.setCustomerCode("C00007");
        order.setAgentCode("A010");
        orders[9] = order;

        order = new Order();
        order.setOrderNum(200121);
        order.setOrderAmount(1500);
        order.setAdvanceAmount(600);
        order.setOrderDate(formatter.parse("2008-09-23"));
        order.setCustomerCode("C00008");
        order.setAgentCode("A004");
        orders[10] = order;

        order = new Order();
        order.setOrderNum(200130);
        order.setOrderAmount(2500);
        order.setAdvanceAmount(400);
        order.setOrderDate(formatter.parse("2008-07-30"));
        order.setCustomerCode("C00025");
        order.setAgentCode("A011");
        orders[11] = order;

        order = new Order();
        order.setOrderNum(200134);
        order.setOrderAmount(4200);
        order.setAdvanceAmount(1800);
        order.setOrderDate(formatter.parse("2008-09-25"));
        order.setCustomerCode("C00004");
        order.setAgentCode("A005");
        orders[12] = order;

        order = new Order();
        order.setOrderNum(200115);
        order.setOrderAmount(2000);
        order.setAdvanceAmount(1200);
        order.setOrderDate(formatter.parse("2008-02-08"));
        order.setCustomerCode("C00013");
        order.setAgentCode("A013");
        orders[13] = order;

        order = new Order();
        order.setOrderNum(200108);
        order.setOrderAmount(4000);
        order.setAdvanceAmount(600);
        order.setOrderDate(formatter.parse("2008-02-15"));
        order.setCustomerCode("C00008");
        order.setAgentCode("A004");
        orders[14] = order;

        order = new Order();
        order.setOrderNum(200103);
        order.setOrderAmount(1500);
        order.setAdvanceAmount(700);
        order.setOrderDate(formatter.parse("2008-05-15"));
        order.setCustomerCode("C00021");
        order.setAgentCode("A005");
        orders[15] = order;

        order = new Order();
        order.setOrderNum(200105);
        order.setOrderAmount(2500);
        order.setAdvanceAmount(500);
        order.setOrderDate(formatter.parse("2008-07-18"));
        order.setCustomerCode("C00025");
        order.setAgentCode("A011");
        orders[16] = order;

        order = new Order();
        order.setOrderNum(200109);
        order.setOrderAmount(3500);
        order.setAdvanceAmount(800);
        order.setOrderDate(formatter.parse("2008-07-30"));
        order.setCustomerCode("C00011");
        order.setAgentCode("A010");
        orders[17] = order;

        order = new Order();
        order.setOrderNum(200101);
        order.setOrderAmount(3000);
        order.setAdvanceAmount(1000);
        order.setOrderDate(formatter.parse("2008-07-15"));
        order.setCustomerCode("C00001");
        order.setAgentCode("A008");
        orders[18] = order;

        order = new Order();
        order.setOrderNum(200111);
        order.setOrderAmount(1000);
        order.setAdvanceAmount(300);
        order.setOrderDate(formatter.parse("2008-07-10"));
        order.setCustomerCode("C00020");
        order.setAgentCode("A008");
        orders[19] = order;
        return orders;
    }
}

class EQJoinResult
{
    private Integer orderNum;
    private Integer orderAmount;
    private Integer advanceAmount;
    private Date orderDate;
    private String customerCode;
    private String agentCode;
    private String orderDescription;

    private String customerName;
    private String customerCity;
    private String workingArea;
    private String customerCountry;
    private Integer grade;
    private Integer openingAmt;
    private Integer receiveAmt;
    private Integer paymentAmt;
    private Integer outstandingAmt;
    private String phoneNo;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getWorkingArea() {
        return workingArea;
    }

    public void setWorkingArea(String workingArea) {
        this.workingArea = workingArea;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getOpeningAmt() {
        return openingAmt;
    }

    public void setOpeningAmt(Integer openingAmt) {
        this.openingAmt = openingAmt;
    }

    public Integer getReceiveAmt() {
        return receiveAmt;
    }

    public void setReceiveAmt(Integer receiveAmt) {
        this.receiveAmt = receiveAmt;
    }

    public Integer getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(Integer paymentAmt) {
        this.paymentAmt = paymentAmt;
    }

    public Integer getOutstandingAmt() {
        return outstandingAmt;
    }

    public void setOutstandingAmt(Integer outstandingAmt) {
        this.outstandingAmt = outstandingAmt;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(Integer advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }    
}