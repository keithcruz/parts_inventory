//<<<<<<< HEAD
////package test_package;
////
////import static org.junit.Assert.*;
////import org.junit.BeforeClass;
////import org.junit.Test;
////
////import parts_inventory.*;
////
////
////public class TestModel {
////	
////	@BeforeClass
////	public static void setUpBeforeClass() throws Exception {
////	}
////	
////
//=======
//package test_package;
//
//import static org.junit.Assert.*;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import parts_inventory.*;
//
//
//public class TestModel {
//	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//	
//
//	@Test
//	public void test1() {
//		InventoryModel model = new InventoryModel();
//
//		//model.newPart( "x", "ven", 20, "Pieces", "Facility 1 Warehouse 1", "YYAA%5");
//		  model.newPart( "x", "ven", "Pieces", "YYAA%5");
//
//		
//		//The Part added should be the first part in the ArrayList of parts.
//		assertTrue(model.getParts().get(0).getName().equals("x"));
//		
//		
//	}
//	
//>>>>>>> origin/b_assign3
////	@Test
////	public void test1() {
////		InventoryModel model = new InventoryModel();
////
////		model.newPart( "x", "ven", 20, "Pieces", "Facility 1 Warehouse 1", "YYAA%5");
////
////		
////		//The Part added should be the first part in the ArrayList of parts.
////		assertTrue(model.getParts().get(0).getName().equals("x"));
////		
////		
////	}
//<<<<<<< HEAD
////	
//////	@Test
//////	public void test2() {
//////		InventoryModel model = new InventoryModel();
//////
//////		model.newPart("x", "ven", 20, "Linear Feet", "Facility 1 Warehouse 1", "");
//////		model.editPart("x", "x", 5, "Linear Feet", "Facility 1 Warehouse 1", "");
//////		
//////		//The part should have been added and the quantity edited.
//////		assertTrue(model.getParts().get(0).getQuantity() == 5);
//////	}
////	
////	@Test
////	public void test3() {
////		InventoryModel model = new InventoryModel();
////
////		Part p = new Part("x", "ven", 20, "Pieces", "Facility 1 Warehouse 1","AYY11&");
////		model.newPart("x", "ven", 20, "Pieces", "Facility 1 Warehouse 1", "AYY11&");
////
////		model.deletePart(p);
////		
////		//The part added should have been deleted and the ArrayList is now empty.
////		assertTrue(model.getParts().isEmpty());
////	}
////	
////	@Test
////	public void test4() {
////		InventoryModel model = new InventoryModel();
////		model.newPart("x", "ven", 20, "Pieces", "Facility 1 Warehouse 1", "");
////
////		Part p = null;
////		p = model.getByName("x");
////		
////		//The part p should be the same as the part just added.
////		assertTrue(model.getParts().get(0).getName().equals("x") && model.getParts().get(0).partNumber().equals("777"));
////	}
////
////}
//=======
//	
//	@Test
//	public void test3() {
//		InventoryModel model = new InventoryModel();
//
//		//Part p = new Part("x", "ven", 20, "Pieces", "Facility 1 Warehouse 1","AYY11&");
//		  Part p = new Part("x", "ven", "Pieces","AYY11&");
//		//model.newPart("x", "ven", 20, "Pieces", "Facility 1 Warehouse 1", "AYY11&");
//		  model.newPart("x", "ven", "Pieces", "AYY11&");
//
//		model.deletePart(p);
//		
//		//The part added should have been deleted and the ArrayList is now empty.
//		assertTrue(model.getParts().isEmpty());
//	}
//	
//	@Test
//	public void test4() {
//		InventoryModel model = new InventoryModel();
//		//model.newPart("x", "ven", 20, "Pieces", "Facility 1 Warehouse 1", "");
//		model.newPart("x", "ven", "Pieces", "");
//
//		Part p = null;
//		p = model.getByName("x");
//		
//		//The part p should be the same as the part just added.
//		assertTrue(model.getParts().get(0).getName().equals("x") && model.getParts().get(0).partNumber().equals("777"));
//	}
//
//}
//>>>>>>> origin/b_assign3
