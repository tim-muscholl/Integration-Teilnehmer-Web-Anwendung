package de.hdbw.integration.jaxrs.client.test;

import de.hdbw.integration.jaxrs.client.TeilnehmerJaxRSClient;

public class EinJaxRSClientTest {

	private static String getUrl =  "https://api.predic8.de/shop/products/33";
	private static String postUrl = "https://api.predic8.de/shop/products/";
	private static String putUrl = "https://api.predic8.de/shop/products/33";
		
	public static void main(String[] args) {
		
		//Ruft GET auf
		String resultGET = TeilnehmerJaxRSClient.getRequest(getUrl, null);
		System.out.println("GET-Response from the Server: ");
		System.out.println(resultGET + "\n");
		

		// Ruft PUT auf
		String inputDataPUT = "{ \"name\": \"Pineapples\", \"price\": 2.22, \"category_url\": \"/shop/categories/Fruits\", \"vendor_url\": \"/shop/vendors/672\" }";
		String resultPUT = TeilnehmerJaxRSClient.putRequest(putUrl, inputDataPUT);
		System.out.println("PUT-Response from the Server: ");
		System.out.println(resultPUT + "\n");
		
		
		// Ruft POST auf
		String inputDataPOST = "{ \"name\": \"LiHzi_2\", \"price\": 1.99, \"category_url\": \"/shop/categories/Fruits\", \"vendor_url\": \"/shop/vendors/672\" }";
		//String inputDataPOST = "{ \"name\": \"LiZhi\", \"price\": 2.98, \"category_url\": \"/shop/categories/Fruits\", \"vendor_url\": \"/shop/vendors/672\" }";
		String resultPOST = TeilnehmerJaxRSClient.postRequest(postUrl, inputDataPOST);
		System.out.println("POST-Response from the Server: ");
		System.out.println(resultPOST+ "\n");
		
	}
}
