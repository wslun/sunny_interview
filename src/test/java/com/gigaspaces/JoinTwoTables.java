package com.gigaspaces;

import com.gigaspaces.model.Company;
import com.gigaspaces.model.Foods;
import com.j_spaces.core.IJSpace;
import com.j_spaces.core.client.SQLQuery;
import org.junit.Before;
import org.junit.Test;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import java.util.logging.Logger;
import java.util.ArrayList;

/**
 * Created by kobi on 12/10/14.
 */
public class JoinTwoTables {


        public static Logger logger = Logger.getLogger("JoinTwoTables");
        public static final String url = "/./mySpace";


        static IJSpace space = null;
        static GigaSpace gigaSpace = null;

        @Before
        public void before() throws Exception {
        space = new UrlSpaceConfigurer(url).space();
        gigaSpace = new GigaSpaceConfigurer(space).gigaSpace();


//      create and write foodses objects

//        +---------+--------------+-----------+------------+
//        | ITEM_ID | ITEM_NAME    | ITEM_UNIT | COMPANY_ID |
//        +---------+--------------+-----------+------------+
//        | 1       | Chex Mix     | Pcs       | 16         |
//        | 6       | Cheez-It     | Pcs       | 15         |
//        | 2       | BN Biscuit   | Pcs       | 15         |
//        | 3       | Mighty Munch | Pcs       | 17         |
//        | 4       | Pot Rice     | Pcs       | 15         |
//        | 5       | Jaffa Cakes  | Pcs       | 18         |
//        | 7       | Salt n Shake | Pcs       | 19         |
//        +---------+--------------+-----------+------------+
        Foods[] foodses = getFoodses();

        gigaSpace.writeMultiple(foodses);

//      create  and write companies
//        +------------+---------------+--------------+
//        | COMPANY_ID | COMPANY_NAME  | COMPANY_CITY |
//        +------------+---------------+--------------+
//        | 18         | Order All     | Boston       |
//        | 15         | Jack Hill Ltd | London       |
//        | 16         | Akas Foods    | Delhi        |
//        | 17         | Foodies.      | London       |
//        | 19         | sip-n-Bite.   | New York     |
//        +------------+---------------+--------------+

        Company[] companies = getCompanies();

        gigaSpace.writeMultiple(companies);

        }

        @Test
        public void test(){
//TODO write the following SQL query eith XAP SQL QUERY - http://docs.gigaspaces.com/xap100net/query-sql.html

//        SELECT foods.item_name,foods.item_unit,
//                company.company_name, company.company_city
//        FROM foods ,company
//        WHERE  foods.company_id =company.company_id
//        AND company.company_city='London';
          space = new UrlSpaceConfigurer(url).space();
          gigaSpace = new GigaSpaceConfigurer(space).gigaSpace();

          
          String m_CompanyName = "", m_CompanyCity ="";
          SQLQuery<Company> qCompany = new SQLQuery<Company>(Company.class, "");
/*          qCompany.setParameter(1, "London");*/
          Company[] cResults =  gigaSpace.readMultiple(qCompany);
          String inCondition = "";
          for(int i=0; i<cResults.length; i++)
          {
        	  if( cResults[i].getCompanyCity().equals("London") )
        	  {
            	  if( ! inCondition.equals("") )
            		  inCondition += ",";        	  
            	  else
            	  {
            	      m_CompanyName = cResults[i].getCompanyName();
            	      m_CompanyCity = cResults[i].getCompanyCity();
            	  }
        	      inCondition += cResults[i].getCompanyId();
        	  }
          }

          ArrayList<JoinResult> results = new ArrayList<JoinResult>();
          SQLQuery<Foods> qFoods = new SQLQuery<Foods>(Foods.class, "companyId in (" + inCondition + ")");
          Foods[] fResults = gigaSpace.readMultiple(qFoods);
          for(int i=0; i<fResults.length; i++)
          {
        	  JoinResult m_Result = new JoinResult();
        	  m_Result.setItemName(fResults[i].getItemName());
        	  m_Result.setItemUnit(fResults[i].getItemUnit());
        	  m_Result.setCompanyCity(m_CompanyCity);
        	  m_Result.setCompanyName(m_CompanyName);
        	  results.add(m_Result);
          }
          for (int i = 0; i < results.size(); i++)
          {
        	   JoinResult item = results.get(i);
        	   System.out.println("Item " + i + " : " + item.getItemName());
          }
        }


        private static Company[] getCompanies() {
                String[] companyNames = {"Order All", "Jack Hill Ltd", "Akas Foods", "Foodies.", "sip-n-Bite."};
                String[] companyCities = {"Boston", "London", "Delhi", "London", "New York"};
                Company[] companies = new Company[5];

                Company company = new Company();
                company.setCompanyId(18);
                company.setCompanyCity(companyCities[0]);
                company.setCompanyName(companyNames[0]);
                companies[0] = company;

                company = new Company();
                company.setCompanyId(15);
                company.setCompanyCity(companyCities[1]);
                company.setCompanyName(companyNames[1]);
                companies[1] = company;

                company = new Company();
                company.setCompanyId(16);
                company.setCompanyCity(companyCities[2]);
                company.setCompanyName(companyNames[2]);
                companies[2] = company;

                company = new Company();
                company.setCompanyId(17);
                company.setCompanyCity(companyCities[3]);
                company.setCompanyName(companyNames[3]);
                companies[3] = company;

                company = new Company();
                company.setCompanyId(19);
                company.setCompanyCity(companyCities[4]);
                company.setCompanyName(companyNames[4]);
                companies[4] = company;
                return companies;
        }

        private static Foods[] getFoodses() {
                String[] itemNames = {"Chex Mix", "Cheez-It", "BN Biscuit", "Mighty Munch", "Pot Rice", "Jaffa Cakes", "Salt n Shake"};
                Foods[] foodses = new Foods[7];

                Foods foods = new Foods();
                foods.setItemId(1);
                foods.setItemName(itemNames[0]);
                foods.setItemUnit("Pcs");
                foods.setCompanyId(16);
                foodses[0] = foods;

                foods = new Foods();
                foods.setItemId(2);
                foods.setItemName(itemNames[2]);
                foods.setItemUnit("Pcs");
                foods.setCompanyId(15);
                foodses[1] = foods;

                foods = new Foods();
                foods.setItemId(3);
                foods.setItemName(itemNames[3]);
                foods.setItemUnit("Pcs");
                foods.setCompanyId(17);
                foodses[2] = foods;

                foods = new Foods();
                foods.setItemId(4);
                foods.setItemName(itemNames[4]);
                foods.setItemUnit("Pcs");
                foods.setCompanyId(15);
                foodses[3] = foods;

                foods = new Foods();
                foods.setItemId(5);
                foods.setItemName(itemNames[5]);
                foods.setItemUnit("Pcs");
                foods.setCompanyId(18);
                foodses[4] = foods;

                foods = new Foods();
                foods.setItemId(6);
                foods.setItemName(itemNames[1]);
                foods.setItemUnit("Pcs");
                foods.setCompanyId(15);
                foodses[5] = foods;

                foods = new Foods();
                foods.setItemId(7);
                foods.setItemName(itemNames[6]);
                foods.setItemUnit("Pcs");
                foods.setCompanyId(19);
                foodses[6] = foods;
                return foodses;
        }
}

class JoinResult
{
	    private String CompanyName;
	    private String CompanyCity;
	    private Integer itemId;
	    private String itemName;
	    private String itemUnit;
	    private Integer companyId;

	    public Integer getItemId() {
	        return itemId;
	    }

	    public void setItemId(Integer itemId) {
	        this.itemId = itemId;
	    }

	    public String getItemName() {
	        return itemName;
	    }

	    public void setItemName(String itemName) {
	        this.itemName = itemName;
	    }

	    public String getItemUnit() {
	        return itemUnit;
	    }

	    public void setItemUnit(String itemUnit) {
	        this.itemUnit = itemUnit;
	    }

	    public Integer getCompanyId() {
	        return companyId;
	    }

	    public void setCompanyId(Integer companyId) {
	        this.companyId = companyId;
	    }

	    public String getCompanyName() {
	        return CompanyName;
	    }

	    public void setCompanyName(String companyName) {
	        CompanyName = companyName;
	    }

	    public String getCompanyCity() {
	        return CompanyCity;
	    }

	    public void setCompanyCity(String companyCity) {
	        CompanyCity = companyCity;
	    }
}