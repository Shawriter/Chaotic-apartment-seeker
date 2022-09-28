import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpClient;

import java.nio.charset.StandardCharsets;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class DistanceEst extends dataScra {

	final static String apiUrl = "https://api.digitransit.fi/routing/v1/routers/hsl/index/graphql";


	public static void APIposter(String osoite2) throws IOException, InterruptedException{

		BufferedReader reader;
		String line;
		String currenTime;
		StringBuffer responseContent = new StringBuffer();
		StringBuilder sb = new StringBuilder();
		


		

		try {

			URL url = new URL(apiUrl);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type","application/json");
			connection.setRequestProperty("Accept", "application/json");

			String payload = "{\r\n"
					+ "    \"id\": \"queryUtils_SummaryPage_Query\",\r\n"
					+ "    \"query\": \"query queryUtils_SummaryPage_Query(\\n  $fromPlace: String!\\n  $toPlace: String!\\n  $intermediatePlaces: [InputCoordinates!]\\n  $numItineraries: Int!\\n  $modes: [TransportMode!]\\n  $date: String!\\n  $time: String!\\n  $walkReluctance: Float\\n  $walkBoardCost: Int\\n  $minTransferTime: Int\\n  $walkSpeed: Float\\n  $maxWalkDistance: Float\\n  $wheelchair: Boolean\\n  $ticketTypes: [String]\\n  $disableRemainingWeightHeuristic: Boolean\\n  $arriveBy: Boolean\\n  $transferPenalty: Int\\n  $bikeSpeed: Float\\n  $optimize: OptimizeType\\n  $itineraryFiltering: Float\\n  $unpreferred: InputUnpreferred\\n  $allowedBikeRentalNetworks: [String]\\n  $locale: String\\n) {\\n  viewer {\\n    ...SummaryPage_viewer_3rqNOH\\n  }\\n  serviceTimeRange {\\n    ...SummaryPage_serviceTimeRange\\n  }\\n}\\n\\nfragment ItineraryLine_legs on Leg {\\n  mode\\n  rentedBike\\n  startTime\\n  endTime\\n  distance\\n  legGeometry {\\n    points\\n  }\\n  transitLeg\\n  interlineWithPreviousLeg\\n  route {\\n    shortName\\n    color\\n    type\\n    agency {\\n      name\\n      id\\n    }\\n    id\\n  }\\n  from {\\n    lat\\n    lon\\n    name\\n    vertexType\\n    bikeRentalStation {\\n      lat\\n      lon\\n      stationId\\n      networks\\n      bikesAvailable\\n      id\\n    }\\n    stop {\\n      gtfsId\\n      code\\n      platformCode\\n      id\\n    }\\n  }\\n  to {\\n    lat\\n    lon\\n    name\\n    vertexType\\n    bikeRentalStation {\\n      lat\\n      lon\\n      stationId\\n      networks\\n      bikesAvailable\\n      id\\n    }\\n    stop {\\n      gtfsId\\n      code\\n      platformCode\\n      id\\n    }\\n  }\\n  trip {\\n    stoptimes {\\n      stop {\\n        gtfsId\\n        id\\n      }\\n      pickupType\\n    }\\n    id\\n  }\\n  intermediatePlaces {\\n    arrivalTime\\n    stop {\\n      gtfsId\\n      lat\\n      lon\\n      name\\n      code\\n      platformCode\\n      id\\n    }\\n  }\\n}\\n\\nfragment ItinerarySummaryListContainer_itineraries on Itinerary {\\n  walkDistance\\n  startTime\\n  endTime\\n  legs {\\n    realTime\\n    realtimeState\\n    transitLeg\\n    startTime\\n    endTime\\n    mode\\n    distance\\n    duration\\n    rentedBike\\n    interlineWithPreviousLeg\\n    intermediatePlace\\n    intermediatePlaces {\\n      stop {\\n        zoneId\\n        id\\n      }\\n    }\\n    route {\\n      mode\\n      shortName\\n      type\\n      color\\n      agency {\\n        name\\n        id\\n      }\\n      alerts {\\n        alertSeverityLevel\\n        effectiveEndDate\\n        effectiveStartDate\\n        trip {\\n          pattern {\\n            code\\n            id\\n          }\\n          id\\n        }\\n        id\\n      }\\n      id\\n    }\\n    trip {\\n      pattern {\\n        code\\n        id\\n      }\\n      stoptimes {\\n        realtimeState\\n        stop {\\n          gtfsId\\n          id\\n        }\\n        pickupType\\n      }\\n      id\\n    }\\n    from {\\n      name\\n      lat\\n      lon\\n      stop {\\n        gtfsId\\n        zoneId\\n        platformCode\\n        alerts {\\n          alertSeverityLevel\\n          effectiveEndDate\\n          effectiveStartDate\\n          id\\n        }\\n        id\\n      }\\n      bikeRentalStation {\\n        bikesAvailable\\n        networks\\n        id\\n      }\\n    }\\n    to {\\n      stop {\\n        gtfsId\\n        zoneId\\n        alerts {\\n          alertSeverityLevel\\n          effectiveEndDate\\n          effectiveStartDate\\n          id\\n        }\\n        id\\n      }\\n      bikePark {\\n        bikeParkId\\n        name\\n        id\\n      }\\n      carPark {\\n        carParkId\\n        name\\n        id\\n      }\\n    }\\n  }\\n}\\n\\nfragment ItineraryTab_itinerary on Itinerary {\\n  walkDistance\\n  duration\\n  startTime\\n  endTime\\n  fares {\\n    cents\\n    components {\\n      cents\\n      fareId\\n      routes {\\n        agency {\\n          gtfsId\\n          fareUrl\\n          name\\n          id\\n        }\\n        gtfsId\\n        id\\n      }\\n    }\\n    type\\n  }\\n  legs {\\n    mode\\n    ...LegAgencyInfo_leg\\n    from {\\n      lat\\n      lon\\n      name\\n      vertexType\\n      bikePark {\\n        bikeParkId\\n        name\\n        id\\n      }\\n      bikeRentalStation {\\n        networks\\n        bikesAvailable\\n        lat\\n        lon\\n        stationId\\n        id\\n      }\\n      stop {\\n        gtfsId\\n        code\\n        platformCode\\n        vehicleMode\\n        zoneId\\n        alerts {\\n          alertSeverityLevel\\n          effectiveEndDate\\n          effectiveStartDate\\n          trip {\\n            pattern {\\n              code\\n              id\\n            }\\n            id\\n          }\\n          alertHeaderText\\n          alertHeaderTextTranslations {\\n            text\\n            language\\n          }\\n          alertDescriptionText\\n          alertDescriptionTextTranslations {\\n            text\\n            language\\n          }\\n          alertUrl\\n          alertUrlTranslations {\\n            text\\n            language\\n          }\\n          id\\n        }\\n        id\\n      }\\n    }\\n    to {\\n      lat\\n      lon\\n      name\\n      vertexType\\n      bikeRentalStation {\\n        lat\\n        lon\\n        stationId\\n        networks\\n        bikesAvailable\\n        id\\n      }\\n      stop {\\n        gtfsId\\n        code\\n        platformCode\\n        zoneId\\n        name\\n        vehicleMode\\n        alerts {\\n          alertSeverityLevel\\n          effectiveEndDate\\n          effectiveStartDate\\n          trip {\\n            pattern {\\n              code\\n              id\\n            }\\n            id\\n          }\\n          alertHeaderText\\n          alertHeaderTextTranslations {\\n            text\\n            language\\n          }\\n          alertDescriptionText\\n          alertDescriptionTextTranslations {\\n            text\\n            language\\n          }\\n          alertUrl\\n          alertUrlTranslations {\\n            text\\n            language\\n          }\\n          id\\n        }\\n        id\\n      }\\n      bikePark {\\n        bikeParkId\\n        name\\n        id\\n      }\\n      carPark {\\n        carParkId\\n        name\\n        id\\n      }\\n    }\\n    legGeometry {\\n      length\\n      points\\n    }\\n    intermediatePlaces {\\n      arrivalTime\\n      stop {\\n        gtfsId\\n        lat\\n        lon\\n        name\\n        code\\n        platformCode\\n        zoneId\\n        id\\n      }\\n    }\\n    realTime\\n    realtimeState\\n    transitLeg\\n    rentedBike\\n    startTime\\n    endTime\\n    interlineWithPreviousLeg\\n    distance\\n    duration\\n    intermediatePlace\\n    route {\\n      shortName\\n      color\\n      gtfsId\\n      type\\n      longName\\n      desc\\n      agency {\\n        gtfsId\\n        fareUrl\\n        name\\n        phone\\n        id\\n      }\\n      alerts {\\n        alertSeverityLevel\\n        effectiveEndDate\\n        effectiveStartDate\\n        trip {\\n          pattern {\\n            code\\n            id\\n          }\\n          id\\n        }\\n        alertHeaderText\\n        alertHeaderTextTranslations {\\n          text\\n          language\\n        }\\n        alertDescriptionText\\n        alertDescriptionTextTranslations {\\n          text\\n          language\\n        }\\n        alertUrl\\n        alertUrlTranslations {\\n          text\\n          language\\n        }\\n        id\\n      }\\n      id\\n    }\\n    trip {\\n      gtfsId\\n      tripHeadsign\\n      pattern {\\n        code\\n        id\\n      }\\n      stoptimesForDate {\\n        headsign\\n        pickupType\\n        realtimeState\\n        stop {\\n          gtfsId\\n          id\\n        }\\n      }\\n      id\\n    }\\n  }\\n}\\n\\nfragment ItineraryTab_plan on Plan {\\n  date\\n}\\n\\nfragment LegAgencyInfo_leg on Leg {\\n  agency {\\n    name\\n    url\\n    fareUrl\\n    id\\n  }\\n}\\n\\nfragment RouteLine_pattern on Pattern {\\n  code\\n  geometry {\\n    lat\\n    lon\\n  }\\n  route {\\n    mode\\n    type\\n    color\\n    id\\n  }\\n  stops {\\n    lat\\n    lon\\n    name\\n    gtfsId\\n    platformCode\\n    code\\n    ...StopCardHeaderContainer_stop\\n    id\\n  }\\n}\\n\\nfragment StopCardHeaderContainer_stop on Stop {\\n  gtfsId\\n  name\\n  code\\n  desc\\n  zoneId\\n  alerts {\\n    alertSeverityLevel\\n    effectiveEndDate\\n    effectiveStartDate\\n    id\\n  }\\n  lat\\n  lon\\n  stops {\\n    name\\n    desc\\n    id\\n  }\\n}\\n\\nfragment SummaryPage_serviceTimeRange on serviceTimeRange {\\n  start\\n  end\\n}\\n\\nfragment SummaryPage_viewer_3rqNOH on QueryType {\\n  plan(fromPlace: $fromPlace, toPlace: $toPlace, intermediatePlaces: $intermediatePlaces, numItineraries: $numItineraries, transportModes: $modes, date: $date, time: $time, walkReluctance: $walkReluctance, walkBoardCost: $walkBoardCost, minTransferTime: $minTransferTime, walkSpeed: $walkSpeed, maxWalkDistance: $maxWalkDistance, wheelchair: $wheelchair, allowedTicketTypes: $ticketTypes, disableRemainingWeightHeuristic: $disableRemainingWeightHeuristic, arriveBy: $arriveBy, transferPenalty: $transferPenalty, bikeSpeed: $bikeSpeed, optimize: $optimize, itineraryFiltering: $itineraryFiltering, unpreferred: $unpreferred, allowedBikeRentalNetworks: $allowedBikeRentalNetworks, locale: $locale) {\\n    ...SummaryPlanContainer_plan\\n    ...ItineraryTab_plan\\n    itineraries {\\n      startTime\\n      endTime\\n      ...ItineraryTab_itinerary\\n      ...SummaryPlanContainer_itineraries\\n      legs {\\n        mode\\n        ...ItineraryLine_legs\\n        transitLeg\\n        legGeometry {\\n          points\\n        }\\n        route {\\n          gtfsId\\n          type\\n          shortName\\n          id\\n        }\\n        trip {\\n          gtfsId\\n          directionId\\n          stoptimesForDate {\\n            scheduledDeparture\\n            pickupType\\n          }\\n          pattern {\\n            ...RouteLine_pattern\\n            id\\n          }\\n          id\\n        }\\n        from {\\n          name\\n          lat\\n          lon\\n          stop {\\n            gtfsId\\n            zoneId\\n            id\\n          }\\n          bikeRentalStation {\\n            bikesAvailable\\n            networks\\n            id\\n          }\\n        }\\n        to {\\n          stop {\\n            gtfsId\\n            zoneId\\n            id\\n          }\\n          bikePark {\\n            bikeParkId\\n            name\\n            id\\n          }\\n        }\\n      }\\n    }\\n  }\\n}\\n\\nfragment SummaryPlanContainer_itineraries on Itinerary {\\n  ...ItinerarySummaryListContainer_itineraries\\n  endTime\\n  startTime\\n  legs {\\n    mode\\n    to {\\n      bikePark {\\n        bikeParkId\\n        name\\n        id\\n      }\\n    }\\n    ...ItineraryLine_legs\\n    transitLeg\\n    legGeometry {\\n      points\\n    }\\n    route {\\n      gtfsId\\n      id\\n    }\\n    trip {\\n      gtfsId\\n      directionId\\n      stoptimesForDate {\\n        scheduledDeparture\\n      }\\n      pattern {\\n        ...RouteLine_pattern\\n        id\\n      }\\n      id\\n    }\\n  }\\n}\\n\\nfragment SummaryPlanContainer_plan on Plan {\\n  date\\n  itineraries {\\n    startTime\\n    endTime\\n    legs {\\n      mode\\n      ...ItineraryLine_legs\\n      transitLeg\\n      legGeometry {\\n        points\\n      }\\n      route {\\n        gtfsId\\n        id\\n      }\\n      trip {\\n        gtfsId\\n        directionId\\n        stoptimesForDate {\\n          scheduledDeparture\\n          pickupType\\n        }\\n        pattern {\\n          ...RouteLine_pattern\\n          id\\n        }\\n        id\\n      }\\n      from {\\n        name\\n        lat\\n        lon\\n        stop {\\n          gtfsId\\n          zoneId\\n          id\\n        }\\n        bikeRentalStation {\\n          bikesAvailable\\n          networks\\n          id\\n        }\\n      }\\n      to {\\n        stop {\\n          gtfsId\\n          zoneId\\n          id\\n        }\\n        bikePark {\\n          bikeParkId\\n          name\\n          id\\n        }\\n      }\\n    }\\n  }\\n}\\n\",\r\n"
					+ "    \"variables\": {\r\n"
					+ "        \"fromPlace\": \""+ osoite2 + ", \",\r\n"
					+ "        \"toPlace\": \"\",\r\n"
					+ "        \"intermediatePlaces\": [],\r\n"
					+ "        \"numItineraries\": 5,\r\n"
					+ "        \"modes\": [\r\n"
					+ "            {\r\n"
					+ "                \"mode\": \"BUS\"\r\n"
					+ "            },\r\n"
					+ "            {\r\n"
					+ "                \"mode\": \"RAIL\"\r\n"
					+ "            },\r\n"
					+ "            {\r\n"
					+ "                \"mode\": \"SUBWAY\"\r\n"
					+ "            },\r\n"
					+ "            {\r\n"
					+ "                \"mode\": \"TRAM\"\r\n"
					+ "            },\r\n"
					+ "            {\r\n"
					+ "                \"mode\": \"WALK\"\r\n"
					+ "            }\r\n"
					+ "        ],\r\n"
					+ "        \"date\": \"2022-09-27\",\r\n"
					+ "        \"time\": \"06:30:00\",\r\n"
					+ "        \"walkReluctance\": 2,\r\n"
					+ "        \"walkBoardCost\": 100,\r\n"
					+ "        \"minTransferTime\": 300,\r\n"
					+ "        \"walkSpeed\": 1.2,\r\n"
					+ "        \"maxWalkDistance\": 2500,\r\n"
					+ "        \"wheelchair\": false,\r\n"
					+ "        \"ticketTypes\": null,\r\n"
					+ "        \"disableRemainingWeightHeuristic\": false,\r\n"
					+ "        \"arriveBy\": false,\r\n"
					+ "        \"transferPenalty\": 0,\r\n"
					+ "        \"bikeSpeed\": 5.55,\r\n"
					+ "        \"optimize\": \"GREENWAYS\",\r\n"
					+ "        \"itineraryFiltering\": 2.5,\r\n"
					+ "        \"unpreferred\": {\r\n"
					+ "            \"useUnpreferredRoutesPenalty\": 1200\r\n"
					+ "        },\r\n"
					+ "        \"allowedBikeRentalNetworks\": [],\r\n"
					+ "        \"locale\": \"fi\"\r\n"
					+ "    }\r\n"
					+ "}\r\n"
					+ "{\r\n"
					+ "    \"fromPlace\": \", \",\r\n"
					+ "    \"toPlace\": \", \",\r\n"
					+ "    \"intermediatePlaces\": [],\r\n"
					+ "    \"numItineraries\": 5,\r\n"
					+ "    \"modes\": [\r\n"
					+ "        {\r\n"
					+ "            \"mode\": \"BUS\"\r\n"
					+ "        },\r\n"
					+ "        {\r\n"
					+ "            \"mode\": \"RAIL\"\r\n"
					+ "        },\r\n"
					+ "        {\r\n"
					+ "            \"mode\": \"SUBWAY\"\r\n"
					+ "        },\r\n"
					+ "        {\r\n"
					+ "            \"mode\": \"TRAM\"\r\n"
					+ "        },\r\n"
					+ "        {\r\n"
					+ "            \"mode\": \"WALK\"\r\n"
					+ "        }\r\n"
					+ "    ],\r\n"
					+ "    \"date\": \"2022-09-19\",\r\n"
					+ "    \"time\": \"06:30:00\",\r\n"
					+ "    \"walkReluctance\": 2,\r\n"
					+ "    \"walkBoardCost\": 600,\r\n"
					+ "    \"minTransferTime\": 120,\r\n"
					+ "    \"walkSpeed\": 1.2,\r\n"
					+ "    \"maxWalkDistance\": 2500,\r\n"
					+ "    \"wheelchair\": false,\r\n"
					+ "    \"ticketTypes\": null,\r\n"
					+ "    \"disableRemainingWeightHeuristic\": false,\r\n"
					+ "    \"arriveBy\": false,\r\n"
					+ "    \"transferPenalty\": 0,\r\n"
					+ "    \"bikeSpeed\": 5.55,\r\n"
					+ "    \"optimize\": \"GREENWAYS\",\r\n"
					+ "    \"itineraryFiltering\": 2.5,\r\n"
					+ "    \"unpreferred\": {\r\n"
					+ "        \"useUnpreferredRoutesPenalty\": 1200\r\n"
					+ "    },\r\n"
					+ "    \"allowedBikeRentalNetworks\": [],\r\n"
					+ "    \"locale\": \"fi\"\r\n"
					+ "}\r\n"
					+ "[\r\n"
					+ "    {\r\n"
					+ "        \"mode\": \"BUS\"\r\n"
					+ "    },\r\n"
					+ "    {\r\n"
					+ "        \"mode\": \"RAIL\"\r\n"
					+ "    },\r\n"
					+ "    {\r\n"
					+ "        \"mode\": \"SUBWAY\"\r\n"
					+ "    },\r\n"
					+ "    {\r\n"
					+ "        \"mode\": \"TRAM\"\r\n"
					+ "    },\r\n"
					+ "    {\r\n"
					+ "        \"mode\": \"WALK\"\r\n"
					+ "    }\r\n"
					+ "]\r\n"
					+ "{\r\n"
					+ "    \"useUnpreferredRoutesPenalty\": 1200\r\n"
					+ "}";

			byte[] out = payload.getBytes(StandardCharsets.UTF_8);
			OutputStream stream = connection.getOutputStream();
			stream.write(out);
			stream.flush();

			TimeUnit.SECONDS.sleep(3);
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			//BufferedWriter writer = new BufferedWriter(new FileWriter("output3.txt"));

			//InputStream vastaus = connection.getInputStream();
			while((line = reader.readLine()) != null) {
				responseContent.append(line);
				//System.out.println(line)


			}

			//writer.write(responseContent.toString());
			//writer.close();

			String response = responseContent.toString();
			
			//System.out.println(responseContent.toString());
			reader.close();
			connection.disconnect();

			JSONManip(response);



		} catch (MalformedURLException ee) {
			
			ee.printStackTrace();
			System.out.println("Error");
		}



	}
	private static String JSONManip(String objst) {

		try {
			List<String> bussit = new ArrayList<String>();
			
			JSONObject jsonObject = new JSONObject(objst);

			JSONObject jso = jsonObject.getJSONObject("data").getJSONObject("viewer").getJSONObject("plan");
			JSONArray arr = jso.getJSONArray("itineraries"); // itineraries array
			
			// For loops for digging the nested JSON arrays, and getting the bus numbers
			for (int i = 0; i < arr.length(); i++) {

				JSONObject explr = arr.getJSONObject(i);

				JSONArray legArray = explr.getJSONArray("legs"); // legs array inside itineraries array
				 
				for (int j = 0; j < legArray.length(); j++) 
				{
					JSONObject explrtwo = legArray.getJSONObject(j);
					
					if(explrtwo.get("route").toString() != "null") {
						// Getting bus numbers
						
						
						String bus = explrtwo.get("route").toString();
						JSONObject busObj = new JSONObject(bus);
						
						bussit.add(busObj.getString("shortName"));
						
						System.out.println(bussit.get(bussit.size() - 1));
						
					}
				}
			    for (int f = 0; f < legArray.length(); f++) {
						
			    		JSONObject explrthree = legArray.getJSONObject(f);
			    		
			    		
			    		
			    		if(explrthree.has("mode")) {
			    			
			    			
							if(explrthree.getString("mode").equals("BUS") | explrthree.getString("mode").equals("WALK")) {
								
								long lahtoAika = explrthree.getLong("startTime");
								long saapumisAika = explrthree.getLong("endTime");
								
								System.out.println(explrthree.getLong("startTime"));
								System.out.println(explrthree.getLong("endTime"));
								ClockManip(lahtoAika, saapumisAika);
								
								
								
							}
							
							
							
						}else {
	
							break;
						}
					
				}

			}

		}catch(Exception e) {
			e.printStackTrace();

		};

		return null;

	}
	public static void ClockManip(long startTime, long endTime) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");

		String dateString = simpleDateFormat.format(startTime);
		String dateString2 = simpleDateFormat.format(endTime);
		System.out.println(dateString);
		System.out.println(dateString2);
		

	};
	public static String TimeFinder() {
		return null;

	}
}
