/*
 * 
 */
package com.kcsit.qa.authentication;

import static io.restassured.RestAssured.given;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;

// TODO: Auto-generated Javadoc
/**
 * The Class AuthenticationTest.
 */
public class AuthenticationTest {

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTest.class);

	/** The cara authentication key. */
	private static String caraAuthenticationKey = "";

	/** The cdp authentication key. */
	private static String cdpAuthenticationKey = "";

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		performCdpAuthentication();
	}

	/**
	 * Perform CARA authentication.
	 *
	 * @return the string
	 */
	public static String performCaraAuthentication() {
		if (StringUtils.isBlank(caraAuthenticationKey)) {
			Response response = given().contentType("multipart/form-data").multiPart("grant_type", "password")
					.multiPart("resource", "https://5a9d9cfd-c32e-4ac1-a9ed-fe83df4f9e4d/cara-api-qa")
				.multiPart("client_id", "aa48fe11-4e42-4d0e-bd43-5a6b6dba8de9")
					.multiPart("client_secret", "xQjBhIypRtVvPhxKvJpos+jAdUXGkWOQrHZS+pk47s4=")
					.multiPart("username", "s_CRMQACara@ashleyfurniture.com").multiPart("password", "F85!aPhv85#fXyTd")
					.post("https://login.microsoftonline.com/5a9d9cfd-c32e-4ac1-a9ed-fe83df4f9e4d/oauth2/token");
					
		/* prod auth CARA 	*/
		 /* Response response = given().contentType("multipart/form-data").multiPart("grant_type", "password")		
		 
					.multiPart("resource", "https://5a9d9cfd-c32e-4ac1-a9ed-fe83df4f9e4d/cara-api-prod")
					.multiPart("client_id", "d3b9cb6d-70e5-4aa8-a90e-04fd5f1c2ecc ")
						.multiPart("client_secret", "7Un7lq+Btjo9prQnt8iW7a43d5gK39yYDeQAGzYBE0o= ")
						.multiPart("username", "s_CRMProdCara@ashleyfurniture.com ").multiPart("password", "D96!wPhbD74#asUx")
						.post("https://login.microsoftonline.com/5a9d9cfd-c32e-4ac1-a9ed-fe83df4f9e4d/oauth2/token"); */
			String responseString = response.getBody().asString();

			if (response.getStatusCode() == 200 && StringUtils.isNotBlank(responseString)) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					JsonNode jsonNode = mapper.readTree(responseString);
					JsonNode resultJsonNode = jsonNode.get("access_token");
					if (resultJsonNode != null) {
						caraAuthenticationKey = "Bearer " + resultJsonNode.asText();
					}
				} catch (JsonProcessingException exception) {
					LOGGER.error("Failed to parse response JSON. Reason - {}", exception.getLocalizedMessage());
				}
			}
		}
		return caraAuthenticationKey;
	}

	/**
	 * Perform CDP authentication.
	 *
	 * @return the string
	 */
	public static String performCdpAuthentication() {
		if (StringUtils.isBlank(cdpAuthenticationKey)) {
			Response response = given().contentType("multipart/form-data")
					.multiPart("client_id", "7dfdcf5f-4d96-40fa-a0e8-0b3bb7cf7baf")
					.multiPart("client_secret", "ym-V9FiFwq./wbuKe9nFDG77i?P@Zcb4")
					.multiPart("grant_type", "client_credentials")
					.multiPart("scope", "https://6c09f207-a37e-4a2c-bf5f-fe2682cd5fab/customer-data-master-stage/.default")
					.post("https://login.microsoftonline.com/5a9d9cfd-c32e-4ac1-a9ed-fe83df4f9e4d/oauth2/v2.0/token");
					
			/* Prod auth CDP */	
			
			/*
			 * CDP auth Response response = given().contentType("multipart/form-data")
			 * .multiPart("grant_type", "password") .multiPart("resource",
			 * "https://5a9d9cfd-c32e-4ac1-a9ed-fe83df4f9e4d/cara-api-prod")
			 * .multiPart("client_id", "d3b9cb6d-70e5-4aa8-a90e-04fd5f1c2ecc ")
			 * .multiPart("client_secret", "7Un7lq+Btjo9prQnt8iW7a43d5gK39yYDeQAGzYBE0o= ")
			 * .multiPart("username",
			 * "s_CRMProdCara@ashleyfurniture.com ").multiPart("password",
			 * "D96!wPhbD74#asUx") .post(
			 * "https://login.microsoftonline.com/5a9d9cfd-c32e-4ac1-a9ed-fe83df4f9e4d/oauth2/token"
			 * );
			 */
					
			/*
			 * Response response = given().contentType("multipart/form-data")
			 * .multiPart("grant_type", "password") .multiPart("resource",
			 * "https://5a9d9cfd-c32e-4ac1-a9ed-fe83df4f9e4d/cara-api-prod")
			 * .multiPart("client_id", "d3b9cb6d-70e5-4aa8-a90e-04fd5f1c2ecc ")
			 * .multiPart("client_secret", "7Un7lq+Btjo9prQnt8iW7a43d5gK39yYDeQAGzYBE0o= ")
			 * .multiPart("username",
			 * "s_CRMProdCara@ashleyfurniture.com ").multiPart("password",
			 * "D96!wPhbD74#asUx") .post(
			 * "https://login.microsoftonline.com/5a9d9cfd-c32e-4ac1-a9ed-fe83df4f9e4d/oauth2/token"
			 * );
			 */
			String responseString = response.getBody().asString();

			if (response.getStatusCode() == 200 && StringUtils.isNotBlank(responseString)) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					JsonNode jsonNode = mapper.readTree(responseString);
					JsonNode resultJsonNode = jsonNode.get("access_token");
					if (resultJsonNode != null) {
						cdpAuthenticationKey = "Bearer " + resultJsonNode.asText();
					}
				} catch (JsonProcessingException exception) {
					LOGGER.error("Failed to parse response JSON. Reason - {}", exception.getLocalizedMessage());
				}
			}			
		}

		return cdpAuthenticationKey;
	}
}
