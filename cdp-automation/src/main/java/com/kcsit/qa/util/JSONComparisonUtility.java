package com.kcsit.qa.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

/**
 * The Class JSONComparisonUtility.
 */
public class JSONComparisonUtility {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(JSONComparisonUtility.class);

	/** The Constant DEFAULT_EMPTY_STRING. */
	private static final String DEFAULT_EMPTY_STRING = "";

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws JsonProcessingException the json processing exception
	 */
	public static void main(String[] args) throws JsonProcessingException {
		String expectedJSONString = "{ \"Name\":\"Craig1\", \"Age\":10, \"Address\": { \"Addres1\":\"Gayatree Landmark Phase-1\", "
				+ "\"Addres2\":\"Dange Chowk\", \"City\":\"Pune\", \"PinCode\":\"411033\" }, \"BookInterests\":[ { \"Book\":\"The Kite Runner\","
				+ " \"Author\":\"Khaled 1 Hosseini\" }, { \"Book\":\"Harry Potter\", \"Author\":\"J. K. Rowling\" } ], \"FoodInterests\":"
				+ "{ \"Breakfast\":[ { \"Bread\":\"Whole wheat\", \"Beverage\":\"Fruit juice\" }, { \"Sandwich\":\"Vegetable Sandwich\","
				+ " \"Beverage\":\"Coffee\" } ] } }";
		String actualJSONString = "{ \"Name\":\"Craig\", \"Age\":10.01, \"Address\": { \"Addres1\":\"Gayatree Landmark Phase-2\", "
				+ "\"Addres2\":\"Dange Chowk\", \"City\":\"Pune\", \"PinCode\":\"411033\" }, \"BookInterests\":[ { \"Book\":\"The Kite Runner\","
				+ " \"Author\":\"Khaled Hosseini\" }, { \"Book\":\"Harry Potter 2\", \"Author\":\"J. K. Rowling\" } ], \"FoodInterests\":"
				+ "{ \"Breakfast\":[ { \"Bread\":\"Whole wheat\", \"Beverage\":\"Fruit juice\" }, { \"Sandwich\":\"Vegetable Sandwich\","
				+ " \"Beverage\":\"Coffee\" } ] } }";
		List<String> comparisonResults = compareJson(expectedJSONString, actualJSONString);
		if (CollectionUtils.isEmpty(comparisonResults)) {
			LOGGER.info("Both Json are Equals!!!");
		}

		comparisonResults.forEach(resultLine -> {
			LOGGER.error(resultLine);
		});
	}

	/**
	 * Instantiates a new JSON comparison utility.
	 */
	private JSONComparisonUtility() {
		// DUMMY
	}

	/**
	 * Compare json.
	 *
	 * @param expectedJSONString the expected JSON string
	 * @param actualJSONString   the actual JSON string
	 * @return the list
	 * @throws JsonProcessingException the json processing exception
	 */
	public static List<String> compareJson(String expectedJSONString, String actualJSONString)
			throws JsonProcessingException {
		List<String> comparisonResults = null;
		if (StringUtils.isBlank(expectedJSONString) || StringUtils.isBlank(actualJSONString)) {
			comparisonResults = Collections.emptyList();
		} else {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode expectedJSONNode = mapper.readTree(expectedJSONString);
			JsonNode actualJSONNode = mapper.readTree(actualJSONString);
			comparisonResults = compareJson(expectedJSONNode, actualJSONNode);
		}
		return comparisonResults;
	}

	/**
	 * Compare json.
	 *
	 * @param expectedJSONNode the expected JSON node
	 * @param actualJSONNode   the actual JSON node
	 * @return the list
	 */
	public static List<String> compareJson(JsonNode expectedJSONNode, JsonNode actualJSONNode) {
		List<String> comparisonResults = new ArrayList<>();
		compareJsonKeys(expectedJSONNode, actualJSONNode, comparisonResults);
		if (CollectionUtils.isEmpty(comparisonResults)) {
			compareJsonValues(expectedJSONNode, actualJSONNode, comparisonResults);
		}
		return comparisonResults;
	}

	/**
	 * Compare json keys.
	 *
	 * @param expectedJSONNode  the expected JSON node
	 * @param actualJSONNode    the actual JSON node
	 * @param comparisonResults the comparison results
	 */
	private static void compareJsonKeys(JsonNode expectedJSONNode, JsonNode actualJSONNode,
			List<String> comparisonResults) {
		List<String> expectedJSONNodeKeys = new ArrayList<>();
		List<String> actualJSONNodeKeys = new ArrayList<>();
		expectedJSONNode.fieldNames().forEachRemaining(jsonNodeName -> expectedJSONNodeKeys.add(jsonNodeName));
		actualJSONNode.fieldNames().forEachRemaining(jsonNodeName -> actualJSONNodeKeys.add(jsonNodeName));
		if (expectedJSONNodeKeys.size() > actualJSONNodeKeys.size()) {
			expectedJSONNodeKeys.removeAll(actualJSONNodeKeys);
			if (CollectionUtils.isNotEmpty(expectedJSONNodeKeys)) {
				comparisonResults.add("Expected JSON has more keys " + expectedJSONNodeKeys.toString() + ".");
			}
		} else {
			actualJSONNodeKeys.removeAll(expectedJSONNodeKeys);
			if (CollectionUtils.isNotEmpty(actualJSONNodeKeys)) {
				comparisonResults.add("Actual JSON has more keys " + expectedJSONNodeKeys.toString() + ".");
			}
		}
	}

	/**
	 * Compare json values.
	 *
	 * @param expectedJSONNode  the expected JSON node
	 * @param actualJSONNode    the actual JSON node
	 * @param comparisonResults the comparison results
	 */
	private static void compareJsonValues(JsonNode expectedJSONNode, JsonNode actualJSONNode,
			List<String> comparisonResults) {
		List<String> expectedJSONNodeKeys = new ArrayList<>();
		expectedJSONNode.fieldNames().forEachRemaining(jsonNodeName -> expectedJSONNodeKeys.add(jsonNodeName));
		expectedJSONNodeKeys.forEach(jsonKey -> {
			JsonNodeType expectedNodeType = expectedJSONNode.get(jsonKey).getNodeType();
			JsonNodeType actualNodeType = actualJSONNode.get(jsonKey).getNodeType();
			if (expectedNodeType.compareTo(JsonNodeType.STRING) == 0
					&& actualNodeType.compareTo(JsonNodeType.STRING) == 0) {
				compareJsonValuesForString(jsonKey, expectedJSONNode.get(jsonKey), actualJSONNode.get(jsonKey),
						comparisonResults);
			} else if (expectedNodeType.compareTo(JsonNodeType.NUMBER) == 0
					&& actualNodeType.compareTo(JsonNodeType.NUMBER) == 0) {
				compareJsonValuesForNumber(jsonKey, expectedJSONNode.get(jsonKey), actualJSONNode.get(jsonKey),
						comparisonResults);
			} else if (expectedNodeType.compareTo(JsonNodeType.OBJECT) == 0
					&& actualNodeType.compareTo(JsonNodeType.OBJECT) == 0) {
				compareJsonValuesForObject(jsonKey, expectedJSONNode.get(jsonKey), actualJSONNode.get(jsonKey),
						comparisonResults);
			} else if (expectedNodeType.compareTo(JsonNodeType.ARRAY) == 0
					&& actualNodeType.compareTo(JsonNodeType.ARRAY) == 0) {
				compareJsonValuesForArray(jsonKey, expectedJSONNode.get(jsonKey), actualJSONNode.get(jsonKey),
						comparisonResults);
			}
		});
	}

	/**
	 * Compare json values for array.
	 *
	 * @param jsonKey           the json key
	 * @param expectedJSONNode  the expected JSON node
	 * @param actualJSONNode    the actual JSON node
	 * @param comparisonResults the comparison results
	 */
	private static void compareJsonValuesForArray(String jsonKey, JsonNode expectedJSONNode, JsonNode actualJSONNode,
			List<String> comparisonResults) {
		List<JsonNode> expectedJSONNodeList = new ArrayList<>();
		List<JsonNode> actualJSONNodeList = new ArrayList<>();
		expectedJSONNode.elements().forEachRemaining(tempNode -> expectedJSONNodeList.add(tempNode));
		actualJSONNode.elements().forEachRemaining(tempNode -> actualJSONNodeList.add(tempNode));
		if (expectedJSONNodeList.size() != actualJSONNodeList.size()) {
			comparisonResults.add("Field [" + jsonKey + "] expected array length is <" + expectedJSONNodeList.size()
					+ "> but actual array length is <" + actualJSONNodeList.size() + ">.");
		} else {
			for(int i = 0; i < expectedJSONNodeList.size(); i++) {
				JsonNode expectedJSONChildNode = expectedJSONNodeList.get(i);
				JsonNode actualJSONChildNode = actualJSONNodeList.get(i); 
				List<String> comparisonResultsForChild = new ArrayList<>();
				compareJsonValues(expectedJSONChildNode, actualJSONChildNode, comparisonResultsForChild);
				if (CollectionUtils.isNotEmpty(comparisonResultsForChild)) {
					final int arrayIndexCount = i;
					comparisonResultsForChild.forEach(result -> {
						comparisonResults.add(result.replace("Field [", "Field [" + jsonKey + "[" + (arrayIndexCount) + "]."));
					});
				}
			}
		}
	}

	/**
	 * Compare json values for object.
	 *
	 * @param jsonKey           the json key
	 * @param expectedJSONNode  the expected JSON node
	 * @param actualJSONNode    the actual JSON node
	 * @param comparisonResults the comparison results
	 */
	private static void compareJsonValuesForObject(String jsonKey, JsonNode expectedJSONNode, JsonNode actualJSONNode,
			List<String> comparisonResults) {
		List<String> comparisonResultsForObject = new ArrayList<>();
		compareJsonValues(expectedJSONNode, actualJSONNode, comparisonResultsForObject);
		if (CollectionUtils.isNotEmpty(comparisonResultsForObject)) {
			comparisonResultsForObject.forEach(result -> {
				comparisonResults.add(result.replace("Field [", "Field [" + jsonKey + "."));
			});
		}
	}

	/**
	 * Compare json values for number.
	 *
	 * @param jsonKey           the json key
	 * @param expectedJSONNode  the expected JSON node
	 * @param actualJSONNode    the actual JSON node
	 * @param comparisonResults the comparison results
	 */
	private static void compareJsonValuesForNumber(String jsonKey, JsonNode expectedJSONNode, JsonNode actualJSONNode,
			List<String> comparisonResults) {
		String expectedValue = expectedJSONNode.asText(DEFAULT_EMPTY_STRING);
		String actualValue = actualJSONNode.asText(DEFAULT_EMPTY_STRING);
		if (!StringUtils.equals(expectedValue, actualValue)) {
			comparisonResults.add("Field [" + jsonKey + "] expected value is <" + expectedValue
					+ "> but actual value is <" + actualValue + ">.");
		}
	}

	/**
	 * Compare JSON values for string.
	 *
	 * @param jsonKey           the JSON key
	 * @param expectedJSONNode  the expected JSON node
	 * @param actualJSONNode    the actual JSON node
	 * @param comparisonResults the comparison results
	 */
	private static void compareJsonValuesForString(String jsonKey, JsonNode expectedJSONNode, JsonNode actualJSONNode,
			List<String> comparisonResults) {
		String expectedValue = expectedJSONNode.asText(DEFAULT_EMPTY_STRING);
		String actualValue = actualJSONNode.asText(DEFAULT_EMPTY_STRING);
		if (!StringUtils.equals(expectedValue, actualValue)) {
			comparisonResults.add("Field [" + jsonKey + "] expected value is <" + expectedValue
					+ "> but actual value is <" + actualValue + ">.");
		}
	}
}
