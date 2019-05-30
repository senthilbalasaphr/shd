


import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties.ODataEntityProviderPropertiesBuilder;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataDeltaFeed;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.core.ep.EntityProviderProducerException;
import org.apache.olingo.odata2.core.ep.aggregator.EntityInfoAggregator;
import org.apache.olingo.odata2.core.ep.producer.JsonEntryEntityProducer;
import org.apache.olingo.odata2.core.ep.util.CircleStreamBuffer;

/**
 *
 */
public class OlingoV2Main {
  public static final String HTTP_METHOD_PUT = "PUT";
  public static final String HTTP_METHOD_POST = "POST";
  public static final String HTTP_METHOD_GET = "GET";
  private static final String HTTP_METHOD_DELETE = "DELETE";

  public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
  public static final String HTTP_HEADER_ACCEPT = "Accept";

  public static final String APPLICATION_JSON = "application/json";
  public static final String APPLICATION_XML = "application/xml";
  public static final String APPLICATION_ATOM_XML = "application/atom+xml";
  public static final String APPLICATION_FORM = "application/x-www-form-urlencoded";
  public static final String METADATA = "$metadata";
  public static final String INDEX = "/index.jsp";
  public static final String SEPARATOR = "/";

  public static final boolean PRINT_RAW_CONTENT = true;

  public static void main(String[] paras) throws Exception {
	  OlingoV2Main app = new OlingoV2Main();

//    String serviceUrl = "http://localhost:8080/cars-annotations-sample/MyFormula.svc";
    String serviceUrl = "http://localhost:8080/MyFormula.svc";
//    String usedFormat = APPLICATION_ATOM_XML;
    String usedFormat = APPLICATION_JSON;

    print("\n----- Generate sample data ------------------------------");
    app.generateSampleData(serviceUrl);

    print("\n----- Read Edm ------------------------------");
    Edm edm = app.readEdm(serviceUrl);
    print("Read default EntityContainer: " + edm.getDefaultEntityContainer().getName());

    print("\n----- Read Feed ------------------------------");
    ODataFeed feed = app.readFeed(edm, serviceUrl, usedFormat, "Manufacturers");

    print("Read: " + feed.getEntries().size() + " entries: ");
    for (ODataEntry entry : feed.getEntries()) {
      print("##########");
      print("Entry:\n" + prettyPrint(entry));
      print("##########");
    }

    print("\n----- Read Entry ------------------------------");
    ODataEntry entry = app.readEntry(edm, serviceUrl, usedFormat, "Manufacturers", "'1'");
    print("Single Entry:\n" + prettyPrint(entry));

    Map<String, Object> data = new HashMap<String, Object>();
    data.put("Id", "123");
    data.put("Name", "MyCarManufacturer");
    data.put("Founded", new Date());
    //
    Map<String, Object> address = new HashMap<String, Object>();
    address.put("Street", "Main");
    address.put("ZipCode", "42421");
    address.put("City", "Fairy City");
    address.put("Country", "FarFarAway");
    data.put("Address", address);

    //
    print("\n----- Read Entry with $expand  ------------------------------");
    ODataEntry entryExpanded = app.readEntry(edm, serviceUrl, usedFormat, "Manufacturers", "'1'", "Cars");
    print("Single Entry with expanded Cars relation:\n" + prettyPrint(entryExpanded));

    //
    //
    print("\n----- Create Entry ------------------------------");
    ODataEntry createdEntry = app.createEntry(edm, serviceUrl, usedFormat, "Manufacturers", data);
    print("Created Entry:\n" + prettyPrint(createdEntry));

    print("\n----- Update Entry ------------------------------");
    data.put("Name", "MyCarManufacturer Renamed");
    address.put("Street", "Main Street");
    app.updateEntry(edm, serviceUrl, usedFormat, "Manufacturers", "'123'", data);
    ODataEntry updatedEntry = app.readEntry(edm, serviceUrl, usedFormat, "Manufacturers", "'123'");
    print("Updated Entry successfully:\n" + prettyPrint(updatedEntry));

    //
    print("\n----- Delete Entry ------------------------------");
    HttpStatusCodes statusCode = app.deleteEntry(serviceUrl, "Manufacturers", "'123'");
    print("Deletion of Entry was successfully: " + statusCode.getStatusCode() + ": " + statusCode.getInfo());

    try {
      print("\n----- Verify Delete Entry ------------------------------");
      app.readEntry(edm, serviceUrl, usedFormat, "Manufacturers", "'123'");
    } catch(Exception e) {
      print(e.getMessage());
    }
  }

  public static void print(String content) {
    System.out.println(content);
  }

  public static String prettyPrint(ODataEntry createdEntry) {
    return prettyPrint(createdEntry.getProperties(), 0);
  }

  private static String prettyPrint(Map<String, Object> properties, int level) {
    StringBuilder b = new StringBuilder();
    Set<Entry<String, Object>> entries = properties.entrySet();

    for (Entry<String, Object> entry : entries) {
      intend(b, level);
      b.append(entry.getKey()).append(": ");
      Object value = entry.getValue();
      if(value instanceof Map) {
        value = prettyPrint((Map<String, Object>)value, level+1);
        b.append(value).append("\n");
      } else if(value instanceof Calendar) {
        Calendar cal = (Calendar) value;
        value = SimpleDateFormat.getInstance().format(cal.getTime());
        b.append(value).append("\n");
      } else if(value instanceof ODataDeltaFeed) {
        ODataDeltaFeed feed = (ODataDeltaFeed) value;
        List<ODataEntry> inlineEntries =  feed.getEntries();
        b.append("{");
        for (ODataEntry oDataEntry : inlineEntries) {
          value = prettyPrint((Map<String, Object>)oDataEntry.getProperties(), level+1);
          b.append("\n[\n").append(value).append("\n],");
        }
        b.deleteCharAt(b.length()-1);
        intend(b, level);
        b.append("}\n");
      } else {
        b.append(value).append("\n");
      }
    }
    // remove last line break
    b.deleteCharAt(b.length()-1);
    return b.toString();
  }

  private static void intend(StringBuilder builder, int intendLevel) {
    for (int i = 0; i < intendLevel; i++) {
      builder.append("  ");
    }
  }

  public void generateSampleData(String serviceUrl) throws MalformedURLException, IOException {
    String url = serviceUrl.substring(0, serviceUrl.lastIndexOf(SEPARATOR));
    HttpURLConnection connection = initializeConnection(url + INDEX, APPLICATION_FORM, HTTP_METHOD_POST);
    String content = "genSampleData=true";
    connection.getOutputStream().write(content.getBytes());
    print("Generate response: " + checkStatus(connection));
    connection.disconnect();
  }

  public Edm readEdm(String serviceUrl) throws IOException, ODataException {
    InputStream content = execute(serviceUrl + SEPARATOR + METADATA, APPLICATION_XML, HTTP_METHOD_GET);
    return EntityProvider.readMetadata(content, false);
  }

  public ODataFeed readFeed(Edm edm, String serviceUri, String contentType, String entitySetName)
      throws IOException, ODataException {
    EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
    String absolutUri = createUri(serviceUri, entitySetName, null);

    InputStream content = execute(absolutUri, contentType, HTTP_METHOD_GET);
    return EntityProvider.readFeed(contentType,
        entityContainer.getEntitySet(entitySetName),
        content,
        EntityProviderReadProperties.init().build());
  }

  public ODataEntry readEntry(Edm edm, String serviceUri, String contentType, String entitySetName, String keyValue)
      throws IOException, ODataException {
    return readEntry(edm, serviceUri, contentType, entitySetName, keyValue, null);
  }

  public ODataEntry readEntry(Edm edm, String serviceUri, String contentType, 
      String entitySetName, String keyValue, String expandRelationName)
      throws IOException, ODataException {
    // working with the default entity container
    EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
    // create absolute uri based on service uri, entity set name with its key property value and optional expanded relation name
    String absolutUri = createUri(serviceUri, entitySetName, keyValue, expandRelationName);

    InputStream content = execute(absolutUri, contentType, HTTP_METHOD_GET);

    return EntityProvider.readEntry(contentType,
        entityContainer.getEntitySet(entitySetName),
        content,
        EntityProviderReadProperties.init().build());
  }

  private InputStream logRawContent(String prefix, InputStream content, String postfix) throws IOException {
    if(PRINT_RAW_CONTENT) {
      byte[] buffer = streamToArray(content);
//      print(prefix + new String(buffer) + postfix);
      return new ByteArrayInputStream(buffer);
    }
    return content;
  }

  private byte[] streamToArray(InputStream stream) throws IOException {
    byte[] result = new byte[0];
    byte[] tmp = new byte[8192];
    int readCount = stream.read(tmp);
    while(readCount >= 0) {
      byte[] innerTmp = new byte[result.length + readCount];
      System.arraycopy(result, 0, innerTmp, 0, result.length);
      System.arraycopy(tmp, 0, innerTmp, result.length, readCount);
      result = innerTmp;
      readCount = stream.read(tmp);
    }
    stream.close();
    return result;
  }

  public ODataEntry createEntry(Edm edm, String serviceUri, String contentType, 
      String entitySetName, Map<String, Object> data) throws Exception {
    String absolutUri = createUri(serviceUri, entitySetName, null);
    return writeEntity(edm, absolutUri, entitySetName, data, contentType, HTTP_METHOD_POST);
  }

  public void updateEntry(Edm edm, String serviceUri, String contentType, String entitySetName, 
      String id, Map<String, Object> data) throws Exception {
    String absolutUri = createUri(serviceUri, entitySetName, id);
    writeEntity(edm, absolutUri, entitySetName, data, contentType, HTTP_METHOD_PUT);
  }

  public HttpStatusCodes deleteEntry(String serviceUri, String entityName, String id) throws IOException {
    String absolutUri = createUri(serviceUri, entityName, id);
    HttpURLConnection connection = connect(absolutUri, APPLICATION_XML, HTTP_METHOD_DELETE);
    return HttpStatusCodes.fromStatusCode(connection.getResponseCode());
  }


  private ODataEntry writeEntity(Edm edm, String absolutUri, String entitySetName, 
      Map<String, Object> data, String contentType, String httpMethod) 
      throws EdmException, MalformedURLException, IOException, EntityProviderException, URISyntaxException {

    HttpURLConnection connection = initializeConnection(absolutUri, contentType, httpMethod);

    EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
    EdmEntitySet entitySet = entityContainer.getEntitySet(entitySetName);
    URI rootUri = new URI(entitySetName);

    EntityProviderWriteProperties properties = EntityProviderWriteProperties.serviceRoot(rootUri).build();
    // serialize data into ODataResponse object
    ODataResponse response = EntityProvider.writeEntry(contentType, entitySet, data, properties);
    // get (http) entity which is for default Olingo implementation an InputStream
    Object entity = response.getEntity();
    if (entity instanceof InputStream) {
      byte[] buffer = streamToArray((InputStream) entity);
      // just for logging
      String content = new String(buffer);
      print(httpMethod + " request on uri '" + absolutUri + "' with content:\n  " + content + "\n");
      //
      connection.getOutputStream().write(buffer);
    }

    // if a entity is created (via POST request) the response body contains the new created entity
    ODataEntry entry = null;
    HttpStatusCodes statusCode = HttpStatusCodes.fromStatusCode(connection.getResponseCode());
    if(statusCode == HttpStatusCodes.CREATED) {
      // get the content as InputStream and de-serialize it into an ODataEntry object
      InputStream content = connection.getInputStream();
      content = logRawContent(httpMethod + " request on uri '" + absolutUri + "' with content:\n  ", content, "\n");
      entry = EntityProvider.readEntry(contentType,
          entitySet, content, EntityProviderReadProperties.init().build());
    }

    //
    connection.disconnect();

    return entry;
  }

  private HttpStatusCodes checkStatus(HttpURLConnection connection) throws IOException {
    HttpStatusCodes httpStatusCode = HttpStatusCodes.fromStatusCode(connection.getResponseCode());
    if (400 <= httpStatusCode.getStatusCode() && httpStatusCode.getStatusCode() <= 599) {
      throw new RuntimeException("Http Connection failed with status " + httpStatusCode.getStatusCode() + " " + httpStatusCode.toString());
    }
    return httpStatusCode;
  }

  private String createUri(String serviceUri, String entitySetName, String id) {
    return createUri(serviceUri, entitySetName, id, null);
  }

  private String createUri(String serviceUri, String entitySetName, String id, String expand) {
    final StringBuilder absolutUri = new StringBuilder(serviceUri).append(SEPARATOR).append(entitySetName);
    if(id != null) {
      absolutUri.append("(").append(id).append(")");
    }
    if(expand != null) {
      absolutUri.append("/?$expand=").append(expand);
    }
    return absolutUri.toString();
  }

  private InputStream execute(String relativeUri, String contentType, String httpMethod) throws IOException {
    HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod);

    connection.connect();
    checkStatus(connection);

    InputStream content = connection.getInputStream();
    content = logRawContent(httpMethod + " request on uri '" + relativeUri + "' with content:\n  ", content, "\n");
    return content;
  }

  private HttpURLConnection connect(String relativeUri, String contentType, String httpMethod) throws IOException {
    HttpURLConnection connection = initializeConnection(relativeUri, contentType, httpMethod);

    connection.connect();
    checkStatus(connection);

    return connection;
  }

  private HttpURLConnection initializeConnection(String absolutUri, String contentType, String httpMethod)
      throws MalformedURLException, IOException {
    URL url = new URL(absolutUri);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    connection.setRequestMethod(httpMethod);
    connection.setRequestProperty(HTTP_HEADER_ACCEPT, contentType);
    if(HTTP_METHOD_POST.equals(httpMethod) || HTTP_METHOD_PUT.equals(httpMethod)) {
      connection.setDoOutput(true);
      connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, contentType);
    }

    return connection;
  }
  
  private HttpURLConnection initializeConnectionSf(String absolutUri, String contentType, String httpMethod,String UserId,String Password)
	      throws MalformedURLException, IOException {
	    URL url = new URL(absolutUri);
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	    String userpass = UserId + ":" + Password;
	    String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));
	    connection.setRequestProperty ("Authorization", basicAuth);
	    
	    
	    
	    connection.setRequestMethod(httpMethod);
	    connection.setRequestProperty(HTTP_HEADER_ACCEPT, contentType);
	    if(HTTP_METHOD_POST.equals(httpMethod) || HTTP_METHOD_PUT.equals(httpMethod)) {
	      connection.setDoOutput(true);
	      connection.setRequestProperty(HTTP_HEADER_CONTENT_TYPE, contentType);
	    }

	    return connection;
	  }
  
  
  private InputStream executeSf(String relativeUri, String contentType, String httpMethod,String UserId,String Password) throws IOException {
	    HttpURLConnection connection = initializeConnectionSf(relativeUri, contentType, httpMethod,UserId,Password);

	    connection.connect();
	    checkStatus(connection);

	    InputStream content = connection.getInputStream();
	    content = logRawContent(httpMethod + " request on uri '" + relativeUri + "' with content:\n  ", content, "\n");
	    return content;
	  }
  
  public Edm readEdmSf(String serviceUrl,String UserId,String Password) throws IOException, ODataException {
	    InputStream content = executeSf(serviceUrl + SEPARATOR + METADATA, APPLICATION_XML, HTTP_METHOD_GET, UserId, Password);
	    return EntityProvider.readMetadata(content, false);
	  } 

  public ODataFeed readFeedSf(Edm edm, String serviceUri, String contentType, String entitySetName,String UserId,String Password)
	      throws IOException, ODataException {
	    EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
	    String absolutUri = createUri(serviceUri, entitySetName, null);
	    	
	    InputStream content = executeSf(absolutUri, contentType, HTTP_METHOD_GET,UserId,Password);

	    return EntityProvider.readFeed(contentType,
	        entityContainer.getEntitySet(entitySetName),
	        content,
	        EntityProviderReadProperties.init().build());
	  }

  public ODataEntry writeEntitySf(Edm edm, String absolutUri, String entitySetName, 
	      Map<String, Object> data, String contentType, String httpMethod,String UserId,String Password ) 
	      throws EdmException, MalformedURLException, IOException, EntityProviderException, URISyntaxException {

	    absolutUri="https://api8preview.sapsf.com/odata/v2/upsert";
	    HttpURLConnection connection = initializeConnectionSf(absolutUri, contentType, httpMethod,UserId,Password);
	    
	    EdmEntityContainer entityContainer = edm.getDefaultEntityContainer();
	    EdmEntitySet entitySet = entityContainer.getEntitySet(entitySetName);
	    URI rootUri = new URI("https://api8preview.sapsf.com:443/odata/v2/");
//	    EntityProviderWriteProperties properties1 = EntityProviderWriteProperties.serviceRoot(rootUri).om;
	    
	    
	    // serialize data into ODataResponse object
	


	  
	  ODataEntityProviderPropertiesBuilder r = new ODataEntityProviderPropertiesBuilder();
	  r.omitJsonWrapper(true);
	  r.omitInlineForNullData(true);
	  r.omitETag(true);
	  r.serviceRoot(rootUri);
	  r.isDataBasedPropertySerialization(true);
	  
  
	  
	  EntityProviderWriteProperties properties = r.build();
	  

	  
	    EntityInfoAggregator entityInfo = EntityInfoAggregator.create(entitySet, properties.getExpandSelectTree());
	    EdmEntityType type = entityInfo.getEntityType();

	  
	  
	    
	    ODataResponse response = EntityProvider.writeEntry(contentType, entitySet, data, properties);
	    
	    // get (http) entity which is for default Olingo implementation an InputStream
	    Object entity = response.getEntity();
	    
	    
	    if (entity instanceof InputStream) {
	      byte[] buffer = streamToArray((InputStream) entity);
	      // just for logging
	      String content = new String(buffer);
	     print(httpMethod + " request on uri '" + absolutUri + "' with content:\n  " + content + "\n");
	      
	      
	      connection.getOutputStream().write(buffer);
	    }

	    // if a entity is created (via POST request) the response body contains the new created entity
	    ODataEntry entry = null;
	    HttpStatusCodes statusCode = HttpStatusCodes.fromStatusCode(connection.getResponseCode());
	    if(statusCode == HttpStatusCodes.CREATED) {
	      // get the content as InputStream and de-serialize it into an ODataEntry object
	      InputStream content = connection.getInputStream();
	      content = logRawContent(httpMethod + " request on uri '" + absolutUri + "' with content:\n  ", content, "\n");
	      entry = EntityProvider.readEntry(contentType,
	          entitySet, content, EntityProviderReadProperties.init().build());
	    }

	    //
	    connection.disconnect();

	    return entry;
	  }
  
  
  public ODataEntry createEntrySf(Edm edm, String serviceUri, String contentType, 
	      String entitySetName, Map<String, Object> data,String UserId,String Password) throws Exception {
	    String absolutUri = createUri(serviceUri, entitySetName, null);
	    System.out.print(absolutUri);
	  
	    return writeEntitySf(edm, absolutUri, entitySetName, data, contentType, HTTP_METHOD_POST,UserId,Password);
  }
	   
  
  public ODataResponse writeEntry(final EdmEntitySet entitySet, final Map<String, Object> data,
	      final EntityProviderWriteProperties properties) throws EntityProviderException {
	    final EntityInfoAggregator entityInfo = EntityInfoAggregator.create(entitySet, properties.getExpandSelectTree());
	    CircleStreamBuffer buffer = new CircleStreamBuffer();

	    try {
	      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(buffer.getOutputStream(), "UTF-8"));
	      JsonEntryEntityProducer producer = new JsonEntryEntityProducer(properties);
	      producer.append(writer, entityInfo, data, false);
	      writer.flush();
	      buffer.closeWrite();

	      return ODataResponse.entity(buffer.getInputStream())
	          .eTag(producer.getETag())
	          .idLiteral(producer.getLocation())
	          .build();
	    } catch (EntityProviderException e) {
	      buffer.close();
	      throw e;
	    } catch (Exception e) {
	      buffer.close();
	      throw new EntityProviderProducerException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
	          .getSimpleName()), e);
	    }
	  }


  
  
}