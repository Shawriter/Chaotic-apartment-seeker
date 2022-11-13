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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;


public class DistanceEst {

	final static String apiUrl = "https://api.digitransit.fi/routing/v1/routers/hsl/index/graphql";
	
	static List<Float> busTriplengthTotal = new ArrayList<Float>();
	static List<Integer> busses = new ArrayList<Integer>();
	static List<Integer> MinutesStartAndEndList = new ArrayList<Integer>();
	static List<String> DateStringList = new ArrayList<String>();
	static Map<Integer, TimeDataandAddr> dimensioncollection = new HashMap<Integer, TimeDataandAddr>();
	
	static float timefindercalc = 0;
	static int globalbuscounter;
	static int dimensionobjcalc;

	public static void APIposter(String osoite2, String[] kordinaatit2, List<String> osoitteet) throws IOException, InterruptedException{

		BufferedReader reader;
		String line;

		StringBuffer responseContent = new StringBuffer();

		try {

			URL url = new URL(apiUrl);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type","application/json");
			connection.setRequestProperty("Accept", "application/json");
			//JSON PAYLOAD REPLACE THE DATE AND TIME WITH A FUTURE TIME ALSO REPLACE toPlace ADDRESS LINES WITH YOUR DESTINATION AND COORDINATES
			String payload = "{\r\n"
					+ "    \"id\": \"queryUtils_SummaryPage_Query\",\r\n"
					+ "    \"query\": \"query queryUtils_SummaryPage_Query(\\n  $fromPlace: String!\\n  $toPlace: String!\\n  $intermediatePlaces: [InputCoordinates!]\\n  $numItineraries: Int!\\n  $modes: [TransportMode!]\\n  $date: String!\\n  $time: String!\\n  $walkReluctance: Float\\n  $walkBoardCost: Int\\n  $minTransferTime: Int\\n  $walkSpeed: Float\\n  $maxWalkDistance: Float\\n  $wheelchair: Boolean\\n  $ticketTypes: [String]\\n  $disableRemainingWeightHeuristic: Boolean\\n  $arriveBy: Boolean\\n  $transferPenalty: Int\\n  $bikeSpeed: Float\\n  $optimize: OptimizeType\\n  $itineraryFiltering: Float\\n  $unpreferred: InputUnpreferred\\n  $allowedBikeRentalNetworks: [String]\\n  $locale: String\\n) {\\n  viewer {\\n    ...SummaryPage_viewer_3rqNOH\\n  }\\n  serviceTimeRange {\\n    ...SummaryPage_serviceTimeRange\\n  }\\n}\\n\\nfragment ItineraryLine_legs on Leg {\\n  mode\\n  rentedBike\\n  startTime\\n  endTime\\n  distance\\n  legGeometry {\\n    points\\n  }\\n  transitLeg\\n  interlineWithPreviousLeg\\n  route {\\n    shortName\\n    color\\n    type\\n    agency {\\n      name\\n      id\\n    }\\n    id\\n  }\\n  from {\\n    lat\\n    lon\\n    name\\n    vertexType\\n    bikeRentalStation {\\n      lat\\n      lon\\n      stationId\\n      networks\\n      bikesAvailable\\n      id\\n    }\\n    stop {\\n      gtfsId\\n      code\\n      platformCode\\n      id\\n    }\\n  }\\n  to {\\n    lat\\n    lon\\n    name\\n    vertexType\\n    bikeRentalStation {\\n      lat\\n      lon\\n      stationId\\n      networks\\n      bikesAvailable\\n      id\\n    }\\n    stop {\\n      gtfsId\\n      code\\n      platformCode\\n      id\\n    }\\n  }\\n  trip {\\n    stoptimes {\\n      stop {\\n        gtfsId\\n        id\\n      }\\n      pickupType\\n    }\\n    id\\n  }\\n  intermediatePlaces {\\n    arrivalTime\\n    stop {\\n      gtfsId\\n      lat\\n      lon\\n      name\\n      code\\n      platformCode\\n      id\\n    }\\n  }\\n}\\n\\nfragment ItinerarySummaryListContainer_itineraries on Itinerary {\\n  walkDistance\\n  startTime\\n  endTime\\n  legs {\\n    realTime\\n    realtimeState\\n    transitLeg\\n    startTime\\n    endTime\\n    mode\\n    distance\\n    duration\\n    rentedBike\\n    interlineWithPreviousLeg\\n    intermediatePlace\\n    intermediatePlaces {\\n      stop {\\n        zoneId\\n        id\\n      }\\n    }\\n    route {\\n      mode\\n      shortName\\n      type\\n      color\\n      agency {\\n        name\\n        id\\n      }\\n      alerts {\\n        alertSeverityLevel\\n        effectiveEndDate\\n        effectiveStartDate\\n        trip {\\n          pattern {\\n            code\\n            id\\n          }\\n          id\\n        }\\n        id\\n      }\\n      id\\n    }\\n    trip {\\n      pattern {\\n        code\\n        id\\n      }\\n      stoptimes {\\n        realtimeState\\n        stop {\\n          gtfsId\\n          id\\n        }\\n        pickupType\\n      }\\n      id\\n    }\\n    from {\\n      name\\n      lat\\n      lon\\n      stop {\\n        gtfsId\\n        zoneId\\n        platformCode\\n        alerts {\\n          alertSeverityLevel\\n          effectiveEndDate\\n          effectiveStartDate\\n          id\\n        }\\n        id\\n      }\\n      bikeRentalStation {\\n        bikesAvailable\\n        networks\\n        id\\n      }\\n    }\\n    to {\\n      stop {\\n        gtfsId\\n        zoneId\\n        alerts {\\n          alertSeverityLevel\\n          effectiveEndDate\\n          effectiveStartDate\\n          id\\n        }\\n        id\\n      }\\n      bikePark {\\n        bikeParkId\\n        name\\n        id\\n      }\\n      carPark {\\n        carParkId\\n        name\\n        id\\n      }\\n    }\\n  }\\n}\\n\\nfragment ItineraryTab_itinerary on Itinerary {\\n  walkDistance\\n  duration\\n  startTime\\n  endTime\\n  fares {\\n    cents\\n    components {\\n      cents\\n      fareId\\n      routes {\\n        agency {\\n          gtfsId\\n          fareUrl\\n          name\\n          id\\n        }\\n        gtfsId\\n        id\\n      }\\n    }\\n    type\\n  }\\n  legs {\\n    mode\\n    ...LegAgencyInfo_leg\\n    from {\\n      lat\\n      lon\\n      name\\n      vertexType\\n      bikePark {\\n        bikeParkId\\n        name\\n        id\\n      }\\n      bikeRentalStation {\\n        networks\\n        bikesAvailable\\n        lat\\n        lon\\n        stationId\\n        id\\n      }\\n      stop {\\n        gtfsId\\n        code\\n        platformCode\\n        vehicleMode\\n        zoneId\\n        alerts {\\n          alertSeverityLevel\\n          effectiveEndDate\\n          effectiveStartDate\\n          trip {\\n            pattern {\\n              code\\n              id\\n            }\\n            id\\n          }\\n          alertHeaderText\\n          alertHeaderTextTranslations {\\n            text\\n            language\\n          }\\n          alertDescriptionText\\n          alertDescriptionTextTranslations {\\n            text\\n            language\\n          }\\n          alertUrl\\n          alertUrlTranslations {\\n            text\\n            language\\n          }\\n          id\\n        }\\n        id\\n      }\\n    }\\n    to {\\n      lat\\n      lon\\n      name\\n      vertexType\\n      bikeRentalStation {\\n        lat\\n        lon\\n        stationId\\n        networks\\n        bikesAvailable\\n        id\\n      }\\n      stop {\\n        gtfsId\\n        code\\n        platformCode\\n        zoneId\\n        name\\n        vehicleMode\\n        alerts {\\n          alertSeverityLevel\\n          effectiveEndDate\\n          effectiveStartDate\\n          trip {\\n            pattern {\\n              code\\n              id\\n            }\\n            id\\n          }\\n          alertHeaderText\\n          alertHeaderTextTranslations {\\n            text\\n            language\\n          }\\n          alertDescriptionText\\n          alertDescriptionTextTranslations {\\n            text\\n            language\\n          }\\n          alertUrl\\n          alertUrlTranslations {\\n            text\\n            language\\n          }\\n          id\\n        }\\n        id\\n      }\\n      bikePark {\\n        bikeParkId\\n        name\\n        id\\n      }\\n      carPark {\\n        carParkId\\n        name\\n        id\\n      }\\n    }\\n    legGeometry {\\n      length\\n      points\\n    }\\n    intermediatePlaces {\\n      arrivalTime\\n      stop {\\n        gtfsId\\n        lat\\n        lon\\n        name\\n        code\\n        platformCode\\n        zoneId\\n        id\\n      }\\n    }\\n    realTime\\n    realtimeState\\n    transitLeg\\n    rentedBike\\n    startTime\\n    endTime\\n    interlineWithPreviousLeg\\n    distance\\n    duration\\n    intermediatePlace\\n    route {\\n      shortName\\n      color\\n      gtfsId\\n      type\\n      longName\\n      desc\\n      agency {\\n        gtfsId\\n        fareUrl\\n        name\\n        phone\\n        id\\n      }\\n      alerts {\\n        alertSeverityLevel\\n        effectiveEndDate\\n        effectiveStartDate\\n        trip {\\n          pattern {\\n            code\\n            id\\n          }\\n          id\\n        }\\n        alertHeaderText\\n        alertHeaderTextTranslations {\\n          text\\n          language\\n        }\\n        alertDescriptionText\\n        alertDescriptionTextTranslations {\\n          text\\n          language\\n        }\\n        alertUrl\\n        alertUrlTranslations {\\n          text\\n          language\\n        }\\n        id\\n      }\\n      id\\n    }\\n    trip {\\n      gtfsId\\n      tripHeadsign\\n      pattern {\\n        code\\n        id\\n      }\\n      stoptimesForDate {\\n        headsign\\n        pickupType\\n        realtimeState\\n        stop {\\n          gtfsId\\n          id\\n        }\\n      }\\n      id\\n    }\\n  }\\n}\\n\\nfragment ItineraryTab_plan on Plan {\\n  date\\n}\\n\\nfragment LegAgencyInfo_leg on Leg {\\n  agency {\\n    name\\n    url\\n    fareUrl\\n    id\\n  }\\n}\\n\\nfragment RouteLine_pattern on Pattern {\\n  code\\n  geometry {\\n    lat\\n    lon\\n  }\\n  route {\\n    mode\\n    type\\n    color\\n    id\\n  }\\n  stops {\\n    lat\\n    lon\\n    name\\n    gtfsId\\n    platformCode\\n    code\\n    ...StopCardHeaderContainer_stop\\n    id\\n  }\\n}\\n\\nfragment StopCardHeaderContainer_stop on Stop {\\n  gtfsId\\n  name\\n  code\\n  desc\\n  zoneId\\n  alerts {\\n    alertSeverityLevel\\n    effectiveEndDate\\n    effectiveStartDate\\n    id\\n  }\\n  lat\\n  lon\\n  stops {\\n    name\\n    desc\\n    id\\n  }\\n}\\n\\nfragment SummaryPage_serviceTimeRange on serviceTimeRange {\\n  start\\n  end\\n}\\n\\nfragment SummaryPage_viewer_3rqNOH on QueryType {\\n  plan(fromPlace: $fromPlace, toPlace: $toPlace, intermediatePlaces: $intermediatePlaces, numItineraries: $numItineraries, transportModes: $modes, date: $date, time: $time, walkReluctance: $walkReluctance, walkBoardCost: $walkBoardCost, minTransferTime: $minTransferTime, walkSpeed: $walkSpeed, maxWalkDistance: $maxWalkDistance, wheelchair: $wheelchair, allowedTicketTypes: $ticketTypes, disableRemainingWeightHeuristic: $disableRemainingWeightHeuristic, arriveBy: $arriveBy, transferPenalty: $transferPenalty, bikeSpeed: $bikeSpeed, optimize: $optimize, itineraryFiltering: $itineraryFiltering, unpreferred: $unpreferred, allowedBikeRentalNetworks: $allowedBikeRentalNetworks, locale: $locale) {\\n    ...SummaryPlanContainer_plan\\n    ...ItineraryTab_plan\\n    itineraries {\\n      startTime\\n      endTime\\n      ...ItineraryTab_itinerary\\n      ...SummaryPlanContainer_itineraries\\n      legs {\\n        mode\\n        ...ItineraryLine_legs\\n        transitLeg\\n        legGeometry {\\n          points\\n        }\\n        route {\\n          gtfsId\\n          type\\n          shortName\\n          id\\n        }\\n        trip {\\n          gtfsId\\n          directionId\\n          stoptimesForDate {\\n            scheduledDeparture\\n            pickupType\\n          }\\n          pattern {\\n            ...RouteLine_pattern\\n            id\\n          }\\n          id\\n        }\\n        from {\\n          name\\n          lat\\n          lon\\n          stop {\\n            gtfsId\\n            zoneId\\n            id\\n          }\\n          bikeRentalStation {\\n            bikesAvailable\\n            networks\\n            id\\n          }\\n        }\\n        to {\\n          stop {\\n            gtfsId\\n            zoneId\\n            id\\n          }\\n          bikePark {\\n            bikeParkId\\n            name\\n            id\\n          }\\n        }\\n      }\\n    }\\n  }\\n}\\n\\nfragment SummaryPlanContainer_itineraries on Itinerary {\\n  ...ItinerarySummaryListContainer_itineraries\\n  endTime\\n  startTime\\n  legs {\\n    mode\\n    to {\\n      bikePark {\\n        bikeParkId\\n        name\\n        id\\n      }\\n    }\\n    ...ItineraryLine_legs\\n    transitLeg\\n    legGeometry {\\n      points\\n    }\\n    route {\\n      gtfsId\\n      id\\n    }\\n    trip {\\n      gtfsId\\n      directionId\\n      stoptimesForDate {\\n        scheduledDeparture\\n      }\\n      pattern {\\n        ...RouteLine_pattern\\n        id\\n      }\\n      id\\n    }\\n  }\\n}\\n\\nfragment SummaryPlanContainer_plan on Plan {\\n  date\\n  itineraries {\\n    startTime\\n    endTime\\n    legs {\\n      mode\\n      ...ItineraryLine_legs\\n      transitLeg\\n      legGeometry {\\n        points\\n      }\\n      route {\\n        gtfsId\\n        id\\n      }\\n      trip {\\n        gtfsId\\n        directionId\\n        stoptimesForDate {\\n          scheduledDeparture\\n          pickupType\\n        }\\n        pattern {\\n          ...RouteLine_pattern\\n          id\\n        }\\n        id\\n      }\\n      from {\\n        name\\n        lat\\n        lon\\n        stop {\\n          gtfsId\\n          zoneId\\n          id\\n        }\\n        bikeRentalStation {\\n          bikesAvailable\\n          networks\\n          id\\n        }\\n      }\\n      to {\\n        stop {\\n          gtfsId\\n          zoneId\\n          id\\n        }\\n        bikePark {\\n          bikeParkId\\n          name\\n          id\\n        }\\n      }\\n    }\\n  }\\n}\\n\",\r\n"
					+ "    \"variables\": {\r\n"
					+ "        \"fromPlace\": \""+ osoite2 +", City::" + kordinaatit2[0]+","+kordinaatit2[1]+"\",\r\n"
					+ "        \"toPlace\": \"Koskelonkuja 1, Espoo::60.262691,24.724533\",\r\n"
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
					+ "        \"date\": \"2022-11-14\",\r\n"
					+ "        \"time\": \"06:30:00\",\r\n"
					+ "        \"walkReluctance\": 2,\r\n"
					+ "        \"walkBoardCost\": 600,\r\n"
					+ "        \"minTransferTime\": 120,\r\n"
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
					+ "    \"fromPlace\": \""+ osoite2 +", City::"+ kordinaatit2[0]+","+kordinaatit2[1]+"\",\r\n"
					+ "    \"toPlace\": \"Koskelonkuja 1, Espoo::60.262691,24.724533\",\r\n"
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
					+ "    \"date\": \"2022-11-06\",\r\n"
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
			

			while((line = reader.readLine()) != null) {
				responseContent.append(line);
				//System.out.println(line)
				
			}
			String response = responseContent.toString();
			
			//System.out.println(responseContent.toString());
			reader.close();
			connection.disconnect();

			JSONManip(response, osoite2, osoitteet);

		} catch (Exception ErrorJSONPayload) {
			
			ErrorJSONPayload.printStackTrace();
			System.out.println("Error");
		}

	}
	private static String JSONManip(String objst, String osoite2, List<String> osoitteet) {

		try {
			List<String> bussit = new ArrayList<String>();
			
		
			JSONObject jsonObject = new JSONObject(objst);
			
			JSONObject jso = jsonObject.getJSONObject("data").getJSONObject("viewer").getJSONObject("plan");
			JSONArray arr = jso.getJSONArray("itineraries"); // itineraries array
			
			// For loops for digging the nested JSON arrays, and getting the bus numbers
			for (int i = 0; i < arr.length(); i++) {

				JSONObject explr = arr.getJSONObject(i);

				JSONArray legArray = explr.getJSONArray("legs"); // legs array inside itineraries array
				
				int buscounter = 0;
				
				for (int j = 0; j < legArray.length(); j++) 
				{
					JSONObject explrtwo = legArray.getJSONObject(j);
					
					if(explrtwo.get("route").toString() != "null") {
						// Getting bus numbers
						
						
						String bus = explrtwo.get("route").toString();
						JSONObject busObj = new JSONObject(bus);
						
						bussit.add(busObj.getString("shortName"));
						
						
						buscounter++;

					    
						//System.out.println(bussit.get(bussit.size() - 1));
						
						
					}
					
				}
			    for (int f = 0; f < legArray.length(); f++) {
						
			    		JSONObject explrthree = legArray.getJSONObject(f);
			    		
			    		boolean run = false;
			    		
			    		if(explrthree.has("mode")) {
			    			
			    			
							if(explrthree.getString("mode").equals("BUS") | explrthree.getString("mode").equals("WALK")) {
								
								long lahtoAika = explrthree.getLong("startTime");
								long saapumisAika = explrthree.getLong("endTime");
								
								if(!run && buscounter >= 1){
									
									//System.out.println();
									run = true;
									
								}
								//System.out.println(explrthree.getLong("startTime"));
								//System.out.println(explrthree.getLong("endTime"));
								
								ClockManip(lahtoAika, saapumisAika);
		
							}
				
						}else {
							
							break;
						}
				}
			    //System.out.println();
			    TimeFinder(buscounter, osoite2);
			    busses.add(buscounter);
			    globalbuscounter = buscounter;
			}
		//System.out.println("Entering average");
		float sumtotalminutes2 = CumulativeMinutesGet();
		float average = Average(osoite2, globalbuscounter, sumtotalminutes2);
		CumulativeMinutesGet();
		dimensioncollectionObjects(average, osoite2, osoitteet, sumtotalminutes2);
		
		}catch(Exception ErrorJSONManip) {
			ErrorJSONManip.printStackTrace();
		};
		return null;
	}
	private static void ClockManip(long startTime, long endTime) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
	
		String dateString = simpleDateFormat.format(startTime); // Converting epoch times to datestrings
		String dateString2 = simpleDateFormat.format(endTime);
		
		String minutes1 = dateString.substring(3,5);
		String minutes2 = dateString2.substring(3,5);
		//System.out.println(dateString);
		//System.out.println(dateString2);
		
		int minutes1Int = Integer.parseInt(minutes1);
		int minutes2Int = Integer.parseInt(minutes2);
		
		DateStringList.add(dateString);
		DateStringList.add(dateString2);
		
		MinutesStartAndEndList.add(minutes1Int);
		MinutesStartAndEndList.add(minutes2Int);
		
	};
	
	private static void TimeFinder(int buscounter, String osoite2) {
		
		//Find out the time difference between startTime and endTime
		float firsthourminutes;
		float secondhourminutes;
		float totalminutesDuration = 0;
		
		int firsthour = Integer.parseInt((DateStringList.get(0).substring(0,2)));
		int secondhour = Integer.parseInt((DateStringList.get(DateStringList.size()-1).substring(0,2)));
		
		int subtractionhours = secondhour - firsthour;
		
		int minutes3Int = MinutesStartAndEndList.get(0);
		int minutes4Int = MinutesStartAndEndList.get(MinutesStartAndEndList.size()-1);
		
		float minutes3IntFloat = (float)minutes3Int;
		float minutes4IntFloat = (float)minutes4Int;
		
		
		
		if(subtractionhours == 0) {
			
				firsthourminutes = minutes3IntFloat;
				secondhourminutes = minutes4IntFloat;
				totalminutesDuration = secondhourminutes - firsthourminutes;

		}
		else if(subtractionhours == 1) {
				
				firsthourminutes = ((1-(minutes3IntFloat/60))*60);
				secondhourminutes = minutes4IntFloat;
				totalminutesDuration = firsthourminutes + secondhourminutes;
	
		}else if(subtractionhours == 2){
			
				firsthourminutes = ((1-(minutes3IntFloat/60))*60);
				secondhourminutes = minutes4IntFloat;
				totalminutesDuration = firsthourminutes + secondhourminutes + 60;
	
		}else if(subtractionhours == 3) {
				
				firsthourminutes = ((1-(minutes3IntFloat/60))*60);
				secondhourminutes = minutes4IntFloat;
				totalminutesDuration = firsthourminutes + secondhourminutes + 60*2;
	
		}else if(subtractionhours == 4){
			
				firsthourminutes = ((1-(minutes3IntFloat/60))*60);
				secondhourminutes = minutes4IntFloat;
				totalminutesDuration = firsthourminutes + secondhourminutes + 60*3;
				
		}
		busTriplengthTotal.add(totalminutesDuration);
		
		timefindercalc++;
	
		DateStringList.clear();
		MinutesStartAndEndList.clear();
		
		
	}
	private static float Average(String osoite2, int globalbuscounter, float sumtotalminutes2){

		//Finding the average bus trip length in minutes
		float avg;
		float minarrlength = timefindercalc;

		if(busTriplengthTotal == null | minarrlength == 0) {
			
			avg = 404;
		}
		else {
			
			avg = sumtotalminutes2 / minarrlength;

		}
		
		minarrlength = 0;
		busTriplengthTotal.clear();
		timefindercalc = 0;
		return avg;
		
	}
	private static float CumulativeMinutesGet() {
		float sumtotalminutes = 0;
		for(float sum : busTriplengthTotal) {
			sumtotalminutes += sum;
		}
		return sumtotalminutes;
	}
	private static void dimensioncollectionObjects(Float average, String osoite2, List<String> osoitteet, float sumtotalminutes2){
		
		try {
			
			float weight = average / sumtotalminutes2;
			int osoitteetPos = osoitteet.indexOf(osoite2);
			int osoitteetPosnull = -1;
			
			if(osoitteetPos != osoitteetPosnull) {
				  
				  dimensioncollection.put(osoitteetPos , new TimeDataandAddr(osoite2, average , busses.get(osoitteetPos), sumtotalminutes2, weight));
				  TimeDataandAddr getAddrobj = (TimeDataandAddr)dimensioncollection.get(osoitteetPos);
				  System.out.println(getAddrobj.address);
				  System.out.println(getAddrobj.Time);
				  System.out.println(getAddrobj.busses);
				  System.out.println(getAddrobj.CumulativeMinutesAttr);
				  System.out.println(getAddrobj.weight);

			}

		}
		catch(Exception errorinaddresses){
			System.out.println(errorinaddresses);
			System.out.println("Error in dimensioncollectionObjects method");
		}
		
		
	}
	public static void finalWriteOut_to_File(List<String> osoitteet) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("Addresses_and_averages.txt"));
			
		for(int aa = 0; aa < osoitteet.size(); aa++) {
			
			TimeDataandAddr getAddrobj = (TimeDataandAddr)dimensioncollection.get(aa);
			
			if (getAddrobj != null && writer != null) {
				writer.write("Destination:"+getAddrobj.address+"\n"+"Avg.time duration:"+getAddrobj.Time+"\n"+"Busses amount:"+getAddrobj.busses+"\n"+"Cumulated minutes:"+getAddrobj.CumulativeMinutesAttr+"\n"+"Weighted number:"+getAddrobj.weight+"\n\n");
				}
			else {
				continue;
				
			 }
			}
	
	writer.flush();
	writer.close();
		
	}
 }

	
