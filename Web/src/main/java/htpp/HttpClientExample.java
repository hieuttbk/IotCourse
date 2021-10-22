package htpp;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientExample {
    
    public static void main(String[] args) throws ClientProtocolException, IOException, InterruptedException {
      //  final String url = "http://localhost:5000/add?s1=10&s2=5";
    	final String url = "http://localhost:9999/test?name=hi";
        final CloseableHttpClient httpclient = HttpClients.createSystem();
 
        for (int i = 0; i < 1; i++) {
            new Thread(new Runnable() {
 
                public void run() {
                    try {
//                        HttpGet httpGet = new HttpGet(url);
//                        CloseableHttpResponse response = httpclient.execute(httpGet);
//                        
//                        System.out.println(response.toString()); 
//                                            
//                        HttpEntity entity = response.getEntity();
//                        
//                        System.out.println(EntityUtils.toString(entity, "UTF-8")); 
//                        
//                        EntityUtils.consume(entity);
//                                
//                        response.close();
                        

                                              
                        HttpPost httpPost = new HttpPost("http://localhost:9999/v1/temperatures");

                        String json = "{\"name\":\"httpNode1\",\"value\":\"27\"}";
                        StringEntity content = new StringEntity(json);
                        httpPost.setEntity(content);
      
                        CloseableHttpResponse resp = httpclient.execute(httpPost);
                        
                        System.out.println(resp.toString()); 
                                            
                        HttpEntity entityP = resp.getEntity();
                        
                        System.out.println(EntityUtils.toString(entityP, "UTF-8")); 
                        
                        EntityUtils.consume(entityP);
                                
                        resp.close();
                        
                        
                        
                       
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
 
        }
 
        Thread.sleep(100000);
    }
}
