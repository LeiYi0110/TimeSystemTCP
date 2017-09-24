package com.bjxc.supervise.http.crypto;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjxc.Result;
import com.bjxc.json.JacksonBinder;

@Service
public class HttpClientService {
	private static final Logger logger = LoggerFactory
			.getLogger(HttpClientService.class);

	@Autowired
	private CloseableHttpClient httpClient;
	@Autowired
	private RequestConfig requestConfig;

	/**
	 * ִ��GET����
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public Result doGet(String url) throws ClientProtocolException, IOException {
		// ����http GET����
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(this.requestConfig);
		Result result = new Result();
		CloseableHttpResponse response = null;
		try {
			// ִ������
			response = httpClient.execute(httpGet);
			/*
			// �жϷ���״̬�Ƿ�Ϊ200
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			*/
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				String responseText = EntityUtils.toString(
						response.getEntity(), "UTF-8");
				logger.info(responseText);
				Map<String, String> responseMap = JacksonBinder
						.buildNonDefaultBinder().fromJson(responseText,
								Map.class);
				result.putAll(responseMap);
			} else {
				result.error(statusCode, response.getStatusLine()
						.getReasonPhrase());
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return result;
	}
	
	//忽略返回直接发送get请求
	public void doSimpleGet(String url) throws ClientProtocolException, IOException {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(this.requestConfig);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	/**
	 * ���в�����GET����
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public Result doGet(String url, Map<String, String> params)
			throws ClientProtocolException, IOException, URISyntaxException {
		URIBuilder uriBuilder = new URIBuilder(url);
		for (String key : params.keySet()) {
			uriBuilder.addParameter(key, params.get(key));
		}
		return this.doGet(uriBuilder.build().toString());
	}

	/**
	 * ִ��POST����
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public Result doPost(String url, Map<String, String> params)
			throws IOException {
		// ����http POST����
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(this.requestConfig);
		Result result = new Result();
		if (params != null) {
			// ����2��post������һ����scope��һ����q
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				parameters.add(new BasicNameValuePair(key, (String) params
						.get(key)));
			}
			// ����һ��form��ʽ��ʵ��
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					parameters, "UTF-8");
			// ������ʵ�����õ�httpPost������
			httpPost.setEntity(formEntity);
		}

		CloseableHttpResponse response = null;
		try {
			// ִ������
			response = httpClient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				String responseText = EntityUtils.toString(
						response.getEntity(), "UTF-8");
				logger.info(responseText);
				Map<String, String> responseMap = JacksonBinder
						.buildNonDefaultBinder().fromJson(responseText,
								Map.class);
				result.putAll(responseMap);
			} else {
				result.error(statusCode, response.getStatusLine()
						.getReasonPhrase());
			}

		} finally {
			if (response != null) {
				response.close();
			}
		}

		return result;
	}

	/**
	 * ִ��POST����
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public Result doPost(String url) throws IOException {
		return this.doPost(url, null);
	}

	/**
	 * �ύjson����
	 * 
	 * @param url
	 * @param json
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Result doPostJson(String url, String json)
			throws ClientProtocolException, IOException {
		// ����http POST����
		HttpPost httpPost = new HttpPost(url);
		Result result = new Result();
		httpPost.setConfig(this.requestConfig);

		if (json != null) {
			// ����һ��form��ʽ��ʵ��
			StringEntity stringEntity = new StringEntity(json,
					ContentType.APPLICATION_JSON);
			// ������ʵ�����õ�httpPost������
			httpPost.setEntity(stringEntity);
		}

		CloseableHttpResponse response = null;
		try {
			// ִ������
			response = this.httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				String responseText = EntityUtils.toString(
						response.getEntity(), "UTF-8");
				Map<String, String> responseMap = JacksonBinder
						.buildNonDefaultBinder().fromJson(responseText,
								Map.class);
				result.putAll(responseMap);
			} else {
				result.setCode(statusCode);
			}

		} finally {
			if (response != null) {
				response.close();
			}
		}
		return result;
	}

	public Result upload(String url,File file) throws ClientProtocolException, IOException {
		HttpPost httppost = new HttpPost(url);
		Result result = new Result();
		FileBody bin = new FileBody(file);
		
		HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file", bin).build();

		
		httppost.setEntity(reqEntity);

		System.out.println("executing request " + httppost.getRequestLine());
		CloseableHttpResponse response = null;
		try {
			response = this.httpClient.execute(httppost);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {
				System.out.println("Response content length: "
						+ resEntity.getContentLength());
				String responseText = EntityUtils.toString(
						response.getEntity(), "UTF-8");
				Map<String, String> responseMap = JacksonBinder
						.buildNonDefaultBinder().fromJson(responseText,
								Map.class);
				result.putAll(responseMap);
			}
			EntityUtils.consume(resEntity);
		} finally {
			response.close();
		}
		
		return result;
	}
	
	public Result doDelete(String url) throws IOException {
		HttpDelete httpDelete = new HttpDelete(url);
		httpDelete.setConfig(this.requestConfig);
		Result result = new Result();
		CloseableHttpResponse response = null;
		try {
			// ִ������
			response = httpClient.execute(httpDelete);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				String responseText = EntityUtils.toString(
						response.getEntity(), "UTF-8");
				logger.info(responseText);
				Map<String, String> responseMap = JacksonBinder
						.buildNonDefaultBinder().fromJson(responseText,
								Map.class);
				result.putAll(responseMap);
			} else {
				result.error(statusCode, response.getStatusLine()
						.getReasonPhrase());
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return result;
	}
	

}
